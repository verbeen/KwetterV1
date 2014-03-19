package kwetter.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity(name="Kwets")  @Table(name="kwets")
public class Kwet implements Comparable<Kwet>, Comparator<Kwet>
{
    @Transient
    private static final long serialVersionUID = 1L;

    @Column(name = "id") @Id @GeneratedValue
    private int id;
    @Column(name = "body")
    private String body;
    @JoinColumn(name = "poster_name") @ManyToOne(optional = false)
    private User poster;
    @Column(name = "post_date") @Temporal(TemporalType.TIMESTAMP)
    private Calendar postDate;
    @Column(name = "posted_from")
    private String postedFrom;

    @ManyToMany(mappedBy = "mentions")
    private Set<User> mentioned = new HashSet<User>();
    @ManyToMany(mappedBy = "kwets")
    private List<Trend> trends = new ArrayList<Trend>();

    public Kwet()
    {

    }

    public Kwet(String body)
    {
        this.body = body;
    }

    public Kwet(User poster, String body, Calendar datum, String vanaf)
    {
        this.poster = poster;
        this.body = body;
        this.postDate = datum;
        this.postedFrom = vanaf;
    }

    public User getPoster()
    {
        return this.poster;
    }

    public void setPoster(User poster)
    {
        this.poster = poster;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Calendar getDatum()
    {
        return postDate;
    }
    public String getDatumString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return sdf.format(this.postDate.getTime());
    }

    public void setDatum(Calendar datum)
    {
        this.postDate = datum;
    }

    public String getVanaf()
    {
        return postedFrom;
    }

    public void setVanaf(String vanaf)
    {
        this.postedFrom = vanaf;
    }


    @Override
    public int hashCode()
    {
        return this.id;
    }

    @Override
    public int compare(Kwet o1, Kwet o2)
    {
        if(o1.id == o2.id)
        {
            return 0;
        }
        else
        {
            return o2.getDatum().compareTo(o1.getDatum());
        }
    }

    @Override
    public int compareTo(Kwet o)
    {
        if(o.id == this.id)
        {
            return 0;
        }
        else
        {
            return o.getDatum().compareTo(this.getDatum());
        }
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Kwet)
        {
            Kwet other = (Kwet) object;

            if(this.id == other.id)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return Kwet.class.toString() + "[name=" + this.id + "]";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<Trend> getTrends()
    {
        return trends;
    }

    public void setTrends(List<Trend> trends)
    {
        this.trends = trends;
    }

    public void addTrend(Trend trend)
    {
        this.trends.add(trend);
    }

    public void setMentioned(Set<User> userMentions)
    {
        this.mentioned = userMentions;
    }

    public List<User> getMentioned()
    {
        return new ArrayList(this.mentioned);
    }

    public boolean addMention(User user)
    {
        return this.mentioned.add(user);
    }
}
