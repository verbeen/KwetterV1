package kwetter.batches;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import kwetter.batches.dtos.KwetDTO;
import kwetter.domain.User;
import kwetter.service.interfaces.IKwetterService;

/**
 * Created by geh on 24-3-14.
 */
@Dependent
@Named("kwetWriter")
public class KwetWriter implements ItemWriter
{
    @Inject
    private IKwetterService service;

    @Override
    public void open(Serializable serializable) throws Exception
    {

    }

    @Override
    public void close() throws Exception
    {

    }

    @Override
    public void writeItems(List<Object> objects) throws Exception
    {
        for(Object obj  : objects)
        {
            KwetDTO kwetDTO = (KwetDTO)obj;
            User user = this.service.getUser(kwetDTO.screenName);
            if(user == null)
            {
                user = this.service.addUser(kwetDTO.screenName, "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "a@b.com", "random", "default bio");
            }
            this.service.addKwet(user, kwetDTO.tweet, kwetDTO.postDate, kwetDTO.postedFrom);
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception
    {
        return new ItemNumberCheckpoint();
    }
}
