package com.hhdl.service.impl;

import com.hhdl.service.ApiService;
import com.hhdl.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private ApiService apiService;

    @Override
    public void testGet() throws Exception {
        for (int i = 1; i < 157; i++) {
            String url = "https://www.autohome.com.cn/bestauto/" + i;
            String html = apiService.getHtml(url);
            Document domcument = Jsoup.parse(html);
            Elements divs = domcument.select("#bestautocontent div.uibox");
            for (Element div : divs) {
                //去重过滤，重复的数据不需要再处理
                String title = div.select("div.uibox-title").first().text();
                //页面
                Map<String, String> carTest = this.getCarTest(div);
                //图片
                String Image = this.getCarImage(div);

                System.out.println(Image);
                System.out.println(carTest.toString());
            }
        }
    }

    public String getCarImage(Element div) {
        List<String> images = new ArrayList<>();
        //获取图片的url
        Elements page = div.select("ul.piclist02 li");
        //遍历评测图片的元素
        for (Element element : page) {
//            System.out.println(element.select("a").attr("href"));
            //获取图片展示地址
            String imagePage = "https:" + element.select("a").attr("href");
            //获取图片展示页面
            String html = this.apiService.getHtml(imagePage);
            //解析图片展示页面
            Document doc = Jsoup.parse(html);
            //获取图片的url地址
            String imageUrl = "https:" + doc.getElementById("img").attr("src");
            //下载
            String image = this.apiService.getImage(imageUrl);
            images.add(image);
        }
        return StringUtils.join(images, ",");
    }

    public Map<String, String> getCarTest(Element div) {
        Map<String, String> restlt = new HashMap<>();
        // 评测车辆标题
        restlt.put("title", div.select("div.uibox-title").first().text());
        // 评测项目-加速(0-100公里/小时),单位毫秒
        String speed = div.select(".tabbox1 dd:nth-child(2) div.dd-div2").first().text();
        restlt.put("speed", speed);
        //评测项目-刹车(100-0公里/小时),单位毫米
        String brake = div.select(".tabbox1 dd:nth-child(3) div.dd-div2").first().text();
        restlt.put("brake", brake);
        // 评测项目-实测油耗(升/100公里),单位毫升
        String oil = div.select(".tabbox1 dd:nth-child(4) div.dd-div2").first().text();
        restlt.put("oil", oil);

        return restlt;
    }
}
