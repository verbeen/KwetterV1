package kwetter.dao;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.Follow;
import kwetter.events.annotations.ProcessKwet;
import kwetter.events.annotations.Unfollow;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by geh on 26-2-14.
 */
@Default
@Stateless
public class UserDAOImplColl implements UserDAO
{
    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    public UserDAOImplColl()
    {

    }

    @Override
    public void addUser(User user)
    {
        this.users.put(user.getName(), user);
    }

    @Override
    public void follow(@Observes @Follow FollowEvent event)
    {
        User follower = event.user;
        User following = event.following;

        if(follower != following)
        {
            if(!follower.isFollowing(following))
            {
                this.addFollowing(follower, following);
            }
        }
    }

    @Override
    public void unFollow(@Observes @Unfollow FollowEvent event)
    {
        User follower = event.user;
        User following = event.following;

        if(follower != following)
        {
            if(follower.isFollowing(following))
            {
               this.removeFollowing(follower, following);
            }
        }
    }

    @Override
    public User getUser(String name)
    {
        return this.users.get(name);
    }

    @Override
    public void addMentions(@Observes @ProcessKwet KwetEvent event)
    {
        Kwet kwet = event.kwet;

        String[] split = kwet.getBody().split(" ");
        for(String s : split)
        {
            if(s.length() > 1 && s.charAt(0) == "@".charAt(0))
            {
                User user = this.getUser(s.substring(1));
                if(user != null)
                {
                    this.addMention(user, kwet);
                }
            }
        }
    }

    @Override
    public void addMention(User user, Kwet kwet)
    {
        user.addMention(kwet);
        kwet.addMention(user);
    }

    @Override
    public void addFollowing(User follower, User following)
    {
        follower.addFollowing(following);
        following.addFollower(follower);
    }

    public void removeFollowing(User follower, User following)
    {
        follower.removeFollowing(following);
        following.removeFollower(follower);
    }
}
