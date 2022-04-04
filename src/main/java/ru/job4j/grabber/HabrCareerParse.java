package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HabrCareerParse {

    private String retrieveDescription(String link) throws IOException {
        StringBuilder sb = new StringBuilder();
        Connection connection = Jsoup.connect(link);
        Document document = connection.get();
        Element description = document.selectFirst(".style-ugc");
        for (Element el : description.children()) {
             sb.append(el.text());
             sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
