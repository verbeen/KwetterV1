package kwetter.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

@Entity(name="Kwets")
public class Kwet implements Comparable<Kwet>, Comparator<Kwet>
{
    @Transient
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private String id;

    @ManyToOne
    private User poster;
    private String kwet;
    private Calendar postDate;
    private String postedFrom;

    public Kwet()
    {
    }

    public Kwet(String kwet)
    {
        this.kwet = kwet;
    }

    public Kwet(User poster, String kwet, Calendar datum, String vanaf)
    {
        this.poster = poster;
        this.kwet = kwet;
        this.postDate = datum;
        this.postedFrom = vanaf;
    }

    public User getPoster() { return this.poster; }
    public void setPoster(User poster) { this.poster = poster; }

    public String getKwet()
    {
        return kwet;
    }

    public void setKwet(String kwet)
    {
        this.kwet = kwet;
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
        int hash = 0;
        hash += (kwet != null ? kwet.hashCode() + postDate.hashCode() : 0);
        return hash;
    }

    @Override
    public int compare(Kwet o1, Kwet o2)
    {
        return o2.getDatum().compareTo(o1.getDatum());
    }

    @Override
    public int compareTo(Kwet o)
    {
        return o.getDatum().compareTo(this.getDatum());
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Kwet)
        {
            Kwet other = (Kwet) object;

            if(this.hashCode() == other.hashCode())
            {
                if(this.kwet == other.kwet && this.postDate.equals(other.postDate) && this.poster.getName() == other.poster.getName())
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "twitter.domain.Tweet[id=" + postDate.toString() + "]";
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
