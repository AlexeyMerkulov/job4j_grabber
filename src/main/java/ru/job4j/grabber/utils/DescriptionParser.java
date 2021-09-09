package ru.job4j.grabber.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DescriptionParser {

    private String parseInfo(String link, String marker, int elementIndex) {
        String info = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(marker);
            Element desc = row.get(elementIndex);
            info = desc.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public String getDescription(String link) {
        return parseInfo(link, ".msgBody", 1);
    }

    public String getCreatedData(String link) {
        String data = parseInfo(link, ".msgFooter", 0);
        String[] dataArray = data.split(" \\[");
        return dataArray[0];
    }

    public String getPostName(String link) {
        return parseInfo(link, ".messageHeader", 0);
    }
}
