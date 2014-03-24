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
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by geh on 26-2-14.
 */
@Stateless
@Alternative @Specializes
public class UserDAOImplJPA extends UserDAOImplColl
{
    @PersistenceContext(unitName = "kwetterDB")
    private EntityManager em;

    public UserDAOImplJPA()
    {

    }

    @Override
    public void addUser(User user)
    {
        em.persist(user);
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
        return em.find(User.class, name);
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
        em.merge(user);
        em.merge(kwet);
    }

    @Override
    public void addFollowing(User follower, User following)
    {
        follower.addFollowing(following);
        following.addFollower(follower);
        em.merge(follower);
        em.merge(following);
    }

    public void removeFollowing(User follower, User following)
    {
        follower.removeFollowing(following);
        following.removeFollower(follower);
        em.merge(follower);
        em.merge(following);
    }
}