package ru.java.pooh;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final String key;
    private final String value;

    public Req(String method, String mode, String queue, String key, String value) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.key = key;
        this.value = value;
    }

    public static Req of(String content) {
        String[] array = {"\\A\\w+", "topic|queue", "weather", "\\w+=|\\w+/\\w+/\\d++", "=\\w+|\\w+\\Z"};
        for (int i = 0; i < array.length; i++) {
            Pattern pattern = Pattern.compile(array[i]);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                array[i] = matcher.group();
            }
        }
        if (array[0].equals("GET") && array[1].equals("topic")) {
            array[3] = array[3].substring(array[3].lastIndexOf('/') + 1);
        }
        return new Req(array[0], array[1],
                content.contains("weather") ? array[2] : "new_topic",
                array[3].contains("=") ? array[3].substring(0, array[3].indexOf('=')) : array[3],
                array[4].contains("=") ? array[4].substring(array[4].indexOf('=') + 1) : array[4]);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String queue() {
        return queue;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }


    @Override
    public String toString() {
        return "Req{"
                + "method='" + method + '\''
                + ", mode='" + mode + '\''
                + ", queue='" + queue + '\''
                + ", key='" + key + '\''
                + ", value='" + value + '\''
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
        return Objects.equals(method, req.method) && Objects.equals(mode, req.mode) && Objects.equals(queue, req.queue) && Objects.equals(key, req.key) && Objects.equals(value, req.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, mode, queue, key, value);
    }
}
