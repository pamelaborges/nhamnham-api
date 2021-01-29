package br.com.nhamnham.resource;

import br.com.nhamnham.infrastructure.security.jwt.JWTTokenUtils;
import br.com.nhamnham.resource.dto.UserDto;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserResource {

    @Inject
    UserService service;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"ENTERPRISE_ADMIN", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Cadastra um usuario")
    public Response insert(UserDto user){

        boolean isAdmin = jwt.getGroups().stream().filter(g-> g.equals(Roles.ADMIN.getName())).count() > 0;
        String enterprise = !isAdmin ? (String) jwt.claim(JWTTokenUtils.CLAIM_ENTERPRISE)
                .orElseThrow(() -> new ForbiddenException()): null;

        service.insert(UserDto.toDomain(user), enterprise);
        return Response.created(null).build();
    }

    @Path("/{id}")
    @PATCH
    @RolesAllowed({"ENTERPRISE_ADMIN", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Vincula permissões a um usuario")
    public Response configureRoles(@PathParam("id") final String id, List<Roles> roles){
        service.configureRoles(roles, id);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed({"ENTERPRISE_ADMIN", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Lista todos os usuários da empresa")
    public Response list(){

        boolean isAdmin = jwt.getGroups().stream().filter(g-> g.equals(Roles.ADMIN.getName())).count() > 0;
        String enterprise = !isAdmin ? (String) jwt.claim(JWTTokenUtils.CLAIM_ENTERPRISE)
                .orElseThrow(() -> new ForbiddenException()): null;

        return Response.ok(service.listByEnterprise(enterprise).
                stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList())).build();
    }



}
