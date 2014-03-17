package kwetter.beans;

import kwetter.dao.interfaces.TrendDAO;
import kwetter.domain.Trend;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
@RequestScoped
@Named("trendsBean")
public class TrendsBean implements Serializable
{
    @Inject
    private TrendDAO trendDAO;

    public TrendsBean()
    {

    }

    public List<Trend> getAllTrends()
    {
        return trendDAO.getAllTrends();
    }
}
