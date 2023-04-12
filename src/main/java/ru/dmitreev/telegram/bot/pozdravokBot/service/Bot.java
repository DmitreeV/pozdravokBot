package ru.dmitreev.telegram.bot.pozdravokBot.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dmitreev.telegram.bot.pozdravokBot.repository.CongratulationsHbDadRepository;
import ru.dmitreev.telegram.bot.pozdravokBot.repository.CongratulationsRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Bot extends TelegramLongPollingBot {

    final private String BOT_TOKEN = "5845803763:AAFvlL96wLT7CFmBD3z88AkcAyEQhYSBbSM";
    final private String BOT_NAME = "pozdravokBot";

    CongratulationsRepository repository;
    CongratulationsHbDadRepository congratulationsHbDadRepository;
    ReplyKeyboardMarkup replyKeyboardMarkup;

    public Bot() {
        repository = new CongratulationsRepository();
        congratulationsHbDadRepository = new CongratulationsHbDadRepository();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inMess = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            parseMessage(inMess, chatId);
        }
    }

    public void parseMessage(String messageText, long chatId) {

        switch (messageText) {
            case "/start":
                sendStartMessage(chatId);
                break;
            case "Выбрать категорию":
                sendGetMessage(chatId);
                break;
            case "С днем рождения":
                sendHBMessage(chatId);
                break;
            case "Для мамы":
                sendMessage(chatId, repository.getRandCongratulations());
                break;
            case "Для папы":
                sendMessage(chatId, congratulationsHbDadRepository.getRandCongratulations());
                break;
            default:
                //response = "Сообщение не распознано";

        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        executeMessage(message);
    }

    private void sendStartMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Приветствую, я помогу вам поздравить близкого человека. Необходимо выбрать категорию:");

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Выбрать категорию");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(replyKeyboardMarkup);
        executeMessage(message);
    }

    private void sendGetMessage(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Предложены следующие события:");

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("С днем рождения");
        row.add("С юбилеем");
        row.add("На свадьбу");
        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Годовщина свадьбы");
        row.add("Любимым");
        row.add("Другие события");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(replyKeyboardMarkup);
        executeMessage(message);
    }

    private void sendHBMessage(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберете кого хотите поздравить:");

        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Для мамы");
        row.add("Для папы");
        row.add("Для сестры");
        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Для брата");
        row.add("Для дедушки");
        row.add("Для бабушки");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(replyKeyboardMarkup);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("ERROR_TEXT" + e.getMessage());
        }
    }
}
