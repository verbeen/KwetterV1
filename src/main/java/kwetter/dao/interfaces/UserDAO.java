package kwetter.dao.interfaces;

import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.ProcessKwet;

import javax.enterprise.event.Observes;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
public interface UserDAO
{
    void addUser(User user);

    User getUser(String name);

    void follow(FollowEvent event);

    void unFollow(FollowEvent event);

    void addMentions(KwetEvent event);

    void addMention(User user, Kwet kwet);

    void addFollowing(User follower, User following);
}
