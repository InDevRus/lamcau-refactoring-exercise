package com.checkr.interviews.beans.utils.filter;

import com.checkr.interviews.beans.FilteringElement;
import com.checkr.interviews.beans.ParsedCSVBean;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.reflections.ReflectionUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class HandlingCSVBeanFilterer<T extends ParsedCSVBean> implements CSVBeanFilterer<T> {
    private static final MethodHandles.Lookup GETTERS_LOOKUP = MethodHandles.publicLookup();

    private static <T extends ParsedCSVBean> MethodHandle makeGetterHandler(Class<T> model, Method method) {
        try {
            return GETTERS_LOOKUP.findVirtual(model, method.getName(), MethodType.methodType(method.getReturnType()));
        } catch (NoSuchMethodException | IllegalAccessException exception) {
            var message = MessageFormat.format(CSVBeanFiltererInitializationException.GETTER_NOT_FOUND_MESSAGE_TEMPLATE,
                    method.getName(), model.getName()
            );
            throw new CSVBeanFiltererInitializationException(message, exception);
        }
    }

    public static <T extends ParsedCSVBean> HandlingCSVBeanFilterer<T> ofClass(Class<T> model) {
        var getterSearchCriteria = ReflectionUtils
                .Methods
                .get(model)
                .filter(method -> method.isAnnotationPresent(FilteringElement.class))
                .map(getter -> makeGetterHandler(model, getter));

        var getters = ReflectionUtils.get(getterSearchCriteria);

        if (getters.isEmpty()) {
            var message = MessageFormat.format(
                    CSVBeanFiltererInitializationException.EMPTY_BEAN_MESSAGE_EXCEPTION,
                    model.getName());
            throw new CSVBeanFiltererInitializationException(message);
        }

        return new HandlingCSVBeanFilterer<>(getters);
    }

    private final Set<MethodHandle> gettersHandles;

    private <U extends T> boolean testSpecificGetter(MethodHandle getterHandle, T filteringBean, U actualBean) {
        try {
            var filteringValue = getterHandle.invoke(filteringBean);
            var actualValue = getterHandle.invoke(actualBean);
            return filteringValue == null || filteringValue.equals(actualValue);
        } catch (Throwable exception) {
            throw new CSVBeanFilteringException(CSVBeanFilteringException.UNEXPECTED_ERROR_MESSAGE, exception);
        }
    }

    @Override
    public <U extends T> boolean testFilter(T filteringBean, U actualBean) {
        return gettersHandles.stream()
                .allMatch(getterHandle -> testSpecificGetter(getterHandle, filteringBean, actualBean));
    }
}
