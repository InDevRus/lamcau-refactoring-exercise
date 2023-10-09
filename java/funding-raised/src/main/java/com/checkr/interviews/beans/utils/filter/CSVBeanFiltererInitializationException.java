package com.checkr.interviews.beans.utils.filter;

import lombok.experimental.StandardException;

@StandardException
public class CSVBeanFiltererInitializationException extends RuntimeException {
    static final String GETTER_NOT_FOUND_TEMPLATE =
            "{0} getter of {1} class model must be public and annotated with FilteringElement.";

    static final String EMPTY_BEAN_TEMPLATE =
            "{0} class model should have at least one field annotated with having public getter annotated with FilteringElement.";
}
