package com.k4rnaj1k;

import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class App {
    void start() {
        Field[] fields = AppProperties.class.getFields();
        Properties properties = loadProperties();
        AppProperties appProps = initFields(AppProperties.class, properties);
        System.out.println(appProps);
    }

    private <T> T initFields(Class<T> tClass, Properties properties){
        try {
            T appProps = tClass.getConstructor().newInstance();
            Field[] fields = tClass.getFields();
            for (Field f :
                    fields) {
                PropertyKey key = f.getAnnotation(PropertyKey.class);
                if (key != null) {
                    String property = key.value();
                    setField(f, properties.getProperty(property), appProps);
                }
            }
            return appProps;
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println("Couldn't initialize the object.");
            throw new RuntimeException();
        }
    }

    private static Properties loadProperties() {
        Properties appProperties = new Properties();
        try (FileReader reader = new FileReader("app.properties")) {
            appProperties.load(reader);
            return appProperties;
        } catch (IOException e) {
            System.out.println("Couldn't find or load properties from app.properties, shutting down.");
            throw new UncheckedIOException(e);
        }
    }

    private <T> void setField(Field field, String value, T object) {
        Class<?> fieldsType = field.getType();
        try {
            if (fieldsType.isAssignableFrom(long.class)) {
                field.set(object, Long.parseLong(value));
            } else if (fieldsType.isAssignableFrom(int.class)) {
                field.set(object, Integer.parseInt(value));
            } else if (fieldsType.isAssignableFrom(double.class)) {
                field.set(object, Double.parseDouble(value));
            } else if (fieldsType.isAssignableFrom(float.class)) {
                field.set(object, Float.parseFloat(value));
            } else if (fieldsType.isAssignableFrom(char.class)) {
                field.set(object, value.charAt(0));
            } else if (fieldsType.isAssignableFrom(byte.class)) {
                field.set(object, Byte.parseByte(value));
            } else {
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            System.out.println("Couldn't access the field " + field.getName());
        }
    }
}
