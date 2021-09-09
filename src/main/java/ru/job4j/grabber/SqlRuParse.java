package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.DescriptionParser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                Post post = detail(href.attr("href"));
                rsl.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post detail(String link) {
        DescriptionParser dp = new DescriptionParser();
        String postName = dp.getPostName(link);
        String postDescription = dp.getDescription(link);
        LocalDateTime postDate = dateTimeParser.parse(dp.getCreatedData(link));
        return new Post(postName, link, postDescription, postDate);
    }
}
