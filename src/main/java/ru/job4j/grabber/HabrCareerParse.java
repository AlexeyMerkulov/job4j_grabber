package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        try {
            for (int i = 1; i < 6; i++) {
                Connection connection = Jsoup.connect(link + String.format("?page=%s", i));
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> {
                    Post post = getPost(row);
                    list.add(post);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private Post getPost(Element element) {
        Element dateElement = element.select(".vacancy-card__date").first();
        Element titleElement = element.select(".vacancy-card__title").first();
        Element linkElement = titleElement.child(0);
        String date = dateElement.child(0).attr("datetime");
        String vacancyName = titleElement.text();
        String vacancyLink = String.format("%s%s", "https://career.habr.com", linkElement.attr("href"));
        String description = retrieveDescription(vacancyLink);
        LocalDateTime dateTime = dateTimeParser.parse(date);
        return new Post(vacancyName, vacancyLink, description, dateTime);
    }

    private String retrieveDescription(String link) {
        StringBuilder sb = new StringBuilder();
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            Element description = document.selectFirst(".style-ugc");
            for (Element el : description.children()) {
                sb.append(el.text());
                sb.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
