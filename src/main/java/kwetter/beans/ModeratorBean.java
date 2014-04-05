package kwetter.beans;

import kwetter.domain.Kwet;
import kwetter.service.interfaces.IKwetterService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by geh on 2-4-14.
 */
@SessionScoped
@Named("moderatorBean")
public class ModeratorBean implements Serializable
{
    @Inject
    private IKwetterService service;

    public List<Kwet> getAllKwets()
    {
        return service.getAllKwets();
    }

    public void removeKwet(int id)
    {
        this.service.removeKwet(id);
    }
}
