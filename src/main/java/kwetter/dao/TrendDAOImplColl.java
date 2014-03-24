package kwetter.dao;

import kwetter.dao.interfaces.TrendDAO;
import kwetter.domain.Kwet;
import kwetter.domain.Trend;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.ProcessKwet;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by geh on 26-2-14.
 */
@Default
@Stateless
public class TrendDAOImplColl implements TrendDAO
{
    private ConcurrentHashMap<String, Trend> trends = new ConcurrentHashMap<String, Trend>();

    public TrendDAOImplColl()
    {

    }

    @Override
    public List<Trend> getAllTrends()
    {
        List<Trend> results = new ArrayList(this.trends.values());
        Collections.sort(results, new Comparator<Trend>()
        {
            @Override
            public int compare(Trend o1, Trend o2)
            {
                Integer x = o1.getKwets().size();
                Integer y = o2.getKwets().size();
                return y.compareTo(x);
            }
        });
        return results;
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
    }

    @Override
    public void addTrend(String name, Kwet kwet)
    {
        if(!this.trends.containsKey(name))
        {
            Trend trend = new Trend(name);

            trend.addKwet(kwet);
            kwet.addTrend(trend);

            this.trends.put(trend.getName(), trend);
        }
        else
        {
            Trend trend = trends.get(name);
            if(!trend.getKwets().contains(kwet))
            {
                trend.addKwet(kwet);
                kwet.addTrend(trend);
            }
        }
    }

    @Override
    public Trend getTrend(String name)
    {
        return this.trends.get(name);
    }
}
