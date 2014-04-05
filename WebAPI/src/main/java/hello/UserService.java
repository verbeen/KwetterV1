package hello;

import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by geh on 5-4-14.
 */
@Path("users") @Stateless
public class UserService
{
    @PersistenceContext(unitName = "webapidb")
    private EntityManager em;
    private Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");

    @GET
    public String doGet()
    {
        return "Hello World";
    }

    @GET @Path("show/{text}")
    public String showText(@PathParam("text") String text)
    {
        if(!pattern.matcher(text).find()) throw new IllegalArgumentException();
        return "Hello " + text;
    }

    @GET @Path("find/{username}") @Produces("application/json")
    public User getUser(@PathParam("username") String name)
    {
        if(!pattern.matcher(name).find()) throw new IllegalArgumentException();
        User user = em.find(User.class, name);
        if(user == null) throw new EntityNotFoundException();
        return user;
    }

    @GET @Path("register/{username},{firstname},{lastname}") @Produces("application/json")
    public User registerUser(@PathParam("username") String username, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        if(!pattern.matcher(username).find()) throw new IllegalArgumentException();
        if(!pattern.matcher(firstname).find()) throw new IllegalArgumentException();
        if(!pattern.matcher(lastname).find()) throw new IllegalArgumentException();
        User user = new User().userName(username).firstName(firstname).lastName(lastname);
        em.persist(user);
        return user;
    }

    @GET @Path("change/first/{username}/{name}") @Produces("application/json")
    public User changeFirstName(@PathParam("username") String username, @PathParam("name") String firstname)
    {
        if(!pattern.matcher(username).find()) throw new IllegalArgumentException();
        if(!pattern.matcher(firstname).find()) throw new IllegalArgumentException();
        User user = this.getUser(username);
        if(user == null) throw new EntityNotFoundException();
        user.firstName(firstname);
        return user;
    }

    @GET @Path("change/last/{username}/{name}") @Produces("application/json")
    public User changeLastName(@PathParam("username") String username, @PathParam("name") String lastname)
    {
        if(!pattern.matcher(username).find()) throw new IllegalArgumentException();
        User user = this.getUser(username);
        if(user == null) throw new EntityNotFoundException();
        user.lastName(lastname);
        return user;
    }

    @GET @Path ("all") @Produces("application/json")
    public List<User> getUsers()
    {
        List<User> users = em.createQuery("select user from Users user").getResultList();
        return users;
    }
}
