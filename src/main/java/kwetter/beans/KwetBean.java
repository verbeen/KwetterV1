package kwetter.beans;

import com.google.gson.Gson;
import kwetter.dao.interfaces.PostingDAO;
import kwetter.dao.interfaces.TrendDAO;
import kwetter.domain.Kwet;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;
import kwetter.helpers.Triplet;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Created by geh on 26-2-14.
 */
@RequestScoped
@Named("kwetBean")
public class KwetBean implements Serializable
{
    @Inject
    private UserBean userBean;
    @Inject
    private PostingDAO postingDAO;
    @Inject
    private TrendDAO trendDAO;
    @Inject @AddKwet
    private Event<KwetEvent> addKwetEvent;

    public KwetBean()
    {

    }

    public void searchKwets()
    {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ctx = fctx.getExternalContext();
        Map<String, String> map = ctx.getRequestParameterMap();
        String search = map.get("search");

        try
        {
            List<Kwet> kwets = postingDAO.searchKwets(search);
            ArrayList<Triplet<String, String, String>> jsonKwets = new ArrayList<Triplet<String, String, String>>();
            for(Kwet kwet : kwets)
            {
                jsonKwets.add(new Triplet(kwet.getPoster().getName(), kwet.getKwet(), kwet.getDatumString()));
            }

            String json = new Gson().toJson(jsonKwets);
            ctx.setResponseContentType("application/json");
            ctx.setResponseCharacterEncoding("UTF-8");
            ctx.getResponseOutputWriter().write(json);
            fctx.responseComplete();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void addKwet()
    {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ctx = fctx.getExternalContext();
        Map<String, String> map = ctx.getRequestParameterMap();
        String txt = map.get("txt");

        if(txt != null && txt != "")
        {
            Kwet kwet = new Kwet(userBean.getCurrentUser(), txt, new GregorianCalendar(), "");
            KwetEvent evtPayload = new KwetEvent(kwet);
            this.addKwetEvent.fire(evtPayload);
        }
    }
}
