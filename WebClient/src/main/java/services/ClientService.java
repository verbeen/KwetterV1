package services;

import domain.User;
import webservice.KwetDTO;
import webservice.SoapService;
import webservice.SoapServiceService;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by geh on 5-4-14.
 */
@Path("client") @Stateless
public class ClientService
{
    @GET @Path("find/{username}") @Produces("application/json")
    public User getUser(@PathParam("username") String name)
    {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/webapi/api/users/find/" + name);
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response response = invocation.invoke();
        User user = response.readEntity(User.class);
        response.close();

        return user;
    }

    @GET @Path("register/{username},{firstname},{lastname}") @Produces("application/json")
    public User registerUser(@PathParam("username") String username, @PathParam("firstname") String firstname, @PathParam("lastname") String lastname)
    {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/webapi/api/users/register/" + username + "," + firstname + "," + lastname);
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response response = invocation.invoke();
        User user = response.readEntity(User.class);
        response.close();

        return user;
    }


    @GET @Path("change/first/{username}/{name}") @Produces("application/json")
    public User changeFirstName(@PathParam("username") String username, @PathParam("name") String firstname)
    {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/webapi/api/users/change/first/" + username + "/" + firstname);
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response response = invocation.invoke();
        User user = response.readEntity(User.class);
        response.close();

        return user;
    }

    @GET @Path("change/last/{username}/{name}") @Produces("application/json")
    public User changeLastName(@PathParam("username") String username, @PathParam("name") String lastname)
    {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/webapi/api/users/change/last/" + username + "/" + lastname);
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response response = invocation.invoke();
        User user = response.readEntity(User.class);
        response.close();

        return user;
    }

    @GET @Path("post/{name},{content},{from}") @Produces("application/json")
    public void postKwet(@PathParam("name") String name,@PathParam("content") String content,@PathParam("from") String from)
    {
        SoapService service = new SoapServiceService().getSoapServicePort();
        service.postKwet(name, content, from);
    }

    @GET @Path("timeline/{name}") @Produces("application/json")
    public List<KwetDTO> getTimeline(@PathParam("name") String name)
    {
        SoapService service = new SoapServiceService().getSoapServicePort();
        return service.getTimeline(name);
    }

    @GET @Path ("all") @Produces("application/json")
    public List<User> getUsers()
    {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/webapi/api/users/all");
        Invocation invocation = target.request(MediaType.APPLICATION_JSON).buildGet();
        Response response = invocation.invoke();
        List<User> users = response.readEntity(List.class);
        response.close();

        return users;
    }
}
