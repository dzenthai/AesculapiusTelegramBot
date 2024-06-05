package com.telegram.bot.command.handler;

import com.telegram.bot.command.CommandHandler;
import com.telegram.bot.command.Commands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class AboutCommandHandler implements CommandHandler {
    private final String ABOUT_COMMAND = """
            Информация о боте:
            
            Привет! Я Асклепий, твой медицинский помощник. Мои возможности включают:
            - Предоставление информации о симптомах различных заболеваний.
            - Описание распространенных заболеваний и их лечение.
            - Информация о лекарствах, их применении и побочных эффектах.
            - Советы по здоровому образу жизни, включая питание и физическую активность.
            - Оказание поддержки и консультации по медицинским вопросам.
            
            Просто напишите мне свой вопрос, и я сразу начну помогать вам!
            """;

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(ABOUT_COMMAND)
                .build();
    }

    @Override
    public Commands getSupportedCommand() {
        return Commands.ABOUT_COMMAND;
    }
}
