package com.checkr.interviews.utils.filter;

import lombok.experimental.StandardException;

@StandardException
public class CSVBeanFilteringException extends RuntimeException {
    final static String UNEXPECTED_ERROR_MESSAGE = "Unexpected error has happened while applying filter.";

    final static String ACCESSIBILITY_ERROR_MESSAGE = "Access to the certain bean element is restricted.";
}
