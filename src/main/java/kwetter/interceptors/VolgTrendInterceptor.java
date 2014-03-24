package kwetter.interceptors;

import kwetter.events.KwetEvent;
import kwetter.interceptors.annotations.VolgTrend;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.regex.Pattern;

/**
 * Created by geh on 19-3-14.
 */
@Interceptor @VolgTrend
@ApplicationScoped
public class VolgTrendInterceptor
{
    @AroundInvoke
    public Object addKwet(InvocationContext ctx) throws Exception
    {
        Object[] parameters = ctx.getParameters();
        KwetEvent param = (KwetEvent)parameters[0];

        param.kwet.setBody(this.replaceWord(param.kwet.getBody(), "vet", "dik"));
        param.kwet.setBody(this.replaceWord(param.kwet.getBody(), "cool", "hard"));

        parameters[0] = param;

        try
        {
            return ctx.proceed();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    private String replaceWord(String source, String word, String replacement)
    {
        return source.replaceAll("\\b" + Pattern.quote(word) + "\\b", replacement);
    }
}
