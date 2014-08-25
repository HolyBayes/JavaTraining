package com.noveogroup.java;

import com.noveogroup.java.my_concurrency.SimpleBlockQueue;
import com.noveogroup.java.validator.ValidateException;
import com.noveogroup.java.validator.ValidatorFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author artem ryzhikov
 */
class Worker implements Runnable {
    private final SimpleBlockQueue<Object> queue;
    private AtomicBoolean flag;
    private AtomicInteger valid;
    private AtomicInteger invalid;
    private Logger log = Logger.getLogger(Worker.class.getName());
    private final ValidatorFactory validatorFactory = new ValidatorFactory();

    public Worker(final SimpleBlockQueue<Object> queue,
                  final AtomicBoolean flag,
                  AtomicInteger valid,
                  AtomicInteger invalid
    ) {
        this.queue = queue;
        this.flag = flag;
        this.valid = valid;
        this.invalid = invalid;
    }

    @Override
    public void run() {
        System.out.println("[Worker] run\n");
        while ((this.queue.size() != 0)
                || (this.flag.get())) {
            try {
                if (this.queue.size() != 0) {
                    Object obj;
                    obj = this.queue.take();
                    validatorFactory.validate(obj);
                    valid.set(valid.get() + 1);
                }
            } catch (ValidateException e) {
                invalid.set(invalid.get() + 1);
                log.log(Level.SEVERE , e.getMessage()
                        + "\n In Field : " + e.getFieldName()
                        + "\n With value :" + e.getFieldValue() , e);
            } catch (Exception e) {
                log.log(Level.SEVERE , e.getMessage());
            }
        }
        log.info(Thread.currentThread().getName() + " finished");
    }
}
