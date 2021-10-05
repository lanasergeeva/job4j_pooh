package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {
    @Test
    public void whenPostMethod() {
        String content = "POST /topic/weather -d \"temperature=18\"";
        Req req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.name(), is("weather"));
        assertThat(req.text(), is("temperature=18"));
        assertThat(req.id(), is("0"));
    }

    @Test
    public void whenGetMethod() {
        String content = "GET /topic/weather/1";
        Req req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.name(), is("weather"));
        assertThat(req.id(), is("1"));
        assertThat(req.text(), is(""));
    }
}