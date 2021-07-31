package com.k4rnaj1k;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVMapper<T> {

    public List<T> getMapped(Class<T> o, CSVParser parser) {
        ArrayList<T> res = new ArrayList<>();
        try {
            for (int i = 1; i <= parser.getSize(); i++) {
                T temp = o.getDeclaredConstructor().newInstance();
                Field[] fields = o.getDeclaredFields();
                for (Field f :
                        fields) {
                    CSVHeader header = f.getAnnotation(CSVHeader.class);
                    if (header != null) {
                        String headerString = header.value();
                        setField(f, parser.get(i, headerString), temp);
                    }
                }
                res.add(temp);
            }
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    private void setField(Field field, String value, T o) {
        Class<?> fieldsType = field.getType();
        try {
            if (fieldsType.isAssignableFrom(long.class)) {
                field.set(o, Long.parseLong(value));
            } else if (fieldsType.isAssignableFrom(int.class)) {
                field.set(o, Integer.parseInt(value));
            } else if (fieldsType.isAssignableFrom(double.class)) {
                field.set(o, Double.parseDouble(value));
            } else if (fieldsType.isAssignableFrom(float.class)) {
                field.set(o, Float.parseFloat(value));
            } else if (fieldsType.isAssignableFrom(char.class)) {
                field.set(o, value.charAt(0));
            } else if (fieldsType.isAssignableFrom(byte.class)) {
                field.set(o, Byte.parseByte(value));
            } else {
                field.set(o, value);
            }
        } catch (IllegalAccessException e) {
            System.out.println("Couldn't access the field " + field.getName());
        }
    }
}
