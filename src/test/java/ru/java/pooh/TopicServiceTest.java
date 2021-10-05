package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenPostAndGet() {
        TopicService topicService = new TopicService();
        Req post = Req.of("POST /topic/weather -d \"temperature=18\"");
        Req get = Req.of("GET /topic/weather/1");
        topicService.process(post);
        Resp rsl = topicService.process(get);
        Resp expected = new Resp("temperature=18", 200);
        assertThat(rsl, is(expected));
    }

    @Test
    public void whenGetFailed() {
        TopicService topicService = new TopicService();
        Req get = Req.of("GET /topic/weather/");
        Resp rsl = topicService.process(get);
        Resp expected = new Resp("Failed", 222);
        assertThat(rsl, is(expected));
    }
}
