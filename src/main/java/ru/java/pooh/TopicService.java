package ru.java.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String temp = req.queue();
        Resp rsl = new Resp("Failed", 0);
        if (req.mode().equals("topic")) {
            queue.putIfAbsent(temp, new ConcurrentLinkedQueue<>());
            map.putIfAbsent(temp, new ConcurrentHashMap<>());
            if ("POST".equals(req.method())) {
                queue.get(temp).add(req.value());
                map.get(temp).putIfAbsent(req.key(), new ConcurrentLinkedQueue<>(queue.get(temp)));
                rsl = new Resp("Post", 5);
            } else if ("GET".equals(req.method())) {
                map.get(temp).putIfAbsent(req.key(), new ConcurrentLinkedQueue<>(queue.get(temp)));
                String message = map.get(temp).get(req.key()).poll();
                rsl = message == null ? rsl : new Resp(message, 10);
            }
        }
        return rsl;
    }
}
