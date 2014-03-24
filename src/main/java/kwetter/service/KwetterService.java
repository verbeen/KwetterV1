package kwetter.service;

import kwetter.dao.interfaces.*;
import kwetter.domain.Kwet;
import kwetter.domain.Trend;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.UserEvent;
import kwetter.events.annotations.*;
import kwetter.interceptors.annotations.VolgTrend;
import kwetter.service.interfaces.IKwetterService;

import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.*;

/**
 * Created by geh on 19-2-14.
 */
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
public class KwetterService implements IKwetterService
{
    @Inject
    private PostingDAO postingDAO;
    @Inject
    private TrendDAO trendDAO;
    @Inject
    private UserDAO userDAO;
    @Inject
    private LogDAO logDAO;
    @Inject @Login
    private Event<UserEvent> loginEvent;
    @Inject @Logout
    private Event<UserEvent> logoutEvent;
    @Inject @Follow
    private Event<FollowEvent> followEvent;
    @Inject @Unfollow
    private Event<FollowEvent> unFollowEvent;
    @Inject @AddKwet
    private Event<KwetEvent> addKwetEvent;
    @Inject @ProcessKwet
    private Event<KwetEvent> processKwetEvent;

    public KwetterService()
    {

    }

    @Override
    public List<Kwet> searchKwets(String filter)
    {
        return postingDAO.searchKwets(filter);
    }

    @Override
    public void addKwet(User user, String content, Calendar cal, String from)
    {
        Kwet kwet = new Kwet(user, content, cal, from);
        KwetEvent evtPayload = new KwetEvent(kwet);
        this.addKwetEvent.fire(evtPayload);
        this.processKwetEvent.fire(evtPayload);
    }

    @Override
    public void addKwet(User user, String content, String from)
    {
        Kwet kwet = new Kwet(user, content, new GregorianCalendar(), from);
        KwetEvent evtPayload = new KwetEvent(kwet);
        this.addKwetEvent.fire(evtPayload);
        this.processKwetEvent.fire(evtPayload);
    }

    @Override
    public List<Trend> getAllTrends()
    {
        return this.trendDAO.getAllTrends();
    }

    @Override
    public User logIn(String name, String pass)
    {
        if(name != null && pass != null)
        {
            User user = userDAO.getUser(name);
            if(user == null)
            {
                user = new User(name, pass, "wnegjnerjgksjkdgk");
                userDAO.addUser(user);
            }

            this.loginEvent.fire(new UserEvent(user));
            return user;
        }
        else
        {
            return null;
        }
    }

    @Override
    public User addUser(String name, String pass, String bio)
    {
        User user = new User(name, pass, bio);
        this.userDAO.addUser(user);
        return user;
    }

    @Override
    public void logOut(User user)
    {
        this.logoutEvent.fire(new UserEvent(user));
    }

    @Override
    public List<Kwet> getTimeline(User user)
    {
        List<Kwet> results = new ArrayList<Kwet>();

        results.addAll(user.getKwets());
        for(User following : user.getFollowing())
        {
            results.addAll(following.getKwets());
        }

        Collections.sort(results);

        return results;
    }

    @Override
    public User getUser(String name)
    {
        return this.userDAO.getUser(name);
    }

    @Override
    public void addFollow(User follower, User following)
    {
        this.followEvent.fire(new FollowEvent(follower, following));
    }

    @Override
    public void unFollow(User follower, User following)
    {
        this.unFollowEvent.fire(new FollowEvent(follower, following));
    }
}
