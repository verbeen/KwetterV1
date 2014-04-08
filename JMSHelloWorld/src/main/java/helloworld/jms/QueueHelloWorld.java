package helloworld.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by geh on 8-4-14.
 */
public class QueueHelloWorld
{
    private static final Logger LOG = Logger.getLogger(TopicHelloWorld.class.getName());
    /**
     * the preconfigured GlassFish-default connection factory
     */
    private static final String JNDI_CONNECTION_FACTORY = "kwetterConnectionFactory";
    /**
     * the JNDI name under which your {@link javax.jms.Topic} should be: you have to
     * create the topic before running this class
     */
    private static final String JNDI_TOPIC = "kwetterGoQueue";

    /**
     * @param <T> the return type
     * @param retvalClass the returned value's {@link Class}
     * @param jndi the JNDI path to the resource
     * @return the resource at the specified {@code jndi} path
     */
    private static <T> T lookup(Class<T> retvalClass, String jndi)
    {
        try
        {
            //Properties props = new Properties();
            //props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.appserv.naming.S1ASCtxFactory");
            //props.put(Context.PROVIDER_URL, "iiop://localhost:3700");
            Context context = new InitialContext();
            Object o = context.lookup(jndi);
            return retvalClass.cast(o);
        }
        catch (NamingException ex)
        {
            throw new IllegalArgumentException("failed to lookup instance of " + retvalClass + " at " + jndi, ex);
        }
    }

    /**
     * Send a {@link javax.jms.TextMessage} to the
     * {@link javax.jms.Queue}@{@value #JNDI_TOPIC} using the
     * {@link javax.jms.ConnectionFactory}@{@value #JNDI_CONNECTION_FACTORY}. The message
     * is received, after which the method exits.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ConnectionFactory connectionFactory = lookup(ConnectionFactory.class, JNDI_CONNECTION_FACTORY);
        final Queue queue = lookup(Queue.class, JNDI_TOPIC);

        //JMSContext implements AutoClosable: let us try 'try-with-resources'
        //see http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (JMSContext jmsContext = connectionFactory.createContext())
        {
            final JMSConsumer topicConsumer = jmsContext.createConsumer(queue);
            final MessageListener messageListener = new MessageListener()
            {
                @Override
                public void onMessage(Message message) {
                    LOG.log(Level.INFO, "received {0}", message);
                    countDownLatch.countDown();
                }
            };
            topicConsumer.setMessageListener(messageListener);
            final JMSProducer producer = jmsContext.createProducer();
            final String text = "hello world!";
            producer.send(queue, text);
            LOG.log(Level.INFO, "sent {0} to {1}", new Object[]{text, JNDI_TOPIC});
            try
            {
                countDownLatch.await();
                LOG.log(Level.INFO, "phew... it worked", text);
            }
            catch (InterruptedException ex)
            {
                LOG.log(Level.SEVERE, "fatal error: ", ex);
            }
        }//jmsContext is autoclosed
    }
}
