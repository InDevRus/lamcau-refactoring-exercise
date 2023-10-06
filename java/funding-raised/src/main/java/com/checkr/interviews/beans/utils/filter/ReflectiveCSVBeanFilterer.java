package com.checkr.interviews.beans.utils.filter;

import com.checkr.interviews.beans.FilteringElement;
import com.checkr.interviews.beans.ParsedCSVBean;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.reflections.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectiveCSVBeanFilterer<T extends ParsedCSVBean> implements CSVBeanFilterer<T> {
    private final Set<Method> getters;

    public static <T extends ParsedCSVBean> ReflectiveCSVBeanFilterer<T> ofClass(Class<T> model) {
        var methodSearchCriteria = ReflectionUtils
                .Methods
                .get(model)
                .filter(method -> method.isAnnotationPresent(FilteringElement.class));
        var getters = ReflectionUtils.get(methodSearchCriteria);

        if (getters.isEmpty()) {
            throw new CSVBeanFiltererInitializationException(CSVBeanFiltererInitializationException.EMPTY_BEAN_MESSAGE_EXCEPTION);
        }

        return new ReflectiveCSVBeanFilterer<>(getters);
    }

    private <U extends T> boolean testSpecificGetter(Method method, T filteringBean, U actualBean) {
        try {
            var filteringValue = method.invoke(filteringBean);
            var actualValue = method.invoke(actualBean);
            return Objects.isNull(filteringValue) || filteringValue.equals(actualValue);
        } catch (InvocationTargetException | IllegalAccessException exception) {
            throw new CSVBeanFilteringException(CSVBeanFilteringException.ACCESSIBILITY_ERROR_MESSAGE, exception);
        }
    }

    @Override
    public <U extends T> boolean testFilter(T filteringBean, U actualBean) {
        return getters.stream().allMatch(method -> testSpecificGetter(method, filteringBean, actualBean));
    }
}