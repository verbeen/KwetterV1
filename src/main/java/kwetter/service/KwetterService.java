package kwetter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kwetter.dao.interfaces.*;
import kwetter.domain.*;
import kwetter.dtos.KwetDTO;
import kwetter.events.FollowEvent;
import kwetter.events.KwetEvent;
import kwetter.events.UserEvent;
import kwetter.events.annotations.*;
import kwetter.service.interfaces.IKwetterService;

import javax.ejb.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.websocket.Session;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by geh on 19-2-14.
 */
@Stateless @Path("service")
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
    @Inject
    private SecurityDAO securityDAO;
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
    private ConcurrentHashMap<String, LinkedList<Session>> socketSessions = new ConcurrentHashMap();

    public KwetterService()
    {

    }

    @Override
    public List<Kwet> searchKwets(String filter)
    {
        return postingDAO.searchKwets(filter);
    }

    @Override
    public Kwet addKwet(User user, String content, Calendar cal, String from)
    {
        Kwet kwet = new Kwet(user, content, cal, from);
        KwetEvent evtPayload = new KwetEvent(kwet);
        this.addKwetEvent.fire(evtPayload);
        this.processKwetEvent.fire(evtPayload);

        this.pushKwet(user, kwet);

        return kwet;
    }

    @Override
    public List<Kwet> getAllKwets()
    {
        return this.postingDAO.getAllKwets();
    }

    @Override
    public void removeKwet(int id)
    {
        Kwet kwet = this.postingDAO.getKwet(id);
        if(kwet != null)
        {
            this.postingDAO.removeKwet(kwet);
        }
    }

    @Override
    public Kwet addKwet(User user, String content, String from)
    {
        Kwet kwet = new Kwet(user, content, new GregorianCalendar(), from);
        KwetEvent evtPayload = new KwetEvent(kwet);
        this.addKwetEvent.fire(evtPayload);
        this.processKwetEvent.fire(evtPayload);

        this.pushKwet(user, kwet);

        return kwet;
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
                user = new User(name, pass, "a@b.com", "jdhsfbgjsdf", "wnegjnerjgksjkdgk");
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
    public User addUser(String name, String pass, String email, String web, String bio)
    {
        User user = new User(name, pass, email, web, bio);
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
    public User getUser(@PathParam("name") String name)
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

    @Override
    public Role addRole(String name)
    {
        Role result = new Role(name);
        this.securityDAO.addRole(result);
        return result;
    }

    @Override
    public void addRole(User user, Role role)
    {
        this.securityDAO.addUserRole(user, role);
    }

    @Override
    public boolean addApplication(String name, String password, String email, String bio, String web)
    {
        if(name == null || "".equals(name) || password == null || "".equals(password) ) return false;
        if(this.getUser(name) == null && this.getApplication(name) == null)
        {
            Application app = new Application(name, password, email, bio, web);
            this.userDAO.addApplication(app);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Application getApplication(String name)
    {
        return this.userDAO.getApplication(name);
    }

    @Override
    public boolean activate(String name, String key)
    {
        Application app = this.userDAO.getApplication(name);

        if(app == null) return false;
        if(!app.getActivationKey().equals(key)) return false;

        User user = new User(app.getName(), app.getPassword(), app.getEmail(), app.getBio(), app.getWeb());
        this.userDAO.removeApplication(name);
        this.userDAO.addUser(user);

        return true;
    }

    @Override
    public void addSession(User user, Session session)
    {
        LinkedList<Session> sessions;
        if(this.socketSessions.containsKey(user.getName()))
        {
            sessions = this.socketSessions.get(user.getName());
        }
        else
        {
            sessions = new LinkedList<>();
            this.socketSessions.put(user.getName(), sessions);
        }

        sessions.add(session);
    }

    @Override
    public void removeSession(User user, Session session)
    {
        if(this.socketSessions.containsKey(user.getName()))
        {
            LinkedList<Session> sessions = this.socketSessions.get(user.getName());
            sessions.remove(session);
            if(sessions.size() == 0)
            {
                this.socketSessions.remove(user.getName());
            }
        }

    }

    @Override
    public List<Session> getSessions(User user)
    {
        return this.socketSessions.get(user.getName());
    }

    @Override
    public void pushKwet(User user, Kwet kwet)
    {
        this.pushKwet(this.socketSessions.get(user.getName()), kwet);
        for(User follower : user.getFollowers())
        {

            this.pushKwet(this.socketSessions.get(follower.getName()), kwet);
        }
    }

    private void pushKwet(LinkedList<Session> sessions, Kwet kwet)
    {
        try
        {
            if(sessions != null)
            {
                for(Session session : sessions)
                {
                    ObjectMapper mapper = new ObjectMapper();
                    String str = mapper.writeValueAsString(new KwetDTO(kwet));
                    session.getAsyncRemote().sendText(str);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
;