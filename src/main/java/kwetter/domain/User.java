package kwetter.domain;

import javax.persistence.*;
import java.util.*;

@Entity(name="Users")
public class User
{
    @Transient
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;
    @Column(unique = true)
    private String name;
    private String web;
    private String bio;

    @OneToMany
    private List<User> followers;
    @OneToMany
    private List<User> following;
    @OneToMany
    private List<Kwet> kwets;
    @OneToMany
    private List<Kwet> mentions;

    public User()
    {

    }

    public User(String name)
    {
        this.name = name;
    }

    public User(String name, String web, String bio)
    {
        this.name = name;
        this.web = web;
        this.bio = bio;
    }

    public int getId()
    {
        return id;
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

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public List<User> getFollowing() { return this.following; }
    public void setFollowing(ArrayList<User> following) { this.following = following; }
    public Boolean addFollowing(User following)
    {
        if(this.following.add(following))
        {
            return following.addFollower(this);
        }
        else
        {
            return false;
        }
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
        return this.kwets;
    }

    public void setKwets(ArrayList<Kwet> tweets)
    {
        this.kwets = tweets;
    }

    public Boolean addKwet(Kwet kwet)
    {
        Boolean result = this.kwets.add(kwet);
        Collections.sort(this.kwets);
        return result;
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

    public ArrayList<Kwet> getTimeline()
    {
        ArrayList<Kwet> kwets = new ArrayList<Kwet>();

        kwets.addAll(this.kwets);

        for(User user : this.following)
        {
            kwets.addAll(user.getKwets());
        }

        Collections.sort(kwets);

        return kwets;
    }

    public Kwet getLatestKwet()
    {
        if(this.kwets.size() > 0)
        {
            return this.kwets.get(0);
        }
        else
        {
            return null;
        }
    }

    public List<Kwet> getMentions()
    {
        return mentions;
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
}
