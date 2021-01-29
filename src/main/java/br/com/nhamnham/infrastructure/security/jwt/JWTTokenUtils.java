package br.com.nhamnham.infrastructure.security.jwt;

import br.com.nhamnham.model.User;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

public class JWTTokenUtils {

    public static String generate(User user, Long duration, String issuer) {
        return Jwt.issuer(issuer)
                // Reserved claims
                .claim(Claims.iss.name(), issuer) // emissor do token
                .claim(Claims.sub.name(), user.id) // id do usuario
                // Public claims
                .claim(Claims.nickname.name(), user.getUsername())
                .groups(user.getRoles()).sign();
    }

}
