package kwetter.beans;

import com.google.gson.Gson;
import kwetter.domain.User;
import kwetter.helpers.Pair;
import kwetter.service.interfaces.IKwetterService;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by geh on 8-4-14.
 */
@MessageDriven(name="kwetterGoBean", activationConfig =
{
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "kwetterGoQueue")
})
public class KwetterGoBean implements MessageListener
{
    @Resource
    private MessageDrivenContext context;
    @Inject
    private IKwetterService service;

    @Override
    public void onMessage(Message message)
    {
        try
        {
            message.acknowledge();
            Pair<String, String> pair = new Gson().fromJson(((TextMessage)message).getText(), Pair.class);
            User user = service.getUser(pair.getFirst());
            service.addKwet(user, pair.getSecond(), "KwetterGo!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
