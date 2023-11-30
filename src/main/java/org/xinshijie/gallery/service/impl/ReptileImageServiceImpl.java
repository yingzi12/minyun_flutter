package org.xinshijie.gallery.service.impl;


import cn.hutool.core.util.HashUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xinshijie.gallery.common.ServiceException;
import org.xinshijie.gallery.dao.Album;
import org.xinshijie.gallery.dao.Image;
import org.xinshijie.gallery.dto.AlbumDto;
import org.xinshijie.gallery.dto.ImageDto;
import org.xinshijie.gallery.service.AlbumService;
import org.xinshijie.gallery.service.IReptileImageService;
import org.xinshijie.gallery.service.ImageService;
import org.xinshijie.gallery.vo.ReptilePage;
import org.xinshijie.gallery.vo.ReptileRule;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class ReptileImageServiceImpl implements IReptileImageService {

     @Autowired
     private AlbumService albumService;

     @Autowired
    private ImageService imageService;

     @Value("${reptile.url}")
     private String reptileUrl;

    @Value("${image.path}")
    private String imagePath;

//    private static SSLContext sc;
//    static {
//
//        try {
//            // 创建信任所有证书的 TrustManager
//            TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//
//                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        }
//
//                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        }
//                    }
//            };
//
//            // 安装信任所有证书的 TrustManager
//            sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new SecureRandom());
//
//        }catch (Exception ex){
//            ex.getMessage();
//        }
//    }

    // 类成
     @Async
     public void ayacData(Integer id){
         //链式构建请求
         String result = HttpRequest.get(reptileUrl+"/wiki/reptileRule/getInfo/"+id)
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();
         JSONObject jsonObject=JSONObject.parseObject(result);
         if(jsonObject.getIntValue("code")!=200){
             throw new ServiceException(" reptileRuleVo 出现异常");
         }
         ReptileRule reptileRuleVo = JSON.parseObject(jsonObject.getString("data"), ReptileRule.class);

         //链式构建请求
         String result2 = HttpRequest.get(reptileUrl+"/wiki/reptilePage/getList/"+id)
                 .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                 .timeout(20000)//超时，毫秒
                 .execute().body();

         JSONObject pageJson=JSONObject.parseObject(result2);
         if(jsonObject.getIntValue("code")!=200){
             throw new ServiceException("pageJson 出现异常");
         }
         List<ReptilePage> pageList =new ArrayList<>();
         pageList.addAll( JSON.parseArray(pageJson.getJSONArray("data").toJSONString(), ReptilePage.class));
         orderBySingle(reptileRuleVo,pageList);
     }

     @Async
    public void singleData(){
        //链式构建请求
        String ruleJson = HttpRequest.get(reptileUrl+"/wiki/reptileRule/getList/4")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        JSONObject ruleListObject=JSONObject.parseObject(ruleJson);
        if(ruleListObject.getIntValue("code")!=200){
            throw new ServiceException(" ruleList 出现异常");
        }
        List<ReptileRule> ruleList = JSON.parseArray(ruleListObject.getJSONArray("data").toJSONString(), ReptileRule.class);

        for(ReptileRule reptileRule:ruleList) {

            //链式构建请求
            String result2 = HttpRequest.get(reptileUrl + "/wiki/reptilePage/getList/" + reptileRule.getId())
                    .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
//                 .form(paramMap)//表单内容
                    .timeout(20000)//超时，毫秒
                    .execute().body();

            JSONObject pageJson = JSONObject.parseObject(result2);
            if (pageJson.getIntValue("code") != 200) {
                throw new ServiceException("pageJson 出现异常");
            }
            List<ReptilePage> pageList = JSON.parseArray(pageJson.getJSONArray("data").toJSONString(), ReptilePage.class);
            orderBySingle(reptileRule, pageList);


        }
    }

    public void orderBySingle(ReptileRule reptileRule, List<ReptilePage> pageList ){
        if(reptileRule==null){
            throw new ServiceException("错误");
        }
        for(ReptilePage pageVo:pageList) {
            Integer pageStart=pageVo.getNowPage();
            if(reptileRule.getIsHead()==1){
                pageStart=1;
            }
            for(int i=pageStart;i<= pageVo.getPageTotal();i++) {
                String url = pageVo.getPageUrl();
                if(pageVo.getPageSize()>0){
                    url = pageVo.getPageUrl().replaceAll("<地址>",((i-1)*20)+"" );
                }else {
                    url = pageVo.getPageUrl().replaceAll("<地址>",i+"" );
                }
                try {
                    Document doc = requestUrl(url,reptileRule,0);
                    Element body = doc.body();
                    Element cont = body.select(reptileRule.getStoryPageRule()).first();
                    Elements storyList = cont.select(reptileRule.getStoryPageGroupRule());
                    if (storyList != null) {
                        for (Element story : storyList) {
                            Element hrefElement = story.select(reptileRule.getStoryPageHrefRule()).first();
                            if(hrefElement !=null) {
                                String detailUrl = hrefElement.attr("href");
                                String imgUrl="";
                                if(StringUtils.isNotEmpty(reptileRule.getStoryPageImgRule())){
                                    Element imgUrlEle = story.select(reptileRule.getStoryPageImgRule()).first();
                                    if(imgUrlEle!=null){
                                        imgUrl=imgUrlEle.attr("src");
                                    }
                                }
                                detail(detailUrl,imgUrl, reptileRule);
                            }else{
                                addError(1,story.html(),reptileRule.getId(),url);
                            }
                        }
                    }
                    if(i%10==0){
                        System.out.println("---------------++++++++++++++++++++++--------------------------");
                    }
                    reptileRule.setStatus(2);
                } catch (Exception e) {
                    addError(1,e.getMessage(),reptileRule.getId(),url);
                    log.error("出现异常 方法:{} reId:{} message","orderByExecutor",reptileRule.getId(),e);
                }
                pageVo.setNowPage(i);
                updatePage(pageVo);
            }
        }
        reptileRule.setEndTime(LocalDateTime.now());
    }

    @Async
    @Override
    public void singleLocalData() {
        AlbumDto albumDto=new AlbumDto();
        albumDto.setPageNum(1);
        albumDto.setOrder("count_see");
        albumDto.setPageSize(800);
        List<Album> list = albumService.list(albumDto);
        for(Album album:list){
            if(album.getImgUrl()!=null&&album.getImgUrl().length()>0 &&( album.getSourceUrl()==null || !album.getSourceUrl().startsWith("/image") )) {
                String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(album.getImgUrl()), album.getSourceWeb() + album.getImgUrl());
                if(sourceUrl.equals("")){
                    log.error("同步album错误1，albumId:{},album:{},imageId:{},sourceWeb:{},imageUrl:{}",album.getId(),album.getTitle(),album.getId(),album.getSourceWeb(),album.getImgUrl());
                }else{
                    album.setSourceUrl(sourceUrl);
                    albumService.updateSourceUrl(album);
                }
            }else{
                if(album.getImgUrl() ==null || !album.getImgUrl().startsWith("/image")) {
                    log.error("同步album错误2，albumId:{},album:{},imageId:{},sourceWeb:{},imageUrl:{}", album.getId(), album.getTitle(), album.getId(), album.getSourceWeb(), album.getImgUrl());
                }
            }

            List<Image>  values=  imageService.listAll(album.getId());
            for(Image image:values){
                if(image.getUrl()!=null && image.getUrl().length()>0 && ( image.getSourceUrl()==null || ! image.getSourceUrl().startsWith("/image") )) {
                    String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(image.getUrl()), image.getSourceWeb() + image.getUrl());
                    if(sourceUrl.equals("")){
                        log.error("同步url错误1，albumId:{},album:{},imageId:{},sourceWeb:{},imageUrl:{}",album.getId(),album.getTitle(),image.getId(),image.getSourceWeb(),image.getUrl());
                    }else{
                        image.setSourceUrl(sourceUrl);
                        imageService.updateSourceUrl(image);
                    }
                }else{
                    if(image.getSourceUrl() ==null || !image.getSourceUrl().startsWith("/image")) {
                        log.error("同步url错误2，albumId:{},album:{},imageId:{},sourceWeb:{},imageUrl:{}", album.getId(), album.getTitle(), image.getId(), image.getSourceWeb(), image.getUrl());
                    }
                }
            }
        }

    }



    public void detail(String url,String imgUrl, ReptileRule reptileRule ){
        String detailUrl=url;
        try {
            if(StringUtils.isNotEmpty(reptileRule.getStoryUrl())){
                detailUrl=reptileRule.getStoryUrl()+url;
            }else {
                if (reptileRule.getStoryUrlType() != null) {
                    detailUrl = reptileRule.getStoryUrl() + url;
                }
            }
            Document doc = requestUrl(detailUrl,reptileRule,0);

            String title = extractContent(doc, reptileRule.getTitleRule());

            String gril = extractContent(doc, reptileRule.getAuthorRule());
            String desc = extractContent(doc, reptileRule.getDescRule());

            Long hash = generate12DigitHash(detailUrl, title);
            Album album= albumService.getInfoBytitle(title);

            String sourceWeb="";
            if(StringUtils.isNotEmpty(imgUrl) && imgUrl.startsWith("http")){
                URL imgURLPath = new URL(imgUrl);
                // 获取协议和主机名
                String protocol = imgURLPath.getProtocol();
                String host = imgURLPath.getHost();
                // 构建基本 URL
                sourceWeb = protocol + "://" + host;
                // 获取资源路径
                imgUrl = imgURLPath.getPath();
            }else{
                sourceWeb=reptileRule.getImgUrl();
            }
            if(album==null){
                album=new Album();
                album.setSourceWeb(sourceWeb);
                album.setSourceUrl(detailUrl);
                album.setUrl(detailUrl);
                album.setImgUrl(imgUrl);
                album.setCountError(0);
                album.setCountSee(0L);
                album.setCreateTime(LocalDate.now().toString());
                album.setUpdateTime(LocalDate.now().toString());
                album.setGril(gril);
                album.setIntro(desc);
                album.setHash(hash);
                album.setTitle(title);

                String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(album.getUrl()), album.getSourceWeb() + album.getUrl());
                album.setUrl(sourceUrl);
                albumService.add(album);
                album= albumService.getInfoBytitle(title);


            }else{
                //判断是否是同一组
                if(hash.equals(album.getHash())||StringUtils.isEmpty(album.getGril())||StringUtils.isEmpty(album.getUrl())||StringUtils.isEmpty(album.getIntro())){
                    album.setHash(hash);
                    album.setSourceUrl(detailUrl);
                    if(StringUtils.isNotEmpty(desc)) {
                        album.setIntro(desc);
                    }
                    if(StringUtils.isNotEmpty(sourceWeb)) {
                        album.setSourceWeb(sourceWeb);
                    }
                    if(StringUtils.isNotEmpty(imgUrl)) {
                        album.setImgUrl(imgUrl);
                    }
                    if(StringUtils.isNotEmpty(detailUrl)) {
                        album.setUrl(detailUrl);
                    }
                    if(StringUtils.isNotEmpty(gril)) {
                        album.setGril(gril);
                    }
                    album.setUpdateTime(LocalDate.now().toString());

                    String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(album.getUrl()), album.getSourceWeb() + album.getImgUrl());
                    if(!"".equals(sourceUrl)) {
                        album.setUrl(sourceUrl);
                    }
                    albumService.updateById(album);
                }
                //判断是否需要强制更新
                if(reptileRule.getIsUpdate()==2){
                    //判断图片地址是否有效,无效会全部删除
                    boolean isImage=isCheckImage(album.getId(),reptileRule);
                    if(isImage){
                        return;
                    }
                }else {
                    //更新内容
                    album=new Album();
                    album.setSourceWeb(reptileRule.getStoryUrl());
                    album.setSourceUrl(detailUrl);
                    album.setImgUrl(imgUrl);
                    album.setCreateTime(LocalDate.now().toString());
                    album.setGril(gril);
                    album.setHash(hash);
                    album.setId(album.getId());
                    album.setUpdateTime(LocalDate.now().toString());
                    String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(album.getUrl()), album.getSourceWeb() + album.getImgUrl());
                    if(!"".equals(sourceUrl)) {
                        album.setUrl(sourceUrl);
                    }
                    albumService.updateById(album);
                    //删除记录
                    imageService.delAlum(album.getId());

                    album= albumService.getInfoBytitle(title);
                }
            }


            if(StringUtils.isEmpty(reptileRule.getContentPageRule())) {
                addImageList(detailUrl,album,reptileRule);
            }else{
                Element body = doc.body();
                Element imagePageCoent = body.select(reptileRule.getContentPageRule()).first();
                if (imagePageCoent != null) {
                    Elements imageListElement = imagePageCoent.select(reptileRule.getChapterPageRule());
                    for (Element element : imageListElement) {
                        String pageUrl = element.select(reptileRule.getChapterPageUrlRule()).first().attr("href");
                        if(StringUtils.isNotEmpty(reptileRule.getPageUrl())){
                            pageUrl=reptileRule.getPageUrl()+pageUrl;
                        }
                        addImageList(pageUrl,album,reptileRule);
                    }
                }
            }
            //如果图片为空，就到图片列表里取一张图片
            if(StringUtils.isEmpty(imgUrl)){
                albumService.updateById(album);
            }

        } catch (Exception e) {
            addError(1,e.getMessage(),reptileRule.getId(),detailUrl);

            log.error("线程名-地址：{}，  href:{}",Thread.currentThread().getName(),detailUrl,e);
        }
    }

    public void addImageList(String detailUrl,Album album,ReptileRule reptileRule){
        Document doc = requestUrl(detailUrl,reptileRule,0);
        if(doc == null){
            return;
        }
        Element body = doc.body();

        Element chapterListContent = body.select(reptileRule.getChapterListRule()).first();
        if (chapterListContent != null) {
            Elements imageListElement = chapterListContent.select(reptileRule.getChapterGroupRule());
            List<Image> iamgeBatchInsertList = new CopyOnWriteArrayList<>();
            for (Element element : imageListElement) {
                String imageUrlSource="";
                Element imgElemnt = element.select(reptileRule.getChapterUrlRule()).first();
                if("a".equals(reptileRule.getChapterUrlRule())){
                     imageUrlSource =imgElemnt .attr("href");
                }
                if("img".equals(reptileRule.getChapterUrlRule())){
                     imageUrlSource = imgElemnt.attr("src");
                }
                if (StringUtils.isNotEmpty(imageUrlSource)){
                    Image image=new Image();
                    image.setAid(album.getId());
                    image.setSourceUrl(imageUrlSource);
                    try {
                        URL url = new URL(imageUrlSource);
                        String domain = url.getProtocol() + "://" + url.getHost();
                        String path = url.getPath() + (url.getQuery() != null ? "?" + url.getQuery() : "");
                        image.setSourceWeb(domain);
                        image.setUrl(path);
                        if(StringUtils.isEmpty(album.getImgUrl())){
                            album.setImgUrl(path);
                            album.setSourceWeb(domain);
                        }
                    } catch (MalformedURLException e) {
                        image.setSourceWeb("");
                        image.setUrl(imageUrlSource);
                        throw new RuntimeException(e);
                    }
                    String sourceUrl =  getImageUrl(album.getTitle(), HashUtil.apHash(image.getUrl()), image.getSourceWeb() + image.getUrl());
                    if(!"".equals(sourceUrl)) {
                        image.setUrl(sourceUrl);
                    }
                    iamgeBatchInsertList.add(image);
                }
            }
            imageService.addBatch(iamgeBatchInsertList);
            iamgeBatchInsertList.clear();
        }
    }
    public Document requestUrl(String url,ReptileRule reptileRule,Integer replyCount){
         if(replyCount>3){
             return null;
         }
        Connection connection= Jsoup.connect(url)
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36")
                .timeout(5000);
        try {
            if(StringUtils.isNotEmpty(reptileRule.getHost())){
                connection.header("Host",reptileRule.getHost());
            }else{
                URL urlPath = new URL(url);
                // 使用getHost()方法获取主机部分
                String host = urlPath.getHost();
                connection.header("Host",host);
            }

            return connection.get();
        } catch (Exception e) {
            addError(1,e.getMessage(),reptileRule.getId(),url);
            log.error("线程名-地址：{}， url：{}",Thread.currentThread().getName(),url,e);
            requestUrl(url,reptileRule,replyCount+1);
        }
        return null;
    }

    public boolean isURLValid(String urlString, ReptileRule reptileRule) {
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        // 创建所有主机名都有效的 HostnameVerifier
//        HostnameVerifier allHostsValid = (hostname, session) -> true;
//        // 安装 HostnameVerifier
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpHead request = new HttpHead(urlString);

            if (StringUtils.isNotEmpty(reptileRule.getHost())) {
                request.addHeader("Host", reptileRule.getHost());
            } else {
                URL url = new URL(urlString);
                request.addHeader("Host", url.getHost());
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int responseCode = response.getStatusLine().getStatusCode();
                return (responseCode >= 200 && responseCode < 300);
                // 2xx 表示成功响应

            }
        } catch (IOException e) {
            // URL无法正常访问
            return false;
        }
    }

    public void addError(Integer kind,String message,Integer ruleId,String url){
         log.error("ruleId:{}  url:{} kind:{}  message:{}",ruleId,url,kind,message);
//        ReptileErrorDto reptileError=new ReptileErrorDto();
//        reptileError.setKind(kind);
//        reptileError.setMessage(message);
//        reptileError.setRuleId(ruleId);
//        reptileError.setUrl(url);
//        errorService.add(reptileError);
    }

    private String extractContent(Document doc, String rule) {
         if(StringUtils.isEmpty(rule)){
             return  null;
         }
         String value=null;
        try {
            if(StringUtils.isEmpty(rule)) {
                return null;
            }
            Element element = doc.select(rule).first();
            if(element!=null){
               value= element.attr("content");
               if(StringUtils.isEmpty(value)){
                   value= element.html();
               }
            }
            return value;
//            return (element != null) ? element.attr("content") : null;
        } catch (Exception e) {
            // 处理异常，例如记录日志
            log.error("获取内容错误 rele:{}",rule,e);
            return null;
        }
    }

    public  Long generate12DigitHash(String url,String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 选择合适的散列算法
            byte[] hash = md.digest(input.getBytes());
            BigInteger bigInt = new BigInteger(1, hash);
            Long twelveBitHash = bigInt.longValue() % (long) Math.pow(10, 12); // 取模以确保是12位整数

            return twelveBitHash+0L;
        } catch (NoSuchAlgorithmException e) {
           log.error("获取title错误 url:{},",url,e);
              return System.currentTimeMillis();
        }
    }

    /**
     * 判断照片是否正常，不正常就删除整个图集，重新导入
     * @param aid
     * @return
     */
    public  boolean  isCheckImage(Long aid,ReptileRule reptileRule){
        ImageDto finDto=new ImageDto();
        finDto.setPageSize(3);
        finDto.setPageNum(2);
        finDto.setAid(aid);
        List<Image> list = imageService.list(finDto);
        if(list==null && list.size()==0){
            return false;
        }else {
            for(Image image:list){
                if(!isURLValid(image.getSourceWeb()+image.getUrl(),reptileRule)){
                    imageService.delAlum(aid);
                    return false;
                }
            }
        }

        return true;
    }

    public void updatePage(ReptilePage reptilePage){
        //链式构建请求
        String updateReq = HttpRequest.post(reptileUrl + "/wiki/reptilePage/edit")
                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                .body(JSONObject.toJSONString(reptilePage))//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
    }

    public String getImageUrl(String name,int imagePath,String url) {
        try {
            if (StringUtils.isEmpty(url)) {
                return "";
            }
            if (!isImageUrlValid(url,0)) {
                log.error("判断地址不可访问：name:{} ,imagePath:{}, url:{}",name,imagePath,url);
                return "";
            }
            String path = "/image/" + Math.abs(HashUtil.apHash(name)) % 1000 + "/" + DigestUtil.md5Hex(name) + "/" + imagePath;
            String extens = getFileExtensionFromURL(url);
            if (StringUtils.isNotEmpty(extens)) {
                path = path + "." + extens;
                return downloadImage(url, path,0);
            } else {
                path = path + ".jpg";
                return downloadImage(url, path,0);
            }
        }catch (Exception ex){
            log.error("判断getImageUrl是否可以访问：name:{} ,imagePath:{}, url:{}",name,imagePath,url);
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 判断url是否可以访问
     * @param imageUrl
     * @return
     */
    public boolean isImageUrlValid(String imageUrl, int count) {
        if (count > 3) {
            log.error("判断url是否可以访问： url:{}", imageUrl);
            return false;
        }

        try {
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//            // 创建所有主机名都有效的 HostnameVerifier
//            HostnameVerifier allHostsValid = (hostname, session) -> true;
//            // 安装 HostnameVerifier
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(imageUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int responseCode = response.getStatusLine().getStatusCode();
                if (responseCode != 200) {
                    return false;
                }else{
                    return true;
                }

//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    ContentType contentType = ContentType.getOrDefault(entity);
//                    String mimeType = contentType.getMimeType();
//
//                    if (mimeType.startsWith("image/")) {
//                        BufferedImage image = ImageIO.read(entity.getContent());
//                        EntityUtils.consume(entity);
//                        return image != null;
//                    }
//                }
            }
        } catch (IOException  e) {
            return isImageUrlValid(imageUrl, count + 1);
        }
    }

    public  String getFileExtensionFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            int lastSlashIndex = path.lastIndexOf("/");
            int lastDotIndex = path.lastIndexOf(".");

            if (lastDotIndex > 0 && lastDotIndex > lastSlashIndex) {
                return path.substring(lastDotIndex + 1);
            }
        } catch (Exception e) {
            // URL解析错误
            log.error("getFileExtensionFromURL urlString:{}",urlString,e);
        }
        return ""; // 无法提取文件扩展名
    }

    public  String downloadImage(String url, String destinationFile,int count) {
        if(count>3){
            log.error("同步url 下载图片，url:{}",url);
        }
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        // 创建所有主机名都有效的 HostnameVerifier
//        HostnameVerifier allHostsValid = (hostname, session) -> true;
//        // 安装 HostnameVerifier
//        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                File outputFile = new File(imagePath+"/"+destinationFile);

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
                    return "";
                } else {
                    EntityUtils.consume(entity);
                    return destinationFile;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            downloadImage(url,destinationFile,count+1);
        }
        return "";
    }

    public static void main(String args[]){
        String str="https://1117.plmn5.com/uploadfile/202311/24/B5165933146.jpg";
        System.out.println(getContentHash("https://1117.plmn5.com/uploadfile/202311/3/3E111144727.jpg"));
    }

    public static String getContentHash(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content.getBytes("UTF-8"));
            String utl = new java.math.BigInteger(1, hash).toString();
            System.out.println(content + " " + utl);
            return utl;
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    public static String getContentHash16(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content.getBytes("UTF-8"));
            String titleHash = bytesToHex(hash);
            String utl = new java.math.BigInteger(1, hash).toString();
            System.out.println(content + " " + utl);
            return utl;
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
