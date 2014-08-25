package com.noveogroup.java.my_concurrency;

import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * Created by artem on 30.07.14.
 * It's cover of concurrency BlockingQueue for SimpleBlockQueue interface
 * @param <T> is queue's element type
 */
public class BlockingQueue<T> implements SimpleBlockQueue<T> {
    private java.util.concurrent.BlockingQueue queue;

    public BlockingQueue(final int size) {
        this.queue = new ArrayBlockingQueue<T>(size);
    }

    @Override
    public T take() throws InterruptedException {
        return (T) this.queue.take();
    }

    public void put(final T value) throws InterruptedException {
        this.queue.put(value);
    }

    @Override
    public int size() {
        return this.queue.size();
    }
}
