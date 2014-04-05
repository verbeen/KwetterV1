package kwetter.batches;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import kwetter.batches.dtos.KwetDTO;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by geh on 24-3-14.
 */
@Dependent
@Named("kwetReader")
public class KwetReader implements ItemReader
{
    @Inject
    private JobContext jobCtx;

    private String fileName;
    private JsonReader reader;
    private ItemNumberCheckpoint checkpoint;
    private Gson gson = new Gson();

    @Override
    public void open(Serializable serializable) throws Exception
    {
        if (serializable == null)
        {
            this.checkpoint = new ItemNumberCheckpoint();
        }
        else
        {
            this.checkpoint = (ItemNumberCheckpoint) serializable;
        }

        this.fileName = this.jobCtx.getProperties().getProperty("kwetfile");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(this.fileName);
        InputStreamReader isr = new InputStreamReader(is);
        this.reader = new JsonReader(isr);

        reader.beginObject();
        String name = reader.nextName();
        if(!"Tweets".equals(name))
        {
            throw new IOException();
        }

        reader.beginArray();
        for (int i=0; i<checkpoint.getItemNumber(); i++)
        {
            reader.beginObject();
            reader.endObject();
        }
    }

    @Override
    public void close() throws Exception
    {
        this.reader.close();
    }

    @Override
    public Object readItem() throws Exception
    {
        if(reader.hasNext())
        {
            this.reader.beginObject();

            KwetDTO kwet = new KwetDTO();
            this.reader.nextName();
            kwet.screenName = this.reader.nextString();
            this.reader.nextName();
            kwet.tweet = this.reader.nextString();
            this.reader.nextName();
            kwet.postedFrom = this.reader.nextString();
            this.reader.nextName();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsed = df.parse(this.reader.nextString());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(parsed);
            kwet.postDate = cal;

            this.reader.endObject();

            return kwet;
        }
        else
        {
            return null;
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception
    {
        return this.checkpoint;
    }
}
