package com.xinshijie.gallery.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.io.IOException;

/**
 * 云路供应链科技有限公司 版权所有 © Copyright 2020
 *
 * @author 卢煌
 * @version 1.0
 * 、 * @date 2021-07-27 15:09
 */
@Slf4j
public class JsoupUtil {

    /**
     * 使用自带的basicWithImages 白名单
     * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
     * strike,strong,sub,sup,u,ul,img
     * 以及a标签的href,img标签的src,align,alt,height,width,title属性
     */
    private static final Safelist whitelist = Safelist.relaxed();
    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    static {
        // 富文本编辑时一些样式是使用style来进行实现的
        // 比如红色字体 style="color:red;"
        // 所以需要给所有标签添加style属性
        whitelist.addTags("font", "video")
                .addAttributes(":all", "style", "color", "face")
                .addAttributes("span", "class")
                .addAttributes("blockquote", "class", "style")
                .addAttributes("colgroup", "class", "style")
                .addAttributes("figure", "class", "style")
                .addAttributes("mark", "class", "style")
                .addAttributes("hr", "style")
                .addAttributes("s", "style");
    }

    public static String clean(String content) {
        return Jsoup.clean(content, "", whitelist, outputSettings);
    }

    public static void main(String[] args) throws IOException {
        String text = "<p><video src=\"\" controls=\"controls\" style=\"max-width:100%\" data-src=\"ylfile/KBM/8015a410873e4da4b6bd6b56b4000665.mp4\"></video><pre><img color=\"#345234\" src=\"http:\\a\" onerror=\"prompt(1)\" data-src=\"\"  data-no-convert=\"true\"&gt;asa</pre><p><a href=\"https://www.baidu.com/s?ie=UTF-8&amp;wd=prompt\" target=\"_blank\">测试</a><br></p><h1 id=\"gnunb\">test1</h1><p><b>test2</b></p><p><b style=\"font-size: x-small;\">test3&nbsp; </b><font size=\"6\">test4</font></p>" +
                "<blockquote><p><font size=\"6\" face=\"微软雅黑\">test5</font></p></blockquote>" +
                "<p><br></p><p><span style=\"font-size: 14px;\"></span><font size=\"6\" face=\"微软雅黑\" color=\"#c24f4a\">test6</font></p><blockquote><p><font size=\"6\" face=\"微软雅黑\" color=\"#c24f4a\">test7</font></p></blockquote><p>😚<span style=\"font-size: 14px;\">😔</span><img src=\"undefined\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\" data-src=\"\" data-no-convert=\"true\"></p><br>";
        String test2 = "<p>VIP系统寄件地址管理视频教程<img src=\"a\" onerror=\"prompt(1)\" data-src=\"\" data-no-convert=\"true\">&nbsp;ffffdfd</p>";
        System.out.println(clean(text));
        System.out.println(clean(test2));

    }
}
