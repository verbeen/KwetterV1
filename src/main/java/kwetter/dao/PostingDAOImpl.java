package kwetter.dao;

import kwetter.dao.interfaces.*;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;
import kwetter.events.annotations.ProcessKwet;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Created by geh on 26-2-14.
 */
@ApplicationScoped
public class PostingDAOImpl implements PostingDAO, Serializable
{
    @Inject @ProcessKwet
    private Event<KwetEvent> processEvent;
    @PersistenceContext(unitName = "kwetterDB")
    private EntityManager em;

    public PostingDAOImpl()
    {

    }

    @Override
    public List<Kwet> searchKwets(String filter)
    {
        Query q = em.createQuery("select kwet from Kwets kwet where kwet.kwet LIKE :filter");
        q.setParameter("filter", "%" + filter + "%");

        return q.getResultList();
    }

    @Override
    public Boolean addKwet(User user)
    {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ctx = fctx.getExternalContext();
        Map<String, String> map = ctx.getRequestParameterMap();
        String txt = map.get("txt");

        if(txt != null && txt != "")
        {
            Kwet kwet = new Kwet(user, txt, new GregorianCalendar(), "");
            this.addKwet(new KwetEvent(kwet));
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void addKwet(@Observes @AddKwet KwetEvent evt)
    {
        evt.kwet = em.merge(evt.kwet);
        if(evt.kwet != null && evt.kwet.getPoster().addKwet(evt.kwet))
        {
            this.processEvent.fire(evt);
        }
    }
}
