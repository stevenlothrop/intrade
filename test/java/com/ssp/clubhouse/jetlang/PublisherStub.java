package com.ssp.clubhouse.jetlang;

import org.jetlang.channels.Publisher;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 11:07 PM
 */
public class PublisherStub<T> implements Publisher<T> {
    public List<T> messages= newArrayList();
    @Override
    public void publish(T msg) {
        messages.add(msg);
    }
}
