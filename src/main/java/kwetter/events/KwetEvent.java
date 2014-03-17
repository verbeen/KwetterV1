package kwetter.events;

import kwetter.domain.Kwet;

/**
 * Created by geh on 10-3-14.
 */
public class KwetEvent
{
    public Kwet kwet;

    public KwetEvent(Kwet kwet)
    {
        this.kwet = kwet;
    }
}
