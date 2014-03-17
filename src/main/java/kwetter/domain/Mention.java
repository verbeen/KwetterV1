package kwetter.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by geh on 26-2-14.
 */
@Entity(name="Mentions")
public class Mention
{
    @Id @ManyToOne
    private User user;
    @OneToMany
    private Set<Kwet> kwets = new HashSet<Kwet>();

    public Mention(User user)
    {
        this.user = user;
    }

    public Mention()
    {

    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Set<Kwet> getKwets() { return kwets; }
    public Boolean addKwet(Kwet kwet)
    {
        return this.kwets.add(kwet);
    }
}
