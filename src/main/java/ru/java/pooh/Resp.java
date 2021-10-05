package ru.java.pooh;

import java.util.Objects;

public class Resp {
    private final String text;
    private final int status;

    public Resp(String text, int status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public int status() {
        return status;
    }

    @Override
    public String toString() {
        return "Resp{"
                + "text='" + text + '\''
                + ", status=" + status
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
        Resp resp = (Resp) o;
        return status == resp.status && Objects.equals(text, resp.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, status);
    }
}
