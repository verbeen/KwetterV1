package kwetter.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by geh on 2-4-14.
 */
@Entity(name="Applications") @Table(name="applications")
public class Application
{
    @Column(unique = true, name="name") @Id
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="web")
    private String web;
    @Column(name="bio")
    private String bio;
    @Column(name="activationkey")
    private String activationKey;
    @Column(name="time") @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;

    public Application()
    {

    }

    public Application(String name, String password, String email, String web, String bio)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.web = web;
        this.bio = bio;
        this.time = new GregorianCalendar();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getWeb()
    {
        return web;
    }

    public String getBio()
    {
        return bio;
    }

    public String getActivationKey()
    {
        return activationKey;
    }

    public void setActivationKey(String key)
    {
        this.activationKey = key;
    }

    public Calendar getTime()
    {
        return time;
    }
}
