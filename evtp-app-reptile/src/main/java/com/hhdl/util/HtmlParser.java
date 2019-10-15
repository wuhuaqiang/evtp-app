package com.hhdl.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {
    public static Document parse(String resourse) {
        Document doc = Jsoup.parse(resourse);
        return doc;
    }
}
