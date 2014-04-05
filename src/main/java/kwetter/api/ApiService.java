package kwetter.api;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.User;
import kwetter.dtos.FollowerDTO;
import kwetter.dtos.UserDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geh on 5-4-14.
 */
@Path("service") @Stateless
public class ApiService
{
    @Inject
    private UserDAO userDAO;

    @GET
    @Path("user/{name}")
    public UserDTO getUser(@PathParam("name") String name)
    {
        return new UserDTO(this.userDAO.getUser(name));
    }

    @GET
    @Path("user/followers/{name}")
    public List<FollowerDTO> getFollowers(@PathParam("name") String name)
    {
        User user = this.userDAO.getUser(name);
        List<User> followers = user.getFollowers();
        List<User> following = user.getFollowing();
        List<FollowerDTO> results = new ArrayList<>();
        for(User follower : followers)
        {
            FollowerDTO fdto = new FollowerDTO(follower);
            if(following.contains(follower))
            {
                fdto.setFollowing("true");
            }
            results.add(fdto);
        }

        return results;
    }
}
