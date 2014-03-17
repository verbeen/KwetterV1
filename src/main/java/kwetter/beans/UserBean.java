package kwetter.beans;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.User;
import kwetter.events.FollowEvent;
import kwetter.events.UserEvent;
import kwetter.events.annotations.Follow;
import kwetter.events.annotations.Login;
import kwetter.events.annotations.Logout;
import kwetter.events.annotations.Unfollow;
import kwetter.helpers.ViewType;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by geh on 19-2-14.
 */

@SessionScoped
@Named("userBean")
public class UserBean implements Serializable
{
    @Inject
    private UserDAO userDAO;
    @Inject @Login
    Event<UserEvent> loginEvent;
    @Inject @Logout
    Event<UserEvent> logoutEvent;
    @Inject @Follow
    Event<FollowEvent> followEvent;
    @Inject @Unfollow
    Event<FollowEvent> unFollowEvent;

    private User currentUser;
    private User viewingUser;
    private ViewType viewWindow = ViewType.KWETS;
    private String currentPage = "index";
    private Boolean loggedIn = false;

    public UserBean()
    {

    }

    public String logIn()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String name = map.get("name");
        String pass = map.get("pass");

        User user = null;
        if(name != null && pass != null)
        {
            user = userDAO.getUser(name);
            if(user == null)
            {
                user = userDAO.addUser(new User(name, pass, "wnegjnerjgksjkdgk"));
            }

            this.loggedIn = true;
        }

        if(user != null)
        {
            this.setCurrentUser(user);
            this.setViewingUser(user);
            this.loginEvent.fire(new UserEvent(user));
        }

        return "?faces-redirect=true";
    }

    public String logOut() throws IOException
    {
        this.logoutEvent.fire(new UserEvent(currentUser));
        this.loggedIn = false;
        return "?faces-redirect=true";
    }

    public ViewType getViewWindow()
    {
        return viewWindow;
    }

    public void setViewWindow(ViewType viewWindow)
    {
        this.viewWindow = viewWindow;
    }

    public void setViewFollowing()
    {
        this.viewWindow = ViewType.FOLLOWING;
    }

    public void setViewKwets()
    {
        this.viewWindow = ViewType.KWETS;
    }

    public void setViewFollowers()
    {
        this.viewWindow = ViewType.FOLLOWERS;
    }

    public Boolean compareWindow(String type)
    {
        if(this.viewWindow.toString().equals(type))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setViewingUser()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String name = map.get("user");
        User user = userDAO.getUser(name);
        if(user != null)
        {
            this.setViewingUser(user);
        }
    }

    public String getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String page = map.get("page");

        if(page != null && page != "")
        {
            this.currentPage = page;
        }
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public User getViewingUser()
    {
        return viewingUser;
    }

    private void setViewingUser(User viewingUser)
    {
        this.viewingUser = viewingUser;
    }

    public Boolean isFollowing()
    {
        return this.currentUser.getFollowing().contains(this.viewingUser);
    }

    public void addFollowing()
    {
        this.followEvent.fire(new FollowEvent(this.currentUser, this.viewingUser));
    }

    public void removeFollowing()
    {
        this.unFollowEvent.fire(new FollowEvent(this.currentUser, this.viewingUser));
    }

    public Boolean getLoggedIn()
    {
        return loggedIn;
    }

}
