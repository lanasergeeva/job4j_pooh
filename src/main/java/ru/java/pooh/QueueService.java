package ru.java.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String temp = req.queue();
        Resp rsl = new Resp("Failed", 0);
        if ("queue".equals(req.mode())) {
            queue.putIfAbsent(temp, new ConcurrentLinkedQueue<>());
            if (req.method().equals("POST")) {
                queue.get(temp).add(req.value());
                rsl = new Resp("Post", 5);
            } else if (req.method().equals("GET")) {
                rsl = new Resp(queue.get(temp).poll(), 10);
            }
        }
        return rsl;
    }
}
