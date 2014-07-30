package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.validator.ValidatorFactory;
import sun.security.validator.ValidatorException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author artem ryzhikov
 */
class Worker implements Runnable {
    private final BlockingQueue<Object> queue;
    private AtomicBoolean flag;
    private Logger log = Logger.getLogger(Worker.class.getName());
    private int correct;
    private int incorrect;
    private final MyBlockingQueue myQueue;
    private int mode;

    public Worker(final BlockingQueue<Object> queue,
                  final MyBlockingQueue myQueue,
                  final AtomicBoolean flag,
                  final int mode
                  ) {
        this.queue = queue;
        this.myQueue = myQueue;
        this.flag = flag;
        this.mode = mode;
    }

    @Override
    public void run() {
        System.out.println("[Worker] run\n");
        //while (_queue.size != 0 or Reader still works)
        while ((this.queue.size() != 0)
                || (this.flag.get())
                || (this.myQueue.size() != 0)) {
            try {
                if (this.queue.size() != 0 || this.myQueue.size() != 0) {
                //Здесь еще можно засыпать если очередь пуста
                    System.out.format("%d %d \n" , correct , incorrect);
                    Object obj;
                    if (this.mode == 0) {
                        obj = this.queue.take();
                    }
                    else {
                        obj = this.myQueue.take();
                    }
                    ValidatorFactory.validate(obj);
                    correct++;
                }
            } catch (InterruptedException e){
                final String message = "Interrupted exception in take()";
                System.out.print(message);
                log.log(Level.SEVERE , message , e);
            } catch (ValidatorException e) {
                incorrect++;
            }
        }
        System.out.format("Correct:%d \n Incorrect:%d" , correct , incorrect);
        log.info("Correct: " + correct + "\n Incorrect: " + incorrect);
        final String message = "[Worker] finished\n";
        System.out.print(message);
        log.info(message);
    }
}
