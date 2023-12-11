package com.xinshijie.gallery.vo;

import lombok.Data;

@Data
public class SitemapUrl {
    private String loc;
    private String lastmod;
    private String changefreq;
    private String priority;

    public SitemapUrl(String loc, String lastmod, String changefreq, String priority) {
        this.loc = loc;
        this.lastmod = lastmod;
        this.changefreq = changefreq;
        this.priority = priority;
    }
}
