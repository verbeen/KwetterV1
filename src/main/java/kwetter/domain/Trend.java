package kwetter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by geh on 26-2-14.
 */
@Entity(name="Trends")
public class Trend
{
    @Id
    private String name;
    @OneToMany
    private Set<Kwet> kwets;

    public Trend(String name)
    {
        this.name = name;
    }

    public Trend()
    {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Kwet> getKwets() { return kwets; }
    public Boolean addKwet(Kwet kwet)
    {
        return this.kwets.add(kwet);
    }
}
