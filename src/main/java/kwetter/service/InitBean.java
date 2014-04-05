package kwetter.service;

import kwetter.domain.Role;
import kwetter.domain.User;
import kwetter.service.interfaces.IKwetterService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by geh on 19-3-14.
 */
@Singleton
@Startup
public class InitBean
{
    @Inject
    private IKwetterService service;

    @PostConstruct
    private void init()
    {
        Role adminRole = service.addRole("admin");
        Role moderatorRole = service.addRole("moderator");

        User admin = service.addUser("admin", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "a@b.com", "ekjlrgbsrbhtlks", "hrthrdty");
        User moderator = service.addUser("moderator", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "a@b.com", "bio", "web" );
        User jan = service.addUser("jan", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918","a@b.com", "gkejrbgkjlesr", "wefwe");
        User piet = service.addUser("piet", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "a@b.com", "gkejrbgkjlesr", "wefwe");
        User bert = service.addUser("bert", "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "a@b.com", "gkejrbgkjlesr", "wefwe");

        service.addRole(admin, adminRole);
        service.addRole(moderator, moderatorRole);

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.HOUR, -24);
        service.addKwet(jan, "net gegeten, was lekker #vet #cool #schranzen @piet @bert", (Calendar)cal.clone(), "mobile");

        service.addFollow(jan, piet);
        service.addFollow(jan, bert);

        cal.add(Calendar.SECOND, 5000);
        service.addKwet(piet, "koken vanavond, preitaartje maken! vet #schranzen", (Calendar)cal.clone(), "home");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "alles verbrand. niet cool. maar naar de friettendt #schranzen", (Calendar)cal.clone(), "mobile");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "frietje sate hoi hoi #YEAH", (Calendar)cal.clone(), "mobile");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "ZX-Hama 230 450cc gekocht, vet. #YEAH #YOLO", (Calendar)cal.clone(), "home");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "limiter dr af gehaald, cool #YOLO", (Calendar)cal.clone(), "mobile");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "iemand 20 euro te leen? #please? @jan @bert", (Calendar)cal.clone(), "mobile");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(piet, "motor gefixt. cruisen denk #YOLO @jan @bert", (Calendar)cal.clone(), "tablet");

        cal.add(Calendar.SECOND, 100);

        service.addFollow(piet, bert);
        service.addFollow(piet, jan);

        cal.add(Calendar.SECOND, 5000);
        service.addKwet(bert, "geslaagd, na endlich #YEAH #eindelijk @jan @piet", (Calendar)cal.clone(), "home");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(bert, "fiets gestolen, ugh #matig @piet", (Calendar)cal.clone(), "home");

        cal.add(Calendar.SECOND, 100);
        service.addKwet(bert, "@piet vrouw heeft me weer geslagen, niet cool. #matig", (Calendar)cal.clone(), "home");

        service.addFollow(bert, jan);
        service.addFollow(bert, piet);
    }
}
