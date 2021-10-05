package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {
    @Test
    public void whenPostMethod() {
        String content = "POST /topic/weather -d temperature=18";
        Req req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.key(), is("temperature"));
        assertThat(req.value(), is("18"));
    }


    @Test
    public void whenGetMethod() {
        String content = "GET /topic/weather/1";
        Req req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.key(), is("1"));
    }
}