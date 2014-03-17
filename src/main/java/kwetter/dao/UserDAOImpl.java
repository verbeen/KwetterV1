package kwetter.dao;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.Kwet;
import kwetter.domain.Mention;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.UserEvent;
import kwetter.events.annotations.Follow;
import kwetter.events.annotations.ProcessKwet;
import kwetter.events.annotations.Unfollow;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@ApplicationScoped
public class UserDAOImpl implements UserDAO, Serializable
{
    @PersistenceContext(unitName = "kwetterDB")
    private EntityManager em;

    public UserDAOImpl()
    {

    }

    @Override
    public User addUser(User user)
    {
        return em.merge(user);
    }

    @Override
    public void follow(@Observes @Follow FollowEvent event)
    {
        User user = event.user;
        User following = event.following;

        if(user != following)
        {
            if(!user.isFollowing(following))
            {
                user.addFollowing(following);
            }
        }
    }

    @Override
    public void unFollow(@Observes @Unfollow FollowEvent event)
    {
        User user = event.user;
        User following = event.following;

        if(user != following)
        {
            if(user.isFollowing(following))
            {
                user.removeFollowing(following);
            }
        }
    }

    @Override
    public User getUser(int id)
    {
        Query query = em.createQuery("select user from Users user where user.id = :id");
        query.setParameter("id", id);

        List<User> users = query.getResultList();
        if(!users.isEmpty())
        {
            return users.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public User getUser(String name)
    {
        Query query = em.createQuery("select user from Users user where user.name = :name");
        query.setParameter("name", name);

        List<User> users = query.getResultList();
        User result = null;

        if(!users.isEmpty())
        {
            result = users.get(0);
        }

        return result;
    }

    @Override
    public List<Mention> getAllMentions()
    {
        List<Mention> results = em.createQuery("select mention from Mentions mention").getResultList();

        Collections.sort(results, new Comparator<Mention>()
        {
            @Override
            public int compare(Mention o1, Mention o2)
            {
                Integer x = o1.getKwets().size();
                Integer y = o2.getKwets().size();
                return y.compareTo(x);
            }
        });
        return results;
    }

    @Override
    public void addMentions(@Observes @ProcessKwet KwetEvent event)
    {
        Kwet kwet = event.kwet;

        String[] split = kwet.getKwet().split(" ");
        for(String s : split)
        {
            if(s.length() > 1 && s.charAt(0) == "@".charAt(0))
            {
                User user = this.getUser(s.substring(1));
                if(user != null)
                {
                    if(!user.getMentions().contains(kwet))
                    {
                        this.addMention(user, kwet);
                    }
                }
            }
        }
    }

    @Override
    public void addMention(User user, Kwet kwet)
    {
        List<Kwet> mentions = user.getMentions();
        if(!mentions.contains(kwet))
        {
            mentions.add(kwet);
        }
    }
}
