package kwetter.service.interfaces;

import kwetter.domain.*;

import javax.websocket.Session;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by geh on 18-3-14.
 */
public interface IKwetterService extends Serializable
{
    List<Kwet> searchKwets(String filter);

    Kwet addKwet(User user, String content, String from);

    Kwet addKwet(User user, String content, Calendar cal, String from);

    List<Kwet> getAllKwets();

    void removeKwet(int id);

    List<Trend> getAllTrends();

    User logIn(String name, String pass);

    void logOut(User user);

    List<Kwet> getTimeline(User user);

    User getUser(String name);

    User addUser(String name, String pass, String email, String web, String bio);

    void addFollow(User follower, User following);

    void unFollow(User follower, User following);

    Role addRole(String name);

    void addRole(User user, Role role);

    boolean addApplication(String name, String password, String email, String bio, String web);

    Application getApplication(String name);

    boolean activate(String name, String key);

    void addSession(User user, Session session);

    void removeSession(User user, Session session);

    List<Session> getSessions(User user);

    void pushKwet(User user, Kwet kwet);
}
