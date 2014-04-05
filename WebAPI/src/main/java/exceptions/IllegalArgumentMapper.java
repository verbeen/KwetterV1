package exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by geh on 5-4-14.
 */
@Provider
public class IllegalArgumentMapper implements ExceptionMapper<IllegalArgumentException>
{
    @Override
    public Response toResponse(IllegalArgumentException e)
    {
        return Response.status(400).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
