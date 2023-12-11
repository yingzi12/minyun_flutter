package com.xinshijie.gallery.controller;

import com.xinshijie.gallery.config.Sitemap;
import com.xinshijie.gallery.vo.SitemapUrl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.util.Arrays;

@RestController
public class SitemapController {

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getSitemap() throws Exception {
        Sitemap sitemap = new Sitemap();
        sitemap.setUrls(Arrays.asList(
                new SitemapUrl("https://example.com/page1", "2023-01-01", "daily", "0.8"),
                new SitemapUrl("https://example.com/page2", "2023-01-02", "weekly", "0.5")
                // 添加更多 URL
        ));

        JAXBContext jaxbContext = JAXBContext.newInstance(Sitemap.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshaller.marshal(sitemap, sw);
        return sw.toString();
    }
}
