package com.telegram.bot.command.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.telegram.bot.command.CommandHandler;
import com.telegram.bot.command.Commands;

@Component
public class StartCommandHandler implements CommandHandler {

    private final String HELLO_MESSAGE = """
            Привет, %s! Меня зовут Асклепий. Я воплощение бога медицины, а также твой медицинский помощник, готовый ответить на все твои вопросы о здоровье. Здесь ты можешь получить информацию о симптомах, заболеваниях, лекарствах и многом другом.
            
            Список команд:
            
            /info - Информация о моих возможностях
            /clear - Очистка контекста
            
            Примеры вопросов, которые ты можешь задать:
            
            🤧 "Как лечить простуду в домашних условиях?"
            💉 "Какие есть побочные эффекты у этого лекарства?"
            🏥 "Что делать при повышенной температуре у ребенка?"
            
            Твои вопросы будут обработаны конфиденциально и с максимальной точностью. Просто напиши свой вопрос, и я сразу начну помогать тебе!
            """;

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(HELLO_MESSAGE.formatted(
                        message.getChat().getFirstName()
                ))
                .build();
    }

    @Override
    public Commands getSupportedCommand() {
        return Commands.START_COMMAND;
    }
}
