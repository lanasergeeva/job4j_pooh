package ru.java.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String temp = req.name();
        Resp rsl = new Resp("Failed", 222);
        if (req.mode().equals("topic")) {
            queue.putIfAbsent(temp, new ConcurrentLinkedQueue<>());
            map.putIfAbsent(temp, new ConcurrentHashMap<>());
            if ("POST".equals(req.method())) {
                queue.get(temp).add(req.text());
                map.get(temp).putIfAbsent(req.id(), new ConcurrentLinkedQueue<>(queue.get(temp)));
                rsl = new Resp("Post add " + req.text(), 200);
            } else if ("GET".equals(req.method())) {
                map.get(temp).putIfAbsent(req.id(), new ConcurrentLinkedQueue<>(queue.get(temp)));
                String message = map.get(temp).get(req.id()).poll();
                rsl = message == null ? rsl : new Resp(message, 200);
            }
        }
        return rsl;
    }
}
