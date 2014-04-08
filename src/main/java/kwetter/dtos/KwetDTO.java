package kwetter.dtos;

import kwetter.domain.Kwet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by geh on 6-4-14.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class KwetDTO
{
    @XmlElement
    private String id;
    @XmlElement
    private String body;
    @XmlElement
    private String poster;
    @XmlElement
    private String postDate;
    @XmlElement
    private String postedFrom;

    public KwetDTO()
    {

    }

    public KwetDTO(Kwet kwet)
    {
        this.id = "" + kwet.getId();
        this.body = kwet.getBody();
        this.poster = kwet.getPoster().getName();
        this.postDate = kwet.getDatumString();
        this.postedFrom = kwet.getVanaf();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    public String getPostDate()
    {
        return postDate;
    }

    public void setPostDate(String postDate)
    {
        this.postDate = postDate;
    }

    public String getPostedFrom()
    {
        return postedFrom;
    }

    public void setPostedFrom(String postedFrom)
    {
        this.postedFrom = postedFrom;
    }
}
