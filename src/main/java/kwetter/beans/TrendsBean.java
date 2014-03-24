package kwetter.beans;

import kwetter.domain.Trend;
import kwetter.service.interfaces.IKwetterService;

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
    private IKwetterService service;

    public TrendsBean()
    {

    }

    public List<Trend> getAllTrends()
    {
        return service.getAllTrends();
    }
}
