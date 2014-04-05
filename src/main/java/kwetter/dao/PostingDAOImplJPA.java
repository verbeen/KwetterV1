package kwetter.dao;

import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;
import kwetter.interceptors.annotations.VolgTrend;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@Stateless
@Alternative @Specializes
public class PostingDAOImplJPA extends PostingDAOImplColl
{
    @PersistenceContext(unitName = "kwetterdb")
    private EntityManager em;

    public PostingDAOImplJPA()
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
    public Kwet getKwet(int id)
    {
        return this.em.find(Kwet.class, id);
    }

    @Override @VolgTrend
    public void addKwet(@Observes @AddKwet KwetEvent evt)
    {
        Kwet kwet = evt.kwet;
        User user = evt.kwet.getPoster();
        em.persist(kwet);
        evt.kwet = kwet;
        user.addKwet(kwet);
    }

    @Override
    public List<Kwet> getAllKwets()
    {
        Query query = em.createQuery("select kwet from Kwets kwet");
        return query.getResultList();
    }

    @Override
    public void removeKwet(Kwet kwet)
    {
        this.em.remove(kwet);
        this.em.getEntityManagerFactory().getCache().evictAll();
    }
}
