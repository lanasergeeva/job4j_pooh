package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TopicServiceTest {
    @Test
    public void whenPostThenGetTopic() {
        TopicService topicService = new TopicService();
        topicService.process(
                new Req("POST", "topic", "weather", "temperature", "18")
        );
        Resp result = topicService.process(
                new Req("GET", "topic", "weather", "userId", "1")
        );
        assertThat(result.text(), is("18"));
    }


    @Test
    public void whenPostAndGet2() {
        TopicService topicService = new TopicService();
        Req post = Req.of("POST /topic/weather -d temperature=18");
        Req get = Req.of("GET /topic/weather/1");
        topicService.process(post);
        Resp rsl = topicService.process(get);
        Resp expected = new Resp("18", 10);
        assertThat(rsl, is(expected));
    }

    @Test
    public void whenGetFailed() {
        TopicService topicService = new TopicService();
        Req get = Req.of("GET /topic/weather/1");
        Resp rsl = topicService.process(get);
        Resp expected = new Resp("Failed", 0);
        assertThat(rsl, is(expected));
    }
}