package com.noveogroup.java.my_concurrency;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by artem on 28.07.14.
 */
public class MyBlockingQueue<T> {

    private List<T> queue = new LinkedList<T>();
    private int  limit = 10;

    public MyBlockingQueue(int limit){
        this.limit = limit;
    }


    public synchronized void put(T item) throws InterruptedException  {
        while (this.queue.size() == this.limit) {
            wait();
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(item);
    }

    public int size(){
        return queue.size();
    }
    public synchronized T take() throws InterruptedException{
        while (this.queue.size() == 0){
            wait();
        }
        if (this.queue.size() == this.limit){
            notifyAll();
        }

        return this.queue.remove(0);
    }
}