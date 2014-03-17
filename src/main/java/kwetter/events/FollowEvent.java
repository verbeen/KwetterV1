package kwetter.events;

import kwetter.domain.User;

/**
 * Created by geh on 11-3-14.
 */
public class FollowEvent
{
    public User user;
    public User following;

    public FollowEvent(User user, User following)
    {
        this.user = user;
        this.following = following;
    }
}
