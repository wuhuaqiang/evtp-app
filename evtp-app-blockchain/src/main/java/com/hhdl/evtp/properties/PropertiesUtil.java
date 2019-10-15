package com.hhdl.evtp.properties;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * 读取Properties文件方法类
 * cwx
 * 2018/5/14
 */
public class PropertiesUtil {
    /**
     * 文件上传的Properties路径
     */
    public static final String FILE_UPLOAD_PATH = "configure/driverpicurl.properties";

    /**
     * 设置Properties文件名字和路径
     *
     * @param propertiesPath
     * @param urlName        你要获取Properties中某个值得key
     * @return
     */
    public static String getUrlValue(String propertiesPath, String urlName) {
        String url = null;
        Properties prop = new Properties();
        try {
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();// 读取属性文件xxxxx.properties
            InputStream in = classLoader.getResourceAsStream(propertiesPath);
            prop.load(in); /// 加载属性列表
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                if (it.next().equals(urlName)) {
                    url = prop.getProperty(urlName);
                }
            }
            in.close();
        } catch (Exception e) {
        }
        return url;
    }


    public static void main(String[] args) {
        //   System.out.print(new PropertiesUtil().getUrlValue("getPicUrl"));
        //   new PropertiesUtil().getUrlValue("getPicUrl"); // 获取a.url的值
    }
}

