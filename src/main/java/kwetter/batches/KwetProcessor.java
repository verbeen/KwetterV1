package kwetter.batches;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by geh on 24-3-14.
 */
@Dependent
@Named("kwetProcessor")
public class KwetProcessor implements ItemProcessor
{
    @Inject
    private JobContext context;

    @Override
    public Object processItem(Object o) throws Exception
    {
        return o;
    }
}
