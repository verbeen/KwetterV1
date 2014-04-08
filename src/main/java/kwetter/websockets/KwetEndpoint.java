package kwetter.websockets;

import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.service.interfaces.IKwetterService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by geh on 7-4-14.
 */
@Stateless
@ServerEndpoint("/socket/kwet")
public class KwetEndpoint
{
    @Inject
    private IKwetterService service;

    @OnOpen
    public void open(Session session, EndpointConfig conf)
    {
        User user = service.getUser(session.getUserPrincipal().getName());
        this.service.addSession(user, session);
    }

    @OnError
    public void error(Session session, Throwable error)
    {
        try
        {
            User user = service.getUser(session.getUserPrincipal().getName());
            this.service.removeSession(user, session);
            session.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(Session session, String msg)
    {
        User user = service.getUser(session.getUserPrincipal().getName());
        Kwet kwet = service.addKwet(user, msg, "websocket");
    }

    @OnClose
    public void close(Session session, CloseReason reason)
    {
        User user = service.getUser(session.getUserPrincipal().getName());
        this.service.removeSession(user, session);
    }
}
