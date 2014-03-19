package kwetter.beans;

import kwetter.domain.Trend;
import kwetter.service.KwetterServiceDAO;
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
    private KwetterServiceDAO service;

    public TrendsBean()
    {

    }

    public List<Trend> getAllTrends()
    {
        return service.getAllTrends();
    }
}
