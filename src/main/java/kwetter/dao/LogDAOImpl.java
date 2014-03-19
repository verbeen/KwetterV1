package kwetter.dao;

import kwetter.dao.interfaces.LogDAO;
import kwetter.domain.User;
import kwetter.events.UserEvent;
import kwetter.events.annotations.Login;
import kwetter.events.annotations.Logout;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.util.GregorianCalendar;

/**
 * Created by geh on 11-3-14.
 */
@Stateless
public class LogDAOImpl implements LogDAO
{
    @Override
    public void loginEvent(@Observes @Login UserEvent event)
    {
        User user = event.user;
        System.out.println("User " + user.getName() + " has logged in at " + new GregorianCalendar().getTime().toString());
    }

    @Override
    public void logoutEvent(@Observes @Logout UserEvent event)
    {
        User user = event.user;
        System.out.println("User " + user.getName() + " has logged out at " + new GregorianCalendar().getTime().toString());
    }
}
