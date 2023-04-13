package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratulationsHbMomRepository {
    private final ArrayList<String> quoteList;

    public CongratulationsHbMomRepository() {
        quoteList = new ArrayList<>();
        parser("https://pozdravim.net/s-dnem-rozhdeniya/mame/v-proze/",
                "entry-summary entry-content", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
