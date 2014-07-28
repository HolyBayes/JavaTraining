package com.noveogroup.java;


import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.serialize.Serializer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


class Reader implements Runnable {

    private final java.util.concurrent.BlockingQueue _queue;
    private Serializer _serializer;
    private AtomicBoolean _flag;//true till Reader works
    private final MyBlockingQueue _myQueue;
    private int _mode;
    public Reader(java.util.concurrent.BlockingQueue queue,
                  MyBlockingQueue myQueue,
                  Serializer serializer,
                  AtomicBoolean flag,
                  int mode) {
        this._queue = queue;
        this._serializer=serializer;
        this._flag=flag;
        this._mode=mode;
        this._myQueue=myQueue;
    }

    @Override
    public void run(){
        _flag.set(true);
        System.out.println("[Reader] run\n");
        while(_flag.get()) {
            try {
                Object obj = _serializer.load();
                if(_mode==0) {
                    _queue.put(obj);
                }
                else{
                    _myQueue.put(obj);
                }
            }
            catch (InterruptedException e){
                System.out.print("Interrupted exception in put()");
            }
            catch (ClassNotFoundException e) {
                System.out.print(e.getMessage());
            }
            catch (IOException e) {
                System.out.print("[Reader] finished\n");
                _flag.set(false);
            }
        }
    }

    private Integer produce() {
//        System.out.println("[Producer] produce: " + i);
        return 0;
    }
}
