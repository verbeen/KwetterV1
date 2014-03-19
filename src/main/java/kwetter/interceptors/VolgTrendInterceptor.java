package kwetter.interceptors;

import kwetter.domain.Kwet;
import kwetter.events.KwetEvent;
import kwetter.events.annotations.AddKwet;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import java.util.regex.Pattern;

/**
 * Created by geh on 19-3-14.
 */
@ApplicationScoped
public class VolgTrendInterceptor
{
    public void interceptKwet(@Observes(during=TransactionPhase.BEFORE_COMPLETION) @AddKwet KwetEvent evt)
    {
        Kwet kwet = evt.kwet;
        kwet.setBody(this.replaceWord(kwet.getBody(), "vet", "dik"));
        kwet.setBody(this.replaceWord(kwet.getBody(), "cool", "hard"));
        evt.kwet = kwet;
    }

    private String replaceWord(String source, String word, String replacement)
    {
        return source.replaceAll("\\b" + Pattern.quote(word) + "\\b", replacement);
    }
}
