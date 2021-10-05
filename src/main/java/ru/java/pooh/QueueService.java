package ru.java.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String temp = req.name();
        Resp rsl = new Resp("Failed", 222);
        if ("queue".equals(req.mode())) {
            queue.putIfAbsent(temp, new ConcurrentLinkedQueue<>());
            if ("POST".equals(req.method())) {
                queue.get(temp).add(req.text());
                rsl = new Resp("Post add: " + req.text(), 200);
            } else if ("GET".equals(req.method())) {
                String message = queue.get(temp).poll();
                rsl = message == null ? rsl : new Resp(message, 200);
            }
        }
        return rsl;
    }
}
