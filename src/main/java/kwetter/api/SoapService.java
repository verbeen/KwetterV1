package kwetter.api;

import kwetter.dao.interfaces.UserDAO;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.dtos.KwetDTO;
import kwetter.service.interfaces.IKwetterService;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by geh on 6-4-14.
 */
@WebService
public class SoapService
{
    @Inject
    private IKwetterService service;
    @Inject
    private UserDAO userDAO;

    @WebMethod
    public void postKwet(String name, String content, String from)
    {
        User user = userDAO.getUser(name);
        if(user != null)
        {
            service.addKwet(user, content, new GregorianCalendar(), from);
        }
    }

    @WebMethod
    public List<KwetDTO> getTimeline(String name)
    {
        List<KwetDTO> results = new ArrayList();
        User user = userDAO.getUser(name);
        if(user != null)
        {
            for(Kwet kwet : service.getTimeline(user))
            {
                results.add(new KwetDTO(kwet));
            }
        }

        return results;
    }
}
