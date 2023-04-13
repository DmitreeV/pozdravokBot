package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratAnniversaryRep {
    private final ArrayList<String> quoteList;

    public CongratAnniversaryRep() {
        quoteList = new ArrayList<>();
        parser("https://smsik.su/s-jubileem/5.html", "tn", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}