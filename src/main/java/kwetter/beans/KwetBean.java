package kwetter.beans;

import com.google.gson.Gson;
import kwetter.domain.Kwet;
import kwetter.helpers.Triplet;
import kwetter.service.interfaces.IKwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private IKwetterService service;
    @Inject
    private UserBean userBean;

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
            List<Kwet> kwets = service.searchKwets(search);

            ArrayList<Triplet<String, String, String>> jsonKwets = new ArrayList<Triplet<String, String, String>>();
            for(Kwet kwet : kwets)
            {
                jsonKwets.add(new Triplet(kwet.getPoster().getName(), kwet.getBody(), kwet.getDatumString()));
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
            this.service.addKwet(this.userBean.getCurrentUser(), txt, "pc");
        }
    }
}
