package com.checkr.interviews.beans.utils.filter;

import com.checkr.interviews.beans.ParsedCSVBean;

public interface CSVBeanFilterer<T extends ParsedCSVBean> {
    <U extends T> boolean testFilter(T filteringBean, U actualBean);
}
