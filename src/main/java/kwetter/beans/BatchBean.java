package kwetter.beans;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by geh on 25-3-14.
 */
@Named("batchBean")
@RequestScoped
public class BatchBean implements Serializable
{
    private long execID;
    private JobOperator jobOperator;

    public void start()
    {
        this.jobOperator = BatchRuntime.getJobOperator();
        this.execID = this.jobOperator.start("kwetjob", null);
    }

    public String getJobStatus()
    {
        return jobOperator.getJobExecution(execID).getBatchStatus().toString();
    }
}
