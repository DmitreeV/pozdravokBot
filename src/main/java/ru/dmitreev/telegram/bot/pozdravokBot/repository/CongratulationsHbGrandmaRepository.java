package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratulationsHbGrandmaRepository {
    private final ArrayList<String> quoteList;

    public CongratulationsHbGrandmaRepository() {
        quoteList = new ArrayList<>();
        parser("https://pozdravim.net/s-dnem-rozhdeniya/babushke/v-proze/",
                "entry-summary entry-content", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}