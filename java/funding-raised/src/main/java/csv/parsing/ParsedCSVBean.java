package csv.parsing;

import com.opencsv.bean.CsvBindByName;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class ParsedCSVBean {
    @SneakyThrows
    private static <T extends ParsedCSVBean> boolean checkSpecificField(T filteringBean, T actualBean, Field field) {
        var filteringValue = field.get(filteringBean);
        var actualValue = field.get(actualBean);
        return Objects.isNull(filteringValue) || filteringValue.equals(actualValue);
    }

    public static <T extends ParsedCSVBean> boolean testFilter(T filteringBean, T actualBean) {
        return ReflectionUtils.get(ReflectionUtils.Fields
                        .get(filteringBean.getClass())
                        .filter(field -> field.isAnnotationPresent(CsvBindByName.class)))
                .stream()
                .peek(field -> field.setAccessible(true))
                .allMatch(field -> checkSpecificField(filteringBean, actualBean, field));
    }
}
