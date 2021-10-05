package ru.java.pooh;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Req {
    private final String method;
    private final String mode;
    private final String name;
    private final String text;
    private final String id;

    public Req(String method, String mode, String name, String text, String id) {
        this.method = method;
        this.mode = mode;
        this.name = name;
        this.text = text;
        this.id = id;
    }

    public static Req of(String content) {
        String[] array = {"\\A\\w+", "topic|queue", "weather", "\\w+=\\w+", "weather/\\w+"};
        for (int i = 0; i < array.length; i++) {
            Pattern pattern = Pattern.compile(array[i]);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                array[i] = matcher.group();
            }
        }
        if (array[0].equals("GET") && array[1].equals("topic")) {
            array[4] = array[4].substring(array[4].lastIndexOf('/') + 1);
        } else {
            array[4] = "0";
        }
        return new Req(array[0], array[1],
                content.contains("weather") ? array[2] : "new_topic",
                array[0].equals("POST") ? array[3].replaceAll("\"", " ").trim() : "", array[4]);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String name() {
        return name;
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }


    @Override
    public String toString() {
        return "Req{"
                + "method='" + method + '\''
                + ", mode='" + mode + '\''
                + ", name='" + name + '\''
                + ", text='" + text + '\''
                + ", id='" + id + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Req req = (Req) o;
        return Objects.equals(method, req.method) && Objects.equals(mode, req.mode) && Objects.equals(name, req.name) && Objects.equals(text, req.text) && Objects.equals(id, req.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, mode, name, text, id);
    }
}
