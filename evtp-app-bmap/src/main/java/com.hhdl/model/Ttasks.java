package com.hhdl.model;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Ttasks implements Serializable {
    public static ConcurrentHashMap<String, Queue<TPoint>> usersTask = new ConcurrentHashMap<String, Queue<TPoint>>();
}
