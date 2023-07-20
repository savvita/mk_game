package com.savita.machi_koro.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class Log {
    private final Consumer<String> logger;
    private String format = "%s : %s : %s";
    public Log(Consumer<String> logger) {
        this.logger = logger;
    }
    public void info(String msg) {
        log(msg, LogLevel.INFO);
    }

    public void warning(String msg) {
        log(msg, LogLevel.WARNING);
    }

    public void error(String msg) {
        log(msg, LogLevel.ERROR);
    }

    private void log(String msg, LogLevel level) {
        logger.accept(String.format(format, level, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), msg));
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
