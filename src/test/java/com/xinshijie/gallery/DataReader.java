package com.xinshijie.gallery;

import com.xinshijie.gallery.dao.Album;
import com.xinshijie.gallery.dao.Image;
import com.xinshijie.gallery.dto.AlbumDto;
import com.xinshijie.gallery.dto.ImageDto;
import com.xinshijie.gallery.service.AlbumService;
import com.xinshijie.gallery.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootTest(classes = GalleryApplication.class)
public class DataReader {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private ImageService imageService;

    @Test
    public void delAlbum() {
        for (int i = 25; i < 60; i++) {
            System.out.println("page:" + i);
            AlbumDto albumDto = new AlbumDto();
            albumDto.setPageNum(1);
            albumDto.setPageSize(1000);
            albumDto.setOffset(i * 1000);
            List<Album> list = albumService.list(albumDto);
            for (Album album : list) {
                ImageDto imageDto = new ImageDto();
                imageDto.setAid(album.getId());
                imageDto.setPageNum(1);
                imageDto.setPageSize(6);
                imageDto.setOffset(0);
                List<Image> imageList = imageService.list(imageDto);
                int count = 0;
                for (Image image : imageList) {
                    String imgUrl = "";
                    if (image.getSourceUrl() != null && image.getSourceUrl().startsWith("/image")) {
                        imgUrl = image.getSourceWeb() + image.getSourceUrl();
                    } else {
                        imgUrl = image.getSourceWeb() + image.getUrl();
                    }
                    boolean ok = isImageUrlValid(imgUrl, 0);
                    if (ok) {
                        count++;
                    }
                }
                if (count == 0) {
                    albumService.removeById(album.getId());
                    imageService.delAlum(album.getId());
                }
            }
        }
    }

    /**
     * 判断url是否可以访问
     *
     * @param imageUrl
     * @return
     */
    public boolean isImageUrlValid(String imageUrl, int count) {
        if (count > 1) {
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
