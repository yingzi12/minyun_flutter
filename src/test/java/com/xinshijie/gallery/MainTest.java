package com.xinshijie.gallery;

//import com.hankcs.hanlp.HanLP;

import com.xinshijie.gallery.common.ServiceException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainTest {
    public static void main(String[] args) {
        String text = "待分词的文本[XiuRen秀人网]第7675期陆萱萱写真[XiuRen秀人网]第7665期娇喘JC写真\n";
//        System.out.println(HanLP.newSegment().enableNameRecognize(true).seg(text));
        try {
            File file = new File("/Users/luhuang/Documents/git/gallery2/data/vedio/gellery.png");
            if (!file.exists()) {
                System.out.println("源文件不存在: " + file.getAbsolutePath());
            }
            // 转换为WebP格式
            float outputQuality = 0.8f; // 可以根据需要设置压缩质量
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                System.out.println("无法读取图像文件: " + file.getAbsolutePath());
            }
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionQuality(outputQuality);

            // 确保输出文件存在
            File outputFile = new File("/Users/luhuang/Documents/git/gallery2/data/vedio/2024-01-19/test.webp");
            outputFile.getParentFile().mkdirs(); // 创建父目录
            outputFile.createNewFile(); // 创建文件

            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            if (ios == null) {
                System.out.println("-------------错误-----------");
                throw new ServiceException("无法创建 ImageOutputStream");
            }

            try {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(image, null, null), param);
            } finally {
                ios.close();
                writer.dispose();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
