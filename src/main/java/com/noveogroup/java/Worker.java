package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.validator.ValidatorFactory;
import sun.security.validator.ValidatorException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by artem on 25.07.14.
 */
class Worker implements Runnable {
    private final BlockingQueue<Object> _queue;
    private AtomicBoolean _flag;
    private static Logger log=Logger.getLogger(Worker.class.getName());
    private int correct=0;
    private int incorrect=0;
    private final MyBlockingQueue _myQueue;
    private int _mode;

    public Worker(BlockingQueue<Object> queue,
                  MyBlockingQueue myQueue,
                  AtomicBoolean flag,
                  int mode
                  ) {
        this._queue = queue;
        this._myQueue=myQueue;
        this._flag=flag;
        this._mode=mode;
    }

    @Override
    public void run() {
        System.out.println("[Worker] run\n");
        while((_queue.size()!=0)
                ||(_flag.get())
                ||(_myQueue.size()!=0)){//while (_queue.size!=0 or Reader still works)
            try{
                if(_queue.size()!=0||_myQueue.size()!=0){//Здесь еще можно засыпать если очередь пуста или wait()-notify()
                    System.out.format("%d %d \n",correct,incorrect);
                    Object obj;
                    if(_mode==0) {
                        obj = _queue.take();
                    }
                    else{
                        obj=_myQueue.take();
                    }
                    ValidatorFactory.validate(obj);
                    correct++;
                }
            } catch (InterruptedException e){
                System.out.print("Interrupted exception in take()");
                log.log(Level.SEVERE,"Interrupted exception in take()",e);
            } catch(ValidatorException e){
                incorrect++;
            }
        }
        System.out.format("Correct:%d \n Incorrect:%d", correct, incorrect);
        log.info("Correct: "+correct+"\n Incorrect: "+incorrect);
        System.out.print("[Worker] finished\n");
        log.info("[Worker] finished\n");
    }
}
