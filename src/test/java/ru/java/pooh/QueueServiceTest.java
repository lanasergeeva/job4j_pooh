package ru.java.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();

        queueService.process(
                new Req("POST", "queue", "weather", "temperature", "18")
        );

        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null, null)
        );
        assertThat(result.text(), is("18"));
    }

    @Test
    public void whenPost() {
        QueueService queueService = new QueueService();
        Req req = Req.of("POST /queue/weather -d temperature=18");
        Resp rsl = queueService.process(req);
        assertThat(rsl.text(), is("Post"));
        assertThat(rsl.status(), is(5));
    }

    @Test
    public void whenGetFailed() {
        QueueService queueService = new QueueService();
        Req req = Req.of("GET /topic/weather");
        Resp rsl = queueService.process(req);
        assertThat(rsl.text(), is("Failed"));
        assertThat(rsl.status(), is(0));
    }
}