package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by geh on 5-4-14.
 */
@Entity(name="Users") @XmlRootElement
public class User
{
    @Id
    public String userName;
    public String firstName;
    public String lastName;

    public User()
    {

    }

    public User userName(String name)
    {
        this.userName = name;
        return this;
    }

    public String userName()
    {
        return this.userName();
    }

    public User firstName(String name)
    {
        this.firstName = name;
        return this;
    }

    public String firstName()
    {
        return this.firstName;
    }

    public User lastName(String name)
    {
        this.lastName = name;
        return this;
    }

    public String lastName()
    {
        return this.lastName;
    }
}
