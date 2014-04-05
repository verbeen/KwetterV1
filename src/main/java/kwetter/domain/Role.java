package kwetter.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geh on 1-4-14.
 */
@Entity(name="Roles") @Table(name="roles")
public class Role
{
    @Id
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList();

    public Role()
    {

    }

    public Role(String name)
    {
        this.name = name;
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }

    public String getName()
    {
        return name;
    }

    public List<User> getUsers()
    {
        return users;
    }
}
