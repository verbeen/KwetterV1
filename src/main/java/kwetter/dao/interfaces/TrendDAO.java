package kwetter.dao.interfaces;

import kwetter.domain.Kwet;
import kwetter.domain.Trend;
import kwetter.events.KwetEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
public interface TrendDAO
{
    List<Trend> getAllTrends();

    void addTrends(KwetEvent event);

    void addTrend(String name, Kwet kwet);

    Trend getTrend(String name);
}
