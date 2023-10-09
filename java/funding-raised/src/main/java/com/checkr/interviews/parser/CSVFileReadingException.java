package com.checkr.interviews.parser;

import lombok.experimental.StandardException;

@StandardException
public class CSVFileReadingException extends RuntimeException {
    static final String FILE_READING_ERROR_TEMPLATE = "An error occured while reading file by path {0}";
}
