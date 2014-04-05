package kwetter.dao;

import kwetter.dao.interfaces.TrendDAO;
import kwetter.domain.Kwet;
import kwetter.domain.Trend;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.ProcessKwet;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@Stateless
@Alternative @Specializes
public class TrendDAOImplJPA extends TrendDAOImplColl
{
    //@Resource
    //private UserTransaction ut;
    @PersistenceContext(unitName = "kwetterdb")
    private EntityManager em;

    public TrendDAOImplJPA()
    {

    }

    @Override
    public List<Trend> getAllTrends()
    {
        List<Trend> trends = em.createQuery("select trend from Trends trend").getResultList();
        Collections.sort(trends, new Comparator<Trend>()
        {
            @Override
            public int compare(Trend o1, Trend o2)
            {
                Integer x = o1.getKwets().size();
                Integer y = o2.getKwets().size();
                return y.compareTo(x);
            }
        });
        return trends;
    }

    @Override
    public void addTrends(@Observes @ProcessKwet KwetEvent event)
    {
        Kwet kwet = event.kwet;
        String[] split = kwet.getBody().split(" ");
        for(String s : split)
        {
            if(s.length() > 1 && s.charAt(0) == "#".charAt(0))
            {
                this.addTrend(s, kwet);
            }
        }

        em.merge(kwet);
    }

    @Override
    public void addTrend(String name, Kwet kwet)
    {
        Query q = em.createQuery("select trend from Trends trend where trend.name = :name ");
        q.setParameter("name", name);
        List<Trend> trends = q.getResultList();
        Trend trend = null;

        if(trends.isEmpty())
        {
            trend = new Trend(name);
            em.persist(trend);

            trend.addKwet(kwet);
            kwet.addTrend(trend);

            em.merge(trend);
            em.merge(kwet);
        }
        else
        {
            trend = trends.get(0);
            if(!trend.getKwets().contains(kwet))
            {
                trend.addKwet(kwet);
                kwet.addTrend(trend);

                em.merge(trend);
                em.merge(kwet);
            }
        }
    }

    @Override
    public Trend getTrend(String name)
    {
        Query q = em.createQuery("select trend from Trends trend where trend.name = :name");
        q.setParameter("name", name);
        List<Trend> trends = q.getResultList();
        Trend result = null;

        if(trends.isEmpty())
        {
            return null;
        }
        else
        {
            return trends.get(0);
        }
    }
}
