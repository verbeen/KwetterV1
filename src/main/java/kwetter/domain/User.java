package kwetter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity(name="Users") @Table(name="users")
public class User
{
    @Transient
    private static final long serialVersionUID = 1L;

    @Column(unique = true, name="name") @Id
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="web")
    private String web;
    @Column(name="bio")
    private String bio;
    @JsonIgnore
    @Column(name="password")
    private String password;

    @ManyToMany @JoinTable(name="following", joinColumns = { @JoinColumn(name="following") }, inverseJoinColumns = { @JoinColumn(name="followers") })
    private List<User> followers = new ArrayList();
    @ManyToMany(mappedBy="followers")
    private List<User> following = new ArrayList();
    @OneToMany(mappedBy = "poster")
    private List<Kwet> kwets = new ArrayList();
    @ManyToMany @JoinTable(name="mentions", joinColumns = { @JoinColumn(name = "mentions") }, inverseJoinColumns = { @JoinColumn(name="mentioned") })
    private List<Kwet> mentions = new ArrayList();
    @ManyToMany @JoinTable(name="groups", joinColumns = { @JoinColumn(name = "user")}, inverseJoinColumns = { @JoinColumn(name="role") })
    private List<Role> roles = new ArrayList();

    public User()
    {

    }

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String email, String web, String bio)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.web = web;
        this.bio = bio;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public List<User> getFollowing()
    {
        return this.following;
    }

    public void setFollowing(ArrayList<User> following)
    {
        this.following = following;
    }

    public Boolean addFollowing(User following)
    {
        return this.following.add(following);
    }
    public Boolean removeFollowing(User following)
    {
        return this.following.remove(following);
    }
    public Boolean isFollowing(User user)
    {
        return this.following.contains(user);
    }

    public List<Kwet> getKwets()
    {
        return new ArrayList(this.kwets);
    }

    public void setKwets(ArrayList<Kwet> kwets)
    {
        this.kwets = kwets;
    }

    public Boolean addKwet(Kwet kwet)
    {
        Boolean added = this.kwets.add(kwet);
        Collections.sort(this.kwets);
        return added;
    }

    public List<User> getFollowers()
    {
        return this.followers;
    }

    public void setFollowers(ArrayList<User> followers)
    {
        this.followers = followers;
    }

    public Boolean addFollower(User follower)
    {
        return this.followers.add(follower);
    }

    public Boolean removeFollower(User follower)
    {
        return this.followers.remove(follower);
    }

    public Kwet getLatestKwet()
    {
        if(this.kwets.size() > 0)
        {
            List kwts = new ArrayList(this.kwets);
            Collections.sort(this.kwets);
            return this.kwets.get(0);
        }
        else
        {
            return null;
        }
    }

    public List<Kwet> getMentions()
    {
        List<Kwet> result = new ArrayList(this.mentions);
        Collections.sort(result);
        return result;
    }

    public boolean addMention(Kwet kwet)
    {
        boolean added = this.mentions.add(kwet);
        Collections.sort(this.mentions);
        return added;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (name != null ? name.hashCode() + bio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the name fields are not set
        if (!(object instanceof User))
        {
            return false;
        }
        User other = (User) object;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString()
    {
        return "twitter.domain.User[naam=" + name + "]";
    }

    public void setMentions(List<Kwet> kwetMentions)
    {
        this.mentions = kwetMentions;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void addRole(Role role)
    {
        this.roles.add(role);
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void removeKwet(Kwet kwet)
    {
        this.kwets.remove(kwet);
    }
}
