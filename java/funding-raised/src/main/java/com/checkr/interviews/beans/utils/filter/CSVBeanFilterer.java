package com.checkr.interviews.beans.utils.filter;

import com.checkr.interviews.beans.ParsedCSVBean;

public interface CSVBeanFilterer<T extends ParsedCSVBean> {
    boolean testFilter(T filteringBean, T actualBean);
}
