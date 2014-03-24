package kwetter.service.interfaces;

import kwetter.domain.Kwet;
import kwetter.domain.Trend;
import kwetter.domain.User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by geh on 18-3-14.
 */
public interface IKwetterService extends Serializable
{
    List<Kwet> searchKwets(String filter);

    void addKwet(User user, String content, String from);

    void addKwet(User user, String content, Calendar cal, String from);

    List<Trend> getAllTrends();

    User logIn(String name, String pass);

    void logOut(User user);

    List<Kwet> getTimeline(User user);

    User getUser(String name);

    User addUser(String name, String pass, String bio);

    void addFollow(User follower, User following);

    void unFollow(User follower, User following);
}
