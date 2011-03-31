package test_projects.mqtest;

import test_projects.mqtest.MQTest;
import javax.jms.JMSException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        test_projects.mqtest.MQTest mq = new test_projects.mqtest.MQTest();
        System.out.println("Creating the queue.");
        mq.setupQueue();
        System.out.println("Queuing a message.");
        mq.createMessage();
        System.out.println("Waiting 3 seconds...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        System.out.println("Consuming the message.");
        mq.grabMessage();
        System.out.println("Closing connection.");
        mq.closeQueue();
    }
}
