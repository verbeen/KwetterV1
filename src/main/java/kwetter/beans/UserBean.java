package kwetter.beans;

import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.helpers.ViewType;
import kwetter.service.interfaces.IKwetterService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by geh on 19-2-14.
 */
@SessionScoped
@Named("userBean")
public class UserBean implements Serializable
{
    @Inject
    private IKwetterService service;

    private User currentUser;
    private User viewingUser;
    private ViewType viewWindow = ViewType.KWETS;
    private String currentPage = "index";

    private String name;
    private String password;

    public UserBean()
    {

    }

    public String logIn()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String name = map.get("name");
        String pass = map.get("pass");

        User user = this.service.logIn(name, pass);

        if(user != null)
        {
            this.setCurrentUser(user);
            this.setViewingUser(user);
        }

        return "master.xhtml";
    }

    public User getAuthenticatedUser()
    {
        User foundUser = null;

        FacesContext fc = FacesContext.getCurrentInstance();
        Principal principal = fc.getExternalContext().getUserPrincipal();

        if (principal != null)
        {
            foundUser = service.getUser(principal.getName());

            if(this.currentUser == null)
            {
                this.setCurrentUser(foundUser);
                this.setViewingUser(foundUser);
            }
        }

        return foundUser;
    }

    public String logOut() throws IOException
    {
        this.service.logOut(this.currentUser);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.setCurrentUser(null);
        this.setViewingUser(null);

        return "login.xhtml";
    }

    public List<Kwet> getTimeline()
    {
        return service.getTimeline(this.currentUser);
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

        User user = service.getUser(name);
        if(user != null)
        {
            this.setViewingUser(user);
        }
    }

    public String getCurrentPage()
    {
        return currentPage;
    }

    public String setCurrentPage()
    {
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String page = map.get("page");

        if(page != null && page != "")
        {
            this.currentPage = page;
        }

        return "master.xhtml";
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
        this.service.addFollow(this.currentUser, this.viewingUser);
    }

    public void removeFollowing()
    {
        service.unFollow(this.currentUser, this.viewingUser);
    }

    public Boolean getLoggedIn()
    {
        this.currentUser = this.getAuthenticatedUser();
        if(this.currentUser == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
