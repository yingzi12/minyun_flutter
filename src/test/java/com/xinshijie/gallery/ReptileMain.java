package com.xinshijie.gallery;

import com.xinshijie.gallery.service.IReptileImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.xinshijie.gallery.vo.ReptilePage;
import com.xinshijie.gallery.vo.ReptileRule;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = GalleryApplication.class)
public class ReptileMain {
    @Autowired
    private IReptileImageService reptileService;



    @Test
    public  void test(){

        ReptileRule ruleDto=new ReptileRule();
        ruleDto.setIsHead(0);
        ruleDto.setIsUpdate(1);
        ruleDto.setStoryPageRule("div#blog-entries");
        ruleDto.setStoryPageGroupRule("div.thumbnail");
        ruleDto.setStoryPageHrefRule("a");

        ruleDto.setTitleRule("meta[property=og:title]");
//        ruleDto.setAuthorRule("og:title");
        ruleDto.setImageUrlRule("meta[property=og:image]");

        ruleDto.setChapterListRule("div.book_list");
        ruleDto.setChapterGroupRule("ul.chapterlist li a");
        ruleDto.setChapterUrlRule("a");

//        ruleDto.setChapterPageRule("");
//        ruleDto.setContentPageRule("a");
//        ruleDto.setChapterPageUrlRule("a");

        ReptilePage reptilePage=new ReptilePage();
        reptilePage.setNowPage(1);
        reptilePage.setPageTotal(5);
        reptilePage.setRuleId(1);
        reptilePage.setStatus(1);
        reptilePage.setPageUrl("https://everia.club/category/korea/page/<地址>/");
        List<ReptilePage> pages=new ArrayList<>();
        pages.add(reptilePage);
        reptileService.orderBySingle(ruleDto,pages);


//        ruleDto.setChapterContentRule("div#htmlContent");
//        Map<String,String> replaceListRule=new HashMap<>();
//        replaceListRule.put("燃文小说网 www.ranwen8.com","");
//        ruleDto.setReplaceListRule(replaceListRule);
//        reptileService.orderByExecutor("https://www.ranwen8.com/fenlei/1_2/",ruleDto);

//       reptileService.chapter("https://www.ranwen8.com/fenlei/1_2/",ruleDto);

    }

    @Test
    public  void test10(){
        reptileService.ayacData(10);
    }





}
