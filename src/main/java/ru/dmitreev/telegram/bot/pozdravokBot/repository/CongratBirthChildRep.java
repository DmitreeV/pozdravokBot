package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratBirthChildRep {
    private final ArrayList<String> quoteList;

    public CongratBirthChildRep() {
        quoteList = new ArrayList<>();
        parser("https://pozhelator.ru/pozdravleniya-s-rozhdeniem-rebenka-v-proze/",
                "trd", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
