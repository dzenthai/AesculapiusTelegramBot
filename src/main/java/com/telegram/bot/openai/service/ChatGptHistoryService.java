package com.telegram.bot.openai.service;

import com.telegram.bot.openai.api.GptHistory;
import com.telegram.bot.openai.api.Message;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.DisposableBean;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ChatGptHistoryService implements DisposableBean {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String HISTORY_KEY_PREFIX = "chatHistory:";

    private String getHistoryKey(Long userId) {
        return HISTORY_KEY_PREFIX + userId;
    }

    public Optional<GptHistory> getUserHistory(Long userId) {
        log.info("Fetching history for user: {}", userId);
        GptHistory chatHistory = (GptHistory) redisTemplate.opsForValue().get(getHistoryKey(userId));
        log.info("Fetched history: {}", chatHistory);
        return Optional.ofNullable(chatHistory);
    }

    public void createHistory(Long userId) {
        log.info("Creating history for user: {}", userId);
        redisTemplate.opsForValue().set(getHistoryKey(userId), new GptHistory(new ArrayList<>()));
        log.info("History created for user: {}", userId);
    }

    public void clearHistory(Long userId) {
        log.info("Clearing history for user: {}", userId);
        redisTemplate.delete(getHistoryKey(userId));
        log.info("History cleared for user: {}", userId);
    }

    public void addUserMessageToHistory(Long userId, Message message) {
        log.info("Adding user message to history for user: {}", userId);
        GptHistory chatHistory = (GptHistory) redisTemplate.opsForValue().get(getHistoryKey(userId));
        if (chatHistory == null) {
            throw new IllegalStateException("History not exists for user = %s".formatted(userId));
        }
        chatHistory.chatMessages().add(message);
        redisTemplate.opsForValue().set(getHistoryKey(userId), chatHistory);
        log.info("User message added to history for user: {}", userId);
    }

    public void addBotMessageToHistory(Long userId, Message message) {
        log.info("Adding bot message to history for user: {}", userId);
        GptHistory chatHistory = (GptHistory) redisTemplate.opsForValue().get(getHistoryKey(userId));
        if (chatHistory == null) {
            throw new IllegalStateException("History not exists for user = %s".formatted(userId));
        }
        chatHistory.chatMessages().add(message);
        redisTemplate.opsForValue().set(getHistoryKey(userId), chatHistory);
        log.info("Bot message added to history for user: {}", userId);
    }

    public void createHistoryIfNotExist(Long userId) {
        log.info("Checking if history exists for user: {}", userId);
        if (redisTemplate.opsForValue().get(getHistoryKey(userId)) == null) {
            createHistory(userId);
            log.info("History created for user: {}", userId);
        }
    }

    @Override
    public void destroy() throws Exception {
        clearAllHistory();
    }

    public void clearAllHistory() {
        log.info("Clearing all histories");
        Set<String> keys = redisTemplate.keys(HISTORY_KEY_PREFIX + "*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
        log.info("All histories cleared");
    }
}
