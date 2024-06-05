package com.telegram.bot.command;

public enum Commands {
    START_COMMAND("/start"),
    CLEAR_COMMAND("/clear"),
    ABOUT_COMMAND("/info");

    private final String commandValue;

    Commands(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getCommandValue() {
        return commandValue;
    }
}
