package br.com.nhamnham.resource;

import br.com.nhamnham.resource.dto.AuthRequest;
import br.com.nhamnham.resource.dto.AuthResponse;
import br.com.nhamnham.service.AuthService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class AuthResource {

    @Inject
    AuthService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(AuthRequest authRequest){
        return Response.ok(service.login(authRequest)).build();
    }
}
