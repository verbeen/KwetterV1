import helloworld.jms.QueueHelloWorld;
import helloworld.jms.TopicHelloWorld;

/**
 * Created by geh on 8-4-14.
 */
public class Start
{
    public static void main(String[] args)
    {
        TopicHelloWorld.main(null);
        QueueHelloWorld.main(null);
    }
}
