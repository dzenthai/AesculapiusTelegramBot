package com.telegram.bot.command.handler;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.telegram.bot.command.CommandHandler;
import com.telegram.bot.command.Commands;
import com.telegram.bot.openai.service.ChatGptHistoryService;

@Component
@AllArgsConstructor
public class ClearChatHistoryCommandHandler implements CommandHandler {

    private final ChatGptHistoryService chatGptHistoryService;

    private final String CLEAR_HISTORY_MESSAGE = "Контекст успешно очищен";

    @Override
    public BotApiMethod<?> processCommand(Message message) {
        chatGptHistoryService.clearHistory(message.getChatId());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(CLEAR_HISTORY_MESSAGE)
                .build();
    }

    @Override
    public Commands getSupportedCommand() {
        return Commands.CLEAR_COMMAND;
    }
}
