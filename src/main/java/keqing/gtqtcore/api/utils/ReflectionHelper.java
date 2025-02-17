package keqing.gtqtcore.api.utils;

import java.lang.reflect.Field;

public class ReflectionHelper {

    public static <T> T getPrivateFieldValue(Object instance, String fieldName, Class<T> fieldType) {
        try {
            // 获取类的 Class 对象
            Class<?> clazz = instance.getClass();

            // 使用反射获取字段
            Field field = clazz.getDeclaredField(fieldName);

            // 设置字段为可访问（如果它是私有的）
            field.setAccessible(true);

            // 获取字段的值
            return fieldType.cast(field.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}