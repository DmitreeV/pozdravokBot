package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratEasterRep {
    private final ArrayList<String> quoteList;

    public CongratEasterRep() {
        quoteList = new ArrayList<>();
        parser("https://pzdravik.ru/pozdravleniya-s-pashoy-v-proze/",
                "proza", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
