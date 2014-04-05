package kwetter.dao;

import kwetter.dao.interfaces.PostingDAO;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by geh on 26-2-14.
 */
@Default
@Stateless
public class PostingDAOImplColl implements PostingDAO
{
    private ConcurrentHashMap<Integer, Kwet> kwets = new ConcurrentHashMap<Integer, Kwet>();
    private AtomicInteger ids = new AtomicInteger();

    public PostingDAOImplColl()
    {

    }

    @Override
    public List<Kwet> searchKwets(String filter)
    {
        List<Kwet> results = new ArrayList<Kwet>();
        for(Kwet kwet : this.kwets.values())
        {
            if(kwet.getBody().contains(filter))
            {
                results.add(kwet);
            }
        }

        return results;
    }

    @Override
    public Kwet getKwet(int id)
    {
        return null;
    }

    @Override
    public void addKwet(@Observes @AddKwet KwetEvent evt)
    {
        Kwet kwet = evt.kwet;
        User user = evt.kwet.getPoster();
        this.kwets.put(this.ids.incrementAndGet(), kwet);
        user.addKwet(kwet);
    }

    @Override
    public List<Kwet> getAllKwets()
    {
        return null;
    }

    @Override
    public void removeKwet(Kwet kwet)
    {

    }
}
