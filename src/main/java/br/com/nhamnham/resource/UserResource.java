package br.com.nhamnham.resource;

import br.com.nhamnham.model.User;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {

    @Inject
    UserService service;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Cadastra um usuario")
    public Response insert(User user){
        service.insert(user);
        return Response.created(null).build();
    }

    @Path("/{id}")
    @PATCH
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Vincula permissões a um usuario")
    public Response configureRoles(@PathParam("id") final String id, List<Roles> roles){
        service.configureRoles(roles, id);
        return Response.ok().build();
    }



}
