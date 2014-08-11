package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.MyBlockingQueue;
import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.serialize.Serializer;
import com.noveogroup.java.validator.ValidateException;
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
    private final SimpleBlockQueue<Object> queue;
    private AtomicBoolean flag;
    private Logger log = Logger.getLogger(Worker.class.getName());
    private int correct;
    private int incorrect;
    private final ValidatorFactory validatorFactory = new ValidatorFactory();

    public Worker(final SimpleBlockQueue<Object> queue ,
                  final AtomicBoolean flag
    ) {
        this.queue = queue;
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println("[Worker] run\n");
        while ((this.queue.size() != 0)
                || (this.flag.get())) {
            try {
                if (this.queue.size() != 0) {
                    System.out.format("%d %d \n" , correct , incorrect);
                    Object obj;
                    obj = this.queue.take();
                    validatorFactory.validate(obj);
                    correct++;
                }
            } catch (InterruptedException e){
                final String message = "Interrupted exception in take()";
                System.out.print(message);
                log.log(Level.SEVERE , message , e);
            } catch (ValidateException e) {
                incorrect++;
//                log.log(Level.SEVERE , e.getMessage());
            }
        }
//        System.out.format("Correct:%d \n Incorrect:%d" , correct , incorrect);
        log.info("Correct: " + correct + "\n Incorrect: " + incorrect);
        System.out.print("[Worker] finished\n");
    }
}
