package com.xinshijie.gallery.service.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.dao.Image;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.service.AlbumService;
import com.xinshijie.gallery.service.ILocalImageService;
import com.xinshijie.gallery.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@Service
public class LocalImageServiceImpl implements ILocalImageService {

    @Autowired
    private AlbumService albumService;
    private final Semaphore semaphore = new Semaphore(30); // Adjust the number of permits as needed
    private final Set<Long> currentAlbumIds = ConcurrentHashMap.newKeySet();


    @Autowired
    private ImageService imageService;

    //    private String sourceWeb="https://image.51x.uk/xinshijie";
//
//    private String sourcePaht="/data/e2";
    @Value("${image.sourceWeb}")
    private String sourceWeb;

    @Value("${image.path}")
    private String sourcePaht;
    private ExecutorService executorService = Executors.newFixedThreadPool(20); // 创建一个固定大小的线程池

    private Map<String, Integer> notHostnameMap = new ConcurrentHashMap<>();


    @Async
    public void updateThread() {
        AlbumDto dto = new AlbumDto();
        dto.setPageNum(1);
        dto.setPageSize(100);
        dto.setNotSource("https://image.51x.uk/xinshijie");
        dto.setOffset(dto.getPageSize() * (dto.getPageNum() - 1));
        Integer total = albumService.count(dto) / 100 + 1;

        for (int i = 0; i < total; i++) {
            System.out.println("i：" + i);
            List<Album> list = albumService.list(dto);
            List<Future<?>> futures = new ArrayList<>();

            for (Album album : list) {
                System.out.println("i:" + i + "  aid:" + album.getId());
                // 提交任务到线程池
                Future<?> future = executorService.submit(() -> saveAlbum(album));
                futures.add(future);

                if (futures.size() == 20) {
                    // 等待这批20个任务完成后再继续
                    waitForFutures(futures);
                    futures.clear(); // 清空列表以便提交下一批任务
                }
            }

            // 确保最后一批任务完成
            waitForFutures(futures);
        }

        System.out.println("所有任务已完成");
    }

    private void waitForFutures(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get(); // 阻塞，直到任务完成
            } catch (InterruptedException | ExecutionException e) {
                log.error("Error in executing task", e);
            }
        }
    }

    @Async
    public void saveLocalAlbum(Album album) {
        Long albumId = album.getId();

        if (currentAlbumIds.add(albumId)) { // Add returns true if the set did not already contain the specified element
            try {
                semaphore.acquire();
                log.info("下载图片到本地：{}", albumId);
                saveAlbum(album);
                log.info("下载图片到本地完成：{}", albumId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // Handle thread interruption
            } finally {
                currentAlbumIds.remove(albumId); // Remove the album id after processing
                semaphore.release();
            }
        }
    }

    public void saveAlbum(Album albumVo) {

        List<Image> values = imageService.listAll(albumVo.getId());
        albumVo.setNumberPhotos(values.size());
        updateAlbum(albumVo);
        imageService.delCfAid(albumVo.getId());
        int count = 0;
        int error = 0;
        for (Image image : values) {
            try {
                if (image.getSourceUrl() == null || !image.getSourceUrl().startsWith("/image")) {
                    Path path = Paths.get(image.getUrl());
                    Path imageName = path.getFileName(); // 这将获取路径的最后一部分
                    if (image.getUrl().startsWith("/image")) {
                        updateImage(image.getId(), image.getAid(), image.getUrl());
                        count = count + 1;
                    } else {
                        String imageLJ = "/image/" + Math.abs(HashUtil.apHash(albumVo.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(albumVo.getTitle()) + "/" + imageName;
                        String destinationPath = sourcePaht + imageLJ;
                        String imgUrl = image.getSourceWeb() + image.getUrl();
                        if (StringUtils.isNotEmpty(image.getSourceUrl())) {
                            imgUrl = image.getSourceUrl();
                        }
                        boolean ok = downloadImage(imgUrl, destinationPath, 0);
                        if (ok) {
                            updateImage(image.getId(), image.getAid(), imageLJ);
                            count = count + 1;
                        } else {
                            error = error + 1;
                            if (error > 5) {
                                count = 0;
                                break;
                            }
                            //                            imageService.removeById(image.getId());
                        }
                    }
                } else {
                    if (!sourceWeb.equals(image.getSourceWeb())) {
                        updateImage(image.getId(), image.getAid(), image.getSourceUrl());
                    }
                    if (count < 6 && !isImageUrlValid(image.getSourceWeb() + image.getSourceUrl(), 0)) {
                        if (error > 5) {
                            count = 0;
                            break;
                        }
                    } else {
                        count++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (count == 0) {
            imageService.delAlum(albumVo.getId());
            albumService.removeById(albumVo.getId());
        } else {
            if (StringUtils.isEmpty(albumVo.getSourceUrl())) {
                albumVo.setSourceUrl(values.get(0).getSourceUrl());
                albumService.updateById(albumVo);
            }
        }


    }

    public Image addImage(Long aid, String path) {
        Image image = new Image();
        image.setAid(aid);
        image.setUrl(path);
        image.setSourceWeb(sourceWeb);
        image.setSourceUrl(path);
        return image;
    }

    public void updateImage(Long id, Long aid, String path) {
        Image image = new Image();
        image.setId(id);
        image.setAid(aid);
        image.setUrl(path);
        image.setSourceWeb(sourceWeb);
        image.setSourceUrl(path);
        imageService.updateById(image);
    }

    public void updateAlbum(Album albumVo) {
        try {
            if (albumVo.getSourceUrl() == null || !albumVo.getSourceUrl().startsWith("/image")) {
                if (albumVo.getSourceWeb() != null && albumVo.getImgUrl() != null) {
                    Path imagePath = Paths.get(albumVo.getImgUrl());
                    String imageLJ = "/image/" + Math.abs(HashUtil.apHash(albumVo.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(albumVo.getTitle()) + "/" + imagePath.getFileName().toString();
                    String destinationPath = sourcePaht + "" + imageLJ;
                    boolean ok = downloadImage(albumVo.getSourceWeb() + albumVo.getImgUrl(), destinationPath, 0);
                    if (ok) {
                        albumVo.setSourceUrl(imageLJ);
                        albumVo.setSourceWeb(sourceWeb);
                        albumService.updateById(albumVo);
                    } else {
                        if (StringUtils.isNotEmpty(albumVo.getSourceUrl()) && albumVo.getSourceUrl().startsWith("http")) {
                            Document doc = requestUrl(albumVo.getSourceUrl(), 0);
                            if (doc == null) {
                                albumVo.setSourceWeb(sourceWeb);
                                albumVo.setSourceUrl("");
                                albumService.updateById(albumVo);
                                return;
                            }
                            String imgUlr = getString(doc, "meta[property=og:image]");
                            if (StringUtils.isEmpty(imgUlr)) {
                                albumVo.setSourceWeb(sourceWeb);
                                albumVo.setSourceUrl("");
                                albumService.updateById(albumVo);
                                return;
                            }
                            String fileName = imgUlr.substring(imgUlr.lastIndexOf('/') + 1);

                            imageLJ = "/image/" + Math.abs(HashUtil.apHash(albumVo.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(albumVo.getTitle()) + "/" + fileName;
                            destinationPath = sourcePaht + "" + imageLJ;
                            ok = downloadImage(imgUlr, destinationPath, 0);
                            if (ok) {
                                albumVo.setSourceUrl(imageLJ);
                                albumVo.setSourceWeb(sourceWeb);
                                albumService.updateById(albumVo);
                            } else {
                                albumVo.setSourceWeb(sourceWeb);
                                albumVo.setSourceUrl("");
                                albumService.updateById(albumVo);
                            }
                        }
                    }
                }
            } else {
                if (albumVo.getSourceUrl().startsWith("/image") || !sourceWeb.equals(albumVo.getSourceWeb())) {
                    albumVo.setSourceWeb(sourceWeb);
                    albumService.updateById(albumVo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean downloadImage(String url, String destinationFile, int count) {
        if (count > 3) {
            log.error("同步url 下载图片，url:{}", url);
            return false;
        }
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        CloseableHttpClient httpClient = null;
        HttpGet request = null;
        try {
            httpClient = HttpClients.createDefault();
            request = new HttpGet(url);
        } catch (Exception exception) {
            return false;
        }
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                File outputFile = new File(destinationFile);

                File parentDir = outputFile.getParentFile();

                // 如果父目录不存在，尝试创建它
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                try (InputStream inputStream = entity.getContent();
                     FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                // 验证图片是否正常
                BufferedImage image = ImageIO.read(outputFile);
                if (image == null) {
                    return false;
                } else {
                    EntityUtils.consume(entity);
                    return true;
                }
            }
        } catch (IOException e) {
            downloadImage(url, destinationFile, count + 1);
        }
        return false;
    }

    public void addAlum(String title, List<Path> files) throws IOException {
        Album album = new Album();
        album.setSourceWeb(sourceWeb);
        album.setTitle(title);
        album.setHash(HashUtil.hfHash(album.getTitle()));
        album.setNumberPhotos(files.size());
        album.setCreateTime(LocalDate.now().toString());
        album.setUpdateTime(LocalDate.now().toString());

        String albumImageUrl = "/image/" + Math.abs(HashUtil.apHash(album.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(album.getTitle()) + "/" + files.get(0).getFileName().toString();
        album.setSourceUrl(albumImageUrl);
        album.setCountSee(RandomUtil.randomLong(0, 100));
        albumService.add(album);
        Album albumVo = albumService.getInfoBytitle(title);
        addImageList(albumVo, files);
    }

    public void addImageList(Album album, List<Path> files) throws IOException {
        List<Image> imageList = new ArrayList<>();
        for (Path imagePath : files) {
            String imageName = imagePath.getFileName().toString();
            Path destinationPathFile = Paths.get(sourcePaht + "\\image\\" + Math.abs(HashUtil.apHash(album.getTitle())) % 1000 + "\\" + DigestUtil.md5Hex(album.getTitle()));
            if (destinationPathFile != null && !Files.exists(destinationPathFile)) {
                // 如果目标目录不存在，则创建它
                Files.createDirectories(destinationPathFile);
            }
            String imageLJ = "/image/" + Math.abs(HashUtil.apHash(album.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(album.getTitle()) + "/" + imageName;
            Path destinationPath = Paths.get(sourcePaht + "" + imageLJ);
            try {
                // 移动文件，如果目标文件存在则替换它
                Files.move(imagePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = addImage(album.getId(), imageLJ);
                imageList.add(image);
            } catch (Exception e) {
                System.err.println("Error occurred while moving the file.");
                e.printStackTrace();
            }
        }
        imageService.addBatch(imageList);
    }

    public Document requestUrl(String url, int count) {
        if (count > 3) {
            return null;
        }
        try {
            Connection connection = Jsoup.connect(url)
                    .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                    .timeout(120000);

            URL urlPath = new URL(url);
            // 使用getHost()方法获取主机部分
            if (count == 0) {
                String host = urlPath.getHost();
                connection.header("Host", host);
            }

            return connection.get();
        } catch (SSLHandshakeException e) {
            log.error("线程名-地址：{}， url：{} count:{}", Thread.currentThread().getName(), url, count, e);
            count = count + 1;
            if (count < 3)
                requestUrl(url, count);
        } catch (Exception e) {
            log.error("线程名-地址：{}， url：{} count:{}", Thread.currentThread().getName(), url, count, e);
            count = count + 1;
            if (count < 3)
                requestUrl(url, count);

        }
        return null;
    }

    public String getString(Document doc, String name) {
        Element element = doc.selectFirst(name);
        if (element != null) {
            String content = element.attr("content");
            return content;
        }
        return "";
    }

    /**
     * 判断url是否可以访问
     *
     * @param imageUrl
     * @return
     */
    public boolean isImageUrlValid(String imageUrl, int count) {
        if (count > 3) {
            log.error("判断url是否可以访问： url:{}", imageUrl);
            return false;
        }

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(imageUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int responseCode = response.getStatusLine().getStatusCode();
                return responseCode == 200;
            }
        } catch (IOException e) {
            return isImageUrlValid(imageUrl, count + 1);
        }
    }

}
