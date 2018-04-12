package com.lexsoft.Heatconduct.parsing;

class ParsingException extends Exception {
    ParsingException(String message) {
        System.err.println("Ошибка парсинга. " + message);
    }
}
