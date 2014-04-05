package kwetter.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by geh on 26-2-14.
 */
@Entity(name="Trends")  @Table(name="trends")
public class Trend
{
    @Column(name = "name") @Id
    private String name;
    @ManyToMany
    private Set<Kwet> kwets = new HashSet<Kwet>();

    public Trend(String name)
    {
        this.name = name;
    }

    public Trend()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Kwet> getKwets()
    {
        return kwets;
    }

    public Boolean addKwet(Kwet kwet)
    {
        return this.kwets.add(kwet);
    }

    public void removeKwet(Kwet kwet)
    {
        this.kwets.remove(kwet);
    }
}
