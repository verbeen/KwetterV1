package kwetter.events;

import kwetter.domain.User;

/**
 * Created by geh on 11-3-14.
 */
public class UserEvent
{
    public User user;

    public UserEvent(User user)
    {
        this.user = user;
    }
}
