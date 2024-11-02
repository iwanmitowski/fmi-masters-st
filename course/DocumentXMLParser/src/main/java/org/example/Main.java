package org.example;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        System.out.println(parseObject(new Item()));
    }

    public static String parseObjectToJSON(Object parsableObject) throws IllegalAccessException {
        var parsableObjectClass = parsableObject.getClass();
        var isJsonEntityAnnotationPresent =  parsableObjectClass.isAnnotationPresent(org.example.JsonEntity.class);

        if (!isJsonEntityAnnotationPresent) {
            return "{}";
        }

        var fields = parsableObjectClass.getDeclaredFields();
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (Field field : fields) {
            field.setAccessible(true);

            var isAnnotationPresent =  field.isAnnotationPresent(org.example.JsonField.class);
            if (!isAnnotationPresent) {
                continue;
            }

            var annotation = field.getAnnotation(org.example.JsonField.class);
            var fieldName = annotation.title().isBlank()
                    ? field.getName()
                    : annotation.title();

            jsonBuilder.append(String.format("\"%s\":", fieldName));

            if (annotation.expectedType().equals(JsonType.STRING)) {
                jsonBuilder.append(String.format("\"%s\",", field.get(parsableObject)));
            } else if (annotation.expectedType().equals(JsonType.PLAIN)) {
                jsonBuilder.append(String.format("\"%s\",", fieldName));
            }
        }

        // Ignore the last comma
        var result = jsonBuilder
            .substring(0, jsonBuilder.length()-1);

        return result + "}";
    }

    public static String parseObject(Object parsableObject) throws IllegalAccessException {
        var parsableObjectClass = parsableObject.getClass();
        var fields = parsableObjectClass.getDeclaredFields();

        StringBuilder xmlBuilder = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);

            var isAnnotationPresent =  field.isAnnotationPresent(org.example.Documentable.class);
            if (isAnnotationPresent) {
                var documentableAnnotation = field.getAnnotation(org.example.Documentable.class);

                ;
                var fieldName = documentableAnnotation.title().isBlank()
                    ? field.getName()
                    : documentableAnnotation.title();

                xmlBuilder.append("<")
                        .append(fieldName)
                        .append(">")
                        .append(field.get(parsableObject))
                        .append("</")
                        .append(fieldName)
                        .append(">");
            }
        }

        return xmlBuilder.toString();
    }
}