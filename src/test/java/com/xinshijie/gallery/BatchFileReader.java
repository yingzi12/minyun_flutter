package com.xinshijie.gallery;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xinshijie.gallery.domain.Album;
import com.xinshijie.gallery.domain.Image;
import com.xinshijie.gallery.service.IAlbumService;
import com.xinshijie.gallery.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@SpringBootTest(classes = GalleryApplication.class)
public class BatchFileReader {

    @Autowired
    private IAlbumService albumService;

    @Autowired
    private ImageService imageService;

    private String sourceWeb = "https://image.51x.uk/xinshijie";

//    public void update() {
//        Path dirPath = Paths.get("E:\\folder\\x6o");
//        int batchSize = 10; // 设置你想每批处理的文件数量
//
//        try (Stream<Path> files = Files.list(dirPath)) {
//            long skippedFiles = 0;
//            boolean keepProcessing = true;
//
//            while (keepProcessing) {
//                List<Path> batch = files.skip(skippedFiles)
//                        .limit(batchSize)
//                        .toList();
//
//                if (batch.isEmpty()) {
//                    keepProcessing = false;
//                } else {
//                    processBatch(batch);
//                    skippedFiles += batch.size();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void update() {
        Path dirPath = Paths.get("E:\\folder\\x6o");
        int batchSize = 100; // 设置你想每批处理的文件数量
        long skippedFiles = 0;
        boolean keepProcessing = true;

        while (keepProcessing) {
            try (Stream<Path> files = Files.list(dirPath)) {
                List<Path> batch = files.skip(skippedFiles)
                        .limit(batchSize)
                        .collect(Collectors.toList());

                if (batch.isEmpty()) {
                    keepProcessing = false;
                } else {
                    processBatch(batch);
                    skippedFiles += batch.size();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processBatch(List<Path> batchs) throws IOException {
        for (Path file : batchs) {
            // 读取文件内容
            List<Path> files = Files.walk(file)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            if (files.size() > 0) {
                String title = file.getFileName().toString().split("_")[0];
                Album albumVo = albumService.getInfoBytitle(title);
                if (albumVo == null) {
                    addAlum(title, files);
                } else {
                    List<Image> values = imageService.listAll(albumVo.getId());
                    int count = 0;
                    for (Image image : values) {
                        if (image.getSourceUrl() == null || !image.getSourceUrl().startsWith("/image")) {
                            Path path = Paths.get(image.getUrl());
                            Path imageName = path.getFileName(); // 这将获取路径的最后一部分

                            String imageLJ = "/image/" + Math.abs(HashUtil.apHash(albumVo.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(albumVo.getTitle()) + "/" + imageName;
                            String destinationPath = "E:\\folder\\x6o_e2" + imageLJ;
                            boolean ok = downloadImage(image.getSourceWeb() + image.getUrl(), destinationPath, 0);
                            if (ok) {
                                count = count + 1;
                            } else {
                                imageService.removeById(image.getId());
                            }
                        }
                    }
                    if (count != files.size()) {
//                        imageService.delAlum(album.getId());
//                        albumService.removeById(albumVo.getId());
                        addImageList(albumVo, files);
                    }
                    if (count == 0) {
                        imageService.delAlum(albumVo.getId());
                        albumService.removeById(albumVo.getId());
                    }
                }
            } else {
                Files.delete(file);
            }
        }
    }

    public Image addImage(Integer aid, String path) {
        Image image = new Image();
        image.setAid(aid);
        image.setUrl(path);
        image.setSourceWeb(sourceWeb);
        image.setSourceUrl(path);
        return image;
    }

    public boolean downloadImage(String url, String destinationFile, int count) {
        if (count > 3) {
            log.error("同步url 下载图片，url:{}", url);
            return false;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

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
            e.printStackTrace();
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
            Path destinationPathFile = Paths.get("E:\\folder\\x6o_e2\\image\\" + Math.abs(HashUtil.apHash(album.getTitle())) % 1000 + "\\" + DigestUtil.md5Hex(album.getTitle()));
            if (destinationPathFile != null && !Files.exists(destinationPathFile)) {
                // 如果目标目录不存在，则创建它
                Files.createDirectories(destinationPathFile);
            }
            String imageLJ = "/image/" + Math.abs(HashUtil.apHash(album.getTitle())) % 1000 + "/" + DigestUtil.md5Hex(album.getTitle()) + "/" + imageName;
            Path destinationPath = Paths.get("E:\\folder\\x6o_e2" + imageLJ);
            try {
                // 移动文件，如果目标文件存在则替换它
                Files.move(imagePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = addImage(album.getId(), imageLJ);
                imageList.add(image);
            } catch (IOException e) {
                System.err.println("Error occurred while moving the file.");
                e.printStackTrace();
            }
        }
        imageService.addBatch(imageList);
    }
}
