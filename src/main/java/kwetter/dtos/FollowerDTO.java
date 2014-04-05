package kwetter.dtos;

import kwetter.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by geh on 5-4-14.
 */
@XmlRootElement @XmlAccessorType(XmlAccessType.NONE)
public class FollowerDTO implements Serializable
{
    @XmlElement
    private String name;
    @XmlElement
    private String bio;
    @XmlElement
    private String web;
    @XmlElement
    private String following;
    @XmlElement
    private String uri;

    public FollowerDTO()
    {

    }

    public FollowerDTO(User user)
    {
        this.name = user.getName();
        this.bio = user.getBio();
        this.web = user.getWeb();
        this.following = "false";
        this.uri = "http://localhost:8080/kwetter/api/service/user/" + this.name;
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

    public String getFollowing()
    {
        return following;
    }

    public void setFollowing(String following)
    {
        this.following = following;
    }
}
