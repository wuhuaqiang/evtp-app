package com.hhdl.entity;


public class SortedEntry implements Comparable<SortedEntry> {
    public SortedEntry(String key, int val) {
        this.key = key;
        this.val = val;
    }

    private String key;
    private int val;

    @Override
    public int compareTo(SortedEntry o) {
        if (this.val < o.val)
            return 1;
        else return -1;
    }

    @Override
    public String toString() {
        return this.key + ":" + this.val;
    }
}
