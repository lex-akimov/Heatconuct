package com.lexsoft.parsing;

class ParsingException extends Exception {
    ParsingException(String message) {
        System.err.println("Ошибка парсинга. " + message);
    }
}
