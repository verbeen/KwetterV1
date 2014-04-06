package kwetter.dao;

import kwetter.domain.Application;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.Follow;
import kwetter.events.annotations.ProcessKwet;
import kwetter.events.annotations.Unfollow;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * Created by geh on 26-2-14.
 */
@Stateless
@Alternative @Specializes
public class UserDAOImplJPA extends UserDAOImplColl
{
    @PersistenceContext(unitName = "kwetterdb")
    private EntityManager em;
    @Resource(lookup="kwettermail")
    private Session session;

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
        User user = em.find(User.class, name);
        return user;
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

    @Override
    public void removeApplication(String name)
    {
        Application app = this.getApplication(name);
        em.remove(app);
    }

    @Override
    public void removeOldApplications()
    {
        GregorianCalendar time = new GregorianCalendar();
        time.add(GregorianCalendar.HOUR, -1);

        Query query = em.createQuery("select app from Applications app where app.time <= :time", Application.class);
        query.setParameter("time", time, TemporalType.TIMESTAMP);

        List<Application> apps = query.getResultList();
        for(Application app : apps)
        {
            em.remove(app);
        }
    }

    @Override
    public Application getApplication(String name)
    {
        return em.find(Application.class, name);
    }

    @Override
    public void addApplication(Application application)
    {
        application.setActivationKey("robuustekey" + new Random().nextInt(50));
        em.persist(application);
        try
        {
            Message msg = new MimeMessage(session);
            msg.setSubject("Kwetter activation");
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress((application.getEmail()), application.getName()));
            msg.setFrom(new InternetAddress(session.getProperty("mail.from")));

            String activationlink = "http://localhost:8080/kwetter/activate.xhtml?name=" + application.getName() + "&key=" + application.getActivationKey();
            msg.setText("Hey, " + application.getName() + ". Please press the following activation link to activate your account " + activationlink);

            Transport.send(msg);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
