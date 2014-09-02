package com.noveogroup.java.my_concurrency;

import java.util.LinkedList;
import java.util.List;

/**
 * No comments =).
 * @author artem ryzhikov
 * @param <T> is queue's element type
 */
public class MyBlockingQueue<T> implements SimpleBlockQueue<T> {

    private List<T> queue = new LinkedList<T>();
    private int limit;

    public MyBlockingQueue(final int limit) {
        this.limit = limit;
    }

    @Override
    public synchronized void put(final T item) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(item);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public synchronized T take() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == this.limit) {
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
