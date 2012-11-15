package com.ssp.clubhouse.jetlang;

import org.jetlang.channels.Publisher;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 11:09 PM
 */
public class NullPublisher<T> implements Publisher<T> {
    @Override
    public void publish(T msg) {
    }
}
