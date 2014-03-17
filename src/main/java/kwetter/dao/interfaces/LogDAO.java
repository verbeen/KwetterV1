package kwetter.dao.interfaces;

import kwetter.events.UserEvent;

/**
 * Created by geh on 11-3-14.
 */
public interface LogDAO
{
    void loginEvent(UserEvent event);
    void logoutEvent(UserEvent event);
}
