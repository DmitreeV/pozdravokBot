package ru.dmitreev.telegram.bot.pozdravokBot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class Parser {

    public static void parser(String strURL, String divClass, List<String> list) {
        Document doc = null;
        try {
            doc = Jsoup.connect(strURL).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        Elements elQuote = doc.getElementsByClass(divClass);

        elQuote.forEach(el -> {
            list.add(el.text());
        });
    }
}
