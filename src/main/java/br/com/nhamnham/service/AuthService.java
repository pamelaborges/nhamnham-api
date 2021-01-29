package br.com.nhamnham.service;

import br.com.nhamnham.infrastructure.security.exception.AuthenticationException;
import br.com.nhamnham.infrastructure.security.jwt.JWTTokenUtils;
import br.com.nhamnham.model.User;
import br.com.nhamnham.resource.dto.AuthRequest;
import br.com.nhamnham.resource.dto.AuthResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.temporal.TemporalAmount;

@ApplicationScoped
public class AuthService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    public AuthResponse login(AuthRequest request){
        final User user = User.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .orElseThrow(()-> new AuthenticationException());
        final AuthResponse response = new AuthResponse();
        final String tokenJWT = JWTTokenUtils.generate(user, issuer, Instant.now().plusSeconds(1440));
        response.setToke(tokenJWT);
        return response;
    }
}
