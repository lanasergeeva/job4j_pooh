package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QueueServiceTest {

    @Test
    public void whenPost() {
        QueueService queueService = new QueueService();
        Req req = Req.of("POST /queue/weather -d \"temperature=18\"");
        Resp rsl = queueService.process(req);
        assertThat(rsl.text(), is("Post add: temperature=18"));
        assertThat(rsl.status(), is(200));
    }

    @Test
    public void whenPostWithoutTopic() {
        QueueService queueService = new QueueService();
        Req req = Req.of("POST /queue -d \"temperature=18\"");
        Resp rsl = queueService.process(req);
        assertThat(req.name(), is("new_topic"));
        assertThat(rsl.text(), is("Post add: temperature=18"));
        assertThat(rsl.status(), is(200));
    }

    @Test
    public void whenGet() {
        QueueService queueService = new QueueService();
        Req req = Req.of("POST /queue/weather -d \"temperature=18\"");
        queueService.process(req);
        Req get = Req.of("GET http://localhost:9000/queue/weather");
        Resp rsl = queueService.process(get);
        assertThat(rsl.text(), is("temperature=18"));
        assertThat(rsl.status(), is(200));
    }

    @Test
    public void whenGetFailed() {
        QueueService queueService = new QueueService();
        Req req = Req.of("GET /queue/weather");
        Resp rsl = queueService.process(req);
        assertThat(rsl.text(), is("Failed"));
        assertThat(rsl.status(), is(222));
    }
}
