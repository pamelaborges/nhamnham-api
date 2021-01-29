package br.com.nhamnham.resource;

import br.com.nhamnham.model.Enterprise;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.EnterpriseService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/enterprise")
@RequestScoped
public class EnterpriseResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    EnterpriseService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    @Operation(summary = "Cadastra uma nova empresa")
    public Response insert(@Context SecurityContext ctx, Enterprise enterprise){
        service.insert(enterprise);
        return Response.created(null).build();
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Operation(summary = "Deleta uma nova empresa")
    @Path("/{id}")
    public Response delete(@PathParam("id") String id){
        service.delete(id);
        return Response.ok().build();
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Operation(summary = "Atualiza uma nova empresa")
    @Path("/{id}")
    public Response update(Enterprise enterprise){
        service.update(enterprise);
        return Response.ok().build();
    }

}
