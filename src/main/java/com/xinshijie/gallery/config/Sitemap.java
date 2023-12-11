package com.xinshijie.gallery.config;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
import com.xinshijie.gallery.vo.SitemapUrl;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class Sitemap {
    private List<SitemapUrl> urls;

    @XmlElement(name = "url")
    public List<SitemapUrl> getUrls() {
        return urls;
    }

    public void setUrls(List<SitemapUrl> urls) {
        this.urls = urls;
    }

    // 其他逻辑，如添加 URL 等
}

