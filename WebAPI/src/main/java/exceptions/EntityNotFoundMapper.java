package exceptions;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by geh on 5-4-14.
 */
@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException>
{
    @Override
    public Response toResponse(EntityNotFoundException e)
    {
        return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
