package br.com.nhamnham.infrastructure.security.jwt;

import br.com.nhamnham.model.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Instant;
import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;


public class JWTTokenUtils {

    public static final String CLAIM_ENTERPRISE = "enterprise";

    public static String generate(User user, String issuer, Instant expires) {

        if(isNull(user) || isNull(user.id)|| isNull(user.getUsername())){
            throw new RuntimeException("Parametros obrigatorios inexistentes");
        }

        JwtClaimsBuilder builder = Jwt.issuer(issuer)
                // Reserved claims
                .claim(Claims.iss.name(), issuer) // emissor do token
                .claim(Claims.sub.name(), user.id) // id do usuario
                // Public claims
                .claim(Claims.nickname.name(), user.getUsername())
                .groups(user.getRoles());

        if (nonNull(user.getEnterpriseId())) {
            builder = builder.claim(CLAIM_ENTERPRISE, user.getEnterpriseId());
        }

        if (nonNull(expires) ){
            builder = builder.expiresAt(expires);
        }

        return builder.sign();
    }

    public static String generate(User user, String issuer) {
        return generate(user, issuer, null);
    }

}
