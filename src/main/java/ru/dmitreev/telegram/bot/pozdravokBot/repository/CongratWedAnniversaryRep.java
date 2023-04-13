package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratWedAnniversaryRep {
    private final ArrayList<String> quoteList;

    public CongratWedAnniversaryRep() {
        quoteList = new ArrayList<>();
        parser("https://mirizoter.ru/pozdravleniya/s-dnem-svadby/item/551-pozdravlenie-s-godovshchinoj-svadby-v-proze",
                "pozdravleniya", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}