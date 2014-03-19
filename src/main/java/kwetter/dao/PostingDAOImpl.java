package kwetter.dao;

import kwetter.dao.interfaces.*;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;
import kwetter.events.annotations.ProcessKwet;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@Stateless
public class PostingDAOImpl implements PostingDAO, Serializable
{
    @PersistenceContext(unitName = "kwetterDB")
    private EntityManager em;

    public PostingDAOImpl()
    {

    }

    @Override
    public List<Kwet> searchKwets(String filter)
    {
        Query q = em.createQuery("select kwet from Kwets kwet where kwet.body LIKE :filter");
        q.setParameter("filter", "%" + filter + "%");

        return q.getResultList();
    }

    @Override
    public void addKwet(@Observes @AddKwet KwetEvent evt)
    {
        Kwet kwet = evt.kwet;
        User user = evt.kwet.getPoster();
        em.persist(kwet);
        evt.kwet = kwet;
        user.addKwet(kwet);
    }
}
