package com.checkr.interviews.utils.filter;

import com.checkr.interviews.utils.beans.ParsedCSVBean;

public interface CSVBeanFilterer<T extends ParsedCSVBean> {
    boolean testFilter(T filteringBean, T actualBean);
}
