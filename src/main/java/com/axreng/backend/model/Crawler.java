package com.axreng.backend.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Crawler {
    private final Queue<String> urlQueue;
    private final List<String> visitedURLs;

    public Crawler() {
        urlQueue = new LinkedList<>();
        visitedURLs = new ArrayList<>();
    }

    public Queue<String> getUrlQueue() {
        return urlQueue;
    }

    public List<String> getVisitedURLs() {
        return visitedURLs;
    }

}
