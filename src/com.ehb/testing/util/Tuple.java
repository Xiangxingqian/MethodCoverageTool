package com.ehb.testing.util;

/**
 * Created by xiangxingqian on 2018/1/14.
 */
public class Tuple<F, S> {

    private F first;
    private S second;

    public Tuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }

    public void setSecond(S second) {
        this.second = second;
    }
}
