# AesculapiusTelegramBot

AesculapiusTelegramBot is a Telegram bot designed to assist users with health-related questions. It leverages ChatGPT under the hood and is built using Java, Spring Boot, Redis, and Docker.

## Features

- **Health Assistance**: Provides answers to health-related questions using ChatGPT.
- **Persistent Context**: Uses Redis to store the conversation context for more coherent and context-aware responses.
- **Voice to Text**: Transcribes voice messages to text and responds accordingly.
- **Simple Commands**: Includes commands to start a conversation, get bot information, and clear the conversation context and cache.
- **Voice Message Support**: Accepts and processes voice messages, converting them to text for further interaction.

## Technologies Used

- **Java**: The primary programming language.
- **Spring Boot**: Used for API key and Telegram token management, as well as for connecting to Redis.
- **Redis**: Stores conversation context to maintain continuity in the dialogue.
- **Docker**: Facilitates containerization for easy deployment.
- **Lombok**: Used for boilerplate code reduction (e.g., getters, setters).
- **Telegram Bots API**: Used for interaction with Telegram.

## Installation

### Prerequisites

- Java 11 or higher
- Docker
- Redis

### Steps

1. Clone the repository:
    ```bash
    git clone https://github.com/dzenthai/AesculapiusTelegramBot
    cd AesculapiusTelegramBot
    ```

2. Update `application.properties` with your API keys and Redis connection details:
    ```properties
    telegram.token=YOUR_TELEGRAM_BOT_TOKEN
    openai.api.key=YOUR_OPENAI_API_KEY
    spring.redis.host=localhost
    spring.redis.port=6379
    ```

3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

4. Run the application using Docker:
    ```bash
    create Dockerfile
    docker-compose up --build
    ```

## Usage

### Commands

- `/start`: Begins a conversation with the bot.
- `/info`: Displays information about the bot.
- `/clear`: Clears the conversation context and cache (Redis).

### Bot Context Configuration

In the `ChatGptService` class, there is a `SYSTEM_MESSAGE` variable that sets the bot's context:
```java
private static final String SYSTEM_MESSAGE = "You are Asclepius bot, and your task is to answer questions related to human health. If the question is not related to health, politely explain that you can only help with health-related questions and do not answer other questions.";
