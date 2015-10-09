package com.gmail.rogermoreta.speedpaintmaze.model;

import java.io.Serializable;

public class Pair implements Serializable {
    public Integer first;
    public Integer second;

    public Pair(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }
}
