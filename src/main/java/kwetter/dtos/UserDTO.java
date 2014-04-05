package kwetter.dtos;

import kwetter.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by geh on 6-4-14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class UserDTO
{
    @XmlElement
    private String name;
    @XmlElement
    private String bio;
    @XmlElement
    private String web;
    @XmlElement
    private String email;

    public UserDTO()
    {

    }

    public UserDTO(User user)
    {
        this.name = user.getName();
        this.bio = user.getBio();
        this.web = user.getWeb();
        this.email = user.getEmail();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
