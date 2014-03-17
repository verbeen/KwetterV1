package kwetter.service;

import kwetter.dao.interfaces.*;
import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by geh on 19-2-14.
 */
@Singleton
@Startup
public class KwetterService implements Serializable
{
    @Inject
    private PostingDAO postingDAO;
    @Inject
    private TrendDAO trendDAO;
    @Inject
    private UserDAO userDAO;
    @Inject
    private LogDAO logDAO;

    public KwetterService()
    {

    }

    @PostConstruct
    private void init()
    {
        User jan = userDAO.addUser(new User("jan", "gkejrbgkjlesr", "wefwe"));
        User piet = userDAO.addUser(new User("piet", "gkejrbgkjlesr", "wefwe"));
        User bert = userDAO.addUser(new User("bert", "gkejrbgkjlesr", "wefwe"));

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR, -24);
        Kwet k1 = new Kwet(jan, "net gegeten, was lekker #schranzen @piet @bert", (Calendar)cal.clone(), "mobile");
        postingDAO.addKwet(new KwetEvent(k1));
        jan.addFollowing(piet);
        jan.addFollowing(bert);

        cal.add(Calendar.SECOND, 5000);
        Kwet k2 = new Kwet(piet, "koken vanavond, preitaartje maken! #schranzen", (Calendar)cal.clone(), "home");
        postingDAO.addKwet(new KwetEvent(k2));

        cal.add(Calendar.SECOND, 100);
        Kwet k3  = new Kwet(piet, "alles verbrand. maar naar de friettendt #schranzen", (Calendar)cal.clone(), "mobile");
        postingDAO.addKwet(new KwetEvent(k3));

        cal.add(Calendar.SECOND, 100);
        Kwet k4  = new Kwet(piet, "frietje sate hoi hoi #YEAH", (Calendar)cal.clone(), "mobile");
        postingDAO.addKwet(new KwetEvent(k4));

        cal.add(Calendar.SECOND, 100);
        Kwet k5  = new Kwet(piet, "ZX-Hama 230 450cc gekocht, awesome. #YEAH #YOLO", (Calendar)cal.clone(), "home");
        postingDAO.addKwet(new KwetEvent(k5));

        cal.add(Calendar.SECOND, 100);
        Kwet k6  = new Kwet(piet, "limiter dr af gehaald #YOLO", (Calendar)cal.clone(), "mobile");
        postingDAO.addKwet(new KwetEvent(k6));

        cal.add(Calendar.SECOND, 100);
        Kwet k7  = new Kwet(piet, "iemand 20 euro te leen? #please? @jan @bert", (Calendar)cal.clone(), "mobile");
        postingDAO.addKwet(new KwetEvent(k7));

        cal.add(Calendar.SECOND, 100);
        Kwet k8  = new Kwet(piet, "motor gefixt. cruisen denk #YOLO @jan @bert", (Calendar)cal.clone(), "tablet");
        postingDAO.addKwet(new KwetEvent(k8));

        cal.add(Calendar.SECOND, 100);
        piet.addFollowing(bert);
        piet.addFollowing(jan);

        cal.add(Calendar.SECOND, 5000);
        Kwet k9  = new Kwet(bert, "geslaagd, na endlich #YEAH #eindelijk @jan @piet", (Calendar)cal.clone(), "home");
        postingDAO.addKwet(new KwetEvent(k9));

        cal.add(Calendar.SECOND, 100);
        Kwet k10  = new Kwet(bert, "fiets gestolen, ugh #klote @piet", (Calendar)cal.clone(), "home");
        postingDAO.addKwet(new KwetEvent(k10));

        cal.add(Calendar.SECOND, 100);
        Kwet k11  = new Kwet(bert, "@piet vrouw heeft me weer geslagen #klote", (Calendar)cal.clone(), "home");
        postingDAO.addKwet(new KwetEvent(k11));

        bert.addFollowing(jan);
        bert.addFollowing(piet);
    }
}
