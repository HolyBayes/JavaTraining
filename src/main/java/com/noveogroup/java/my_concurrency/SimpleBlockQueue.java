package com.noveogroup.java.my_concurrency;

/**
 * Simple blocking queue interface with only 3 methods
 */
public interface SimpleBlockQueue<T> {
    public T take() throws InterruptedException;
    public void put(final T value) throws InterruptedException;
    public int size();
}
