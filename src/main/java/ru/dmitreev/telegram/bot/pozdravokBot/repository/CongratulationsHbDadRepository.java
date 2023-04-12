package ru.dmitreev.telegram.bot.pozdravokBot.repository;

import java.util.ArrayList;
import java.util.List;

import static ru.dmitreev.telegram.bot.pozdravokBot.service.Parser.parser;

public class CongratulationsHbDadRepository {

    private final List<String> quoteList;

    public CongratulationsHbDadRepository() {
        quoteList = new ArrayList<>();
        parser("https://pozdravim.net/s-dnem-rozhdeniya/pape/v-proze/",
                "entry-summary entry-content", quoteList);
    }

    public String getRandCongratulations() {
        int randValue = (int) (Math.random() * quoteList.size());
        return quoteList.get(randValue);
    }
}
