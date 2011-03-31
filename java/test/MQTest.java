package MQTest;

import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;

/**
 * An ActiveMQ test program that creates a queue, writes a message to it, waits
 * awhile, grabs the message, then closes the connections.  Requires
 * activemq-all-5.4.1.jar.
 *
 * @author Bruce C. Miller
 */
class MQTest {
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private boolean transacted = true;
    private String subject = "TOOL.DEFAULT";
    
    public void setupQueue() {
        try {
            // Create connection.
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                                                              ActiveMQConnection.DEFAULT_PASSWORD,
                                                              ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            // Create session.
            session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            // Create destination.
            destination = session.createQueue(subject);
        } catch (javax.jms.JMSException e) {
            System.out.println("An error occurred during setup: " + e.toString());
        }
    }

    public void createMessage() {
        try {
            // Create producer.
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.setTimeToLive(5000);
            // Create message.
            TextMessage message = session.createTextMessage("Here is some message text.");
            // Send message.
            producer.send(message);
            // Commit message.
            session.commit();
            // Close producer.
            producer.close();
        } catch (javax.jms.JMSException e) {
            System.out.println("An error occurred during message creation: " + e.toString());
        }
    }

    public void grabMessage() {
        try {
            // Create consumer.
            MessageConsumer consumer = session.createConsumer(destination);
            // Consume the message(s).
            Message message;
            while ((message = consumer.receive(1000)) != null) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("MESSAGE RECEIVED: " + textMessage);
                    // Message is auto-acknowledged, otherwise, we'd have to
                    // acknowledge it here.
                }
            }
            // Close consumer.
            consumer.close();
        }
        catch (javax.jms.JMSException e) {
            System.out.println("An error occurred during message consumption: " + e.toString());
        }
    }

    public void closeQueue() {
        try {
            // Close session and connection.
            session.close();
            connection.close();
        } catch (javax.jms.JMSException e) {
            System.out.println("An error occurred during shutdown: " + e.toString());
        }
    }
    
    public static void main(String[] args) {
        MQTest test = new MQTest();
        
        System.out.println("Creating the queue.");
        test.setupQueue();
        System.out.println("Queuing a message.");
        test.createMessage();
        System.out.println("Waiting 3 seconds...");
        try {
            Thread.sleep(3000);
        } catch (java.lang.InterruptedException e) {
        }
        System.out.println("Consuming the message.");
        test.grabMessage();
        System.out.println("Closing connection.");
        test.closeQueue();
    }
}
