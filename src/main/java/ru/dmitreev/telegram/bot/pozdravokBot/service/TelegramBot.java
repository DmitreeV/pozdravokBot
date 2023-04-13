package ru.dmitreev.telegram.bot.pozdravokBot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dmitreev.telegram.bot.pozdravokBot.repository.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.name}")
    private String botName;

    private CongratulationsHbMomRepository congratulationsHbMomRepository;
    private CongratulationsHbDadRepository congratulationsHbDadRepository;
    private CongratulationsHbSistRepository congratulationsHbSistRepository;
    private CongratulationsHbBroRepository congratulationsHbBroRepository;
    private CongratulationsHbGrandpaRepository congratulationsHbGrandpaRepository;
    private CongratulationsHbGrandmaRepository congratulationsHbGrandmaRepository;
    private CongratAnniversaryRep congratAnniversaryRep;
    private CongratWeddingRep congratWeddingRep;
    private CongratWedAnniversaryRep congratWedAnniversaryRep;
    private CongratBirthChildRep congratBirthChildRep;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public TelegramBot() {
        congratulationsHbMomRepository = new CongratulationsHbMomRepository();
        congratulationsHbDadRepository = new CongratulationsHbDadRepository();
        congratulationsHbSistRepository = new CongratulationsHbSistRepository();
        congratulationsHbBroRepository = new CongratulationsHbBroRepository();
        congratulationsHbGrandpaRepository = new CongratulationsHbGrandpaRepository();
        congratulationsHbGrandmaRepository = new CongratulationsHbGrandmaRepository();
        congratWeddingRep = new CongratWeddingRep();
        congratAnniversaryRep = new CongratAnniversaryRep();
        congratWedAnniversaryRep = new CongratWedAnniversaryRep();
        congratBirthChildRep = new CongratBirthChildRep();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String inMess = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            parseMessage(inMess, chatId);
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

        paramButton();
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

        paramButton();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("С днем рождения");
        row.add("С юбилеем");
        row.add("На свадьбу");
        keyboardRows.add(row);

        row = new KeyboardRow();

        row.add("Годовщина свадьбы");
        row.add("Рождение ребёнка");
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

        paramButton();
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

        row = new KeyboardRow();

        row.add("Назад к категориям");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(replyKeyboardMarkup);
        executeMessage(message);
    }

    private void parseMessage(String messageText, long chatId) {

        switch (messageText) {
            case "/start":
                sendStartMessage(chatId);
                break;
            case "Выбрать категорию":
            case "Назад к категориям":
                sendGetMessage(chatId);
                break;
            case "С днем рождения":
                sendHBMessage(chatId);
                break;
            case "Для мамы":
                sendMessage(chatId, congratulationsHbMomRepository.getRandCongratulations());
                break;
            case "Для папы":
                sendMessage(chatId, congratulationsHbDadRepository.getRandCongratulations());
                break;
            case "Для сестры":
                sendMessage(chatId, congratulationsHbSistRepository.getRandCongratulations());
                break;
            case "Для брата":
                sendMessage(chatId, congratulationsHbBroRepository.getRandCongratulations());
                break;
            case "Для дедушки":
                sendMessage(chatId, congratulationsHbGrandpaRepository.getRandCongratulations());
                break;
            case "Для бабушки":
                sendMessage(chatId, congratulationsHbGrandmaRepository.getRandCongratulations());
                break;
            case "С юбилеем":
                sendMessage(chatId, congratAnniversaryRep.getRandCongratulations());
                break;
            case "На свадьбу":
                sendMessage(chatId, congratWeddingRep.getRandCongratulations());
                break;
            case "Годовщина свадьбы":
                sendMessage(chatId, congratWedAnniversaryRep.getRandCongratulations());
                break;
            case "Рождение ребёнка":
                sendMessage(chatId, congratBirthChildRep.getRandCongratulations());
                break;
            case "Другие события":
                sendMessage(chatId, "Я всё-таки Бот а не писатель, попробуйте справиться без меня, " +
                        "я в Вас верю!");
                break;
            default:
                sendMessage(chatId, "Извините команды не существует");
        }
    }

    private void paramButton() {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }
}
