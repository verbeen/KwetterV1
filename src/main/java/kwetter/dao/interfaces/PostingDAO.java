package kwetter.dao.interfaces;

import kwetter.domain.Kwet;
import kwetter.domain.User;
import kwetter.events.KwetEvent;
import kwetter.helpers.Triplet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geh on 26-2-14.
 */
public interface PostingDAO
{
    List<Kwet> searchKwets(String search);

    Boolean addKwet(User user);

    void addKwet(KwetEvent event);
}
