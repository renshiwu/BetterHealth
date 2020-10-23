package com.rsw.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by RSW on 2018/7/9.
 * com.rsw.util
 */

public class JSoupTest {
    public static void main(String args[]) throws IOException {
        String baseUrl = "http://www.bzjw.com/standard/";
        String ajaxUrl = "http://www.bzjw.com/include/getinfo.asp?info_x=stdsort&class_x=GB%B9%FA%B1%EA&order_x=";

        Document baseDoc = getDoc("http://www.bzjw.com/standard/showstd.asp?id_x=1");
        System.out.println(baseDoc.select(".sort_title_ul li").text());

        for (int i = 1; i < 13; i++) {
            String aUrl = ajaxUrl + i;
            System.out.println(aUrl);
            Document ajaxDoc = getDoc(aUrl);
            System.out.println(ajaxDoc.select("a").text());
            for (Element e : ajaxDoc.select("a")) {
                System.out.println(baseUrl + e.attr("href"));
                Document picDoc = getDoc(baseUrl + e.attr("href"));
                System.out.println("文字信息：\n" + picDoc.select(".std_detail_c").text());
                String imageName = picDoc.select(".std_detail_c strong").get(1).text();
                System.out.println(imageName);
                System.out.println("图片地址：" + picDoc.select(".std_img_c a").attr("href"));
                String stdImg = picDoc.select(".std_img_c a").attr("href");
                getImg(stdImg, imageName);
            }
        }

    }

    private static void getImg(String img, String name) {
        try {
            //根据String形式创建一个URL对象，
            URL url = new URL(img);
            //实列一个URLconnection对象，用来读取和写入此 URL 引用的资源
            URLConnection con = url.openConnection();
            //获取一个输入流
            InputStream is = con.getInputStream();
            //实列一个输出对象
            FileOutputStream fos = new FileOutputStream("../image/" + name + ".jpg");
            //一个byte[]数组，一次读取多个字节
            byte[] bt = new byte[1024];
            //用来接收每次读取的字节个数
            int b = 0;
            //循环判断，如果读取的个数b为空了，则is.read()方法返回-1，具体请参考InputStream的read();
            while ((b = is.read(bt)) != -1) {
                //将对象写入到对应的文件中
                fos.write(bt, 0, b);
            }
            //刷新流
            fos.flush();
            //关闭流
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document getDoc(String url) {
        try {
            Document doc = Jsoup
                    .connect(url)
                    .timeout(30000)
                    .get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    /**
     * 从字符串中获取数字
     *
     * @param id
     * @return
     */
    private static int getNum(String id) {
        String str = id.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }

        }
        System.out.println(Integer.parseInt(str2));
        return Integer.parseInt(str2);
    }

}
