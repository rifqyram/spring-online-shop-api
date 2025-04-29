package digital.gtech.onlineshop.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class PageUtil {
    public static Pageable createPageable(Integer page, Integer size) {
        page = (page == null || page <= 0) ? 0 : page - 1;
        size = (size == null || size <= 0) ? 10 : size;
        return PageRequest.of(page, size);
    }

    public static Pageable createPageable(Integer page, Integer size, String direction, String sortBy, Class<?> entityClass) {
        page = (page == null || page <= 0) ? 0 : page - 1;
        size = (size == null || size <= 0) ? 10 : size;

        Set<String> allowedProperties = getAllFields(entityClass);

        if (sortBy == null || !allowedProperties.contains(sortBy)) {
            sortBy = "id";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return PageRequest.of(page, size, sort);
    }

    private static Set<String> getAllFields(Class<?> clazz) {
        Set<String> fields = new HashSet<>();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
