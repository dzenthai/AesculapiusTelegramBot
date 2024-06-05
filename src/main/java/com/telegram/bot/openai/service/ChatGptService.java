package com.telegram.bot.openai.service;

import com.telegram.bot.openai.api.GptHistory;
import com.telegram.bot.openai.api.GptRequest;
import com.telegram.bot.openai.api.Message;
import com.telegram.bot.openai.api.client.OpenAIClient;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatGptService {

    private final OpenAIClient openAIClient;
    @Autowired
    private final ChatGptHistoryService chatGptHistoryService;

    private static final String SYSTEM_MESSAGE = "Ты бот Асклепий, и твоя задача - " +
            "отвечать на вопросы связанные со здоровьем человека. " +
            "Если вопрос не связан со здоровьем, вежливо объясни, " +
            "что ты можешь помочь только с вопросами со здоровьем и не отвечать на другие вопросы.";

    private boolean isGreeting(String userInput) {
        String lowerCaseInput = userInput.toLowerCase();
        return lowerCaseInput.contains("привет") || lowerCaseInput.contains("здравствуйте") || lowerCaseInput.contains("добрый день");
    }

    @Nonnull
    public String getResponseChatForUser(
            Long userId,
            String userTextInput
    ) {
        chatGptHistoryService.createHistoryIfNotExist(userId);

        List<Message> messages = new ArrayList<>(List.of(
                Message.builder()
                        .role("system")
                        .content(SYSTEM_MESSAGE)
                        .build()
        ));

        var history = chatGptHistoryService.getUserHistory(userId).orElseGet(() -> new GptHistory(new ArrayList<>()));
        messages.addAll(history.chatMessages());

        // Add user message to history
        Message userMessage = Message.builder()
                .content(userTextInput)
                .role("user")
                .build();
        chatGptHistoryService.addUserMessageToHistory(userId, userMessage);

        messages.add(userMessage);

        var request = GptRequest.builder()
                .model("gpt-4o")
                .messages(messages)
                .build();

        var response = openAIClient.createChatCompletion(request);

        var messageFromGpt = response.choices().get(0).message();

        // Add bot message to history
        chatGptHistoryService.addBotMessageToHistory(userId, messageFromGpt);

        return messageFromGpt.content();
    }
}
