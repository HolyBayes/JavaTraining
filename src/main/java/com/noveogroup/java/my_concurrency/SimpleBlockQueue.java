package com.noveogroup.java.my_concurrency;

/**
 * Simple blocking queue interface with only 3 methods.
 * @param <T> is queue's element type
 */
public interface SimpleBlockQueue<T> {
    T take() throws InterruptedException;
    void put(final T value) throws InterruptedException;
    int size();
}
