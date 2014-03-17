package kwetter.beans;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.Mention;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@RequestScoped
@Named("mentionsBean")
public class MentionsBean implements Serializable
{
    @Inject
    private UserDAO userDAO;

    public MentionsBean()
    {

    }

    public List<Mention> getAllMentions()
    {
        return userDAO.getAllMentions();
    }
}
