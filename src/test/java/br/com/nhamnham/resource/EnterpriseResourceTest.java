package br.com.nhamnham.resource;

import br.com.nhamnham.container.MongoDbResource;
import br.com.nhamnham.infrastructure.security.jwt.JWTTokenUtils;
import br.com.nhamnham.model.Enterprise;
import br.com.nhamnham.model.User;
import br.com.nhamnham.resource.enumeration.Roles;
import br.com.nhamnham.service.EnterpriseService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

@QuarkusTest
@QuarkusTestResource(MongoDbResource.class)
public class EnterpriseResourceTest {

    @Test
    public  void testNotAuthorizedCreateEnterprise(){
        User user = new User();
        user.id = new ObjectId();
        user.setUsername("Nome");
        user.setRoles(new HashSet<>(Arrays.asList(Roles.ENTERPRISE_OPERATION.getName())));

        String bearerToken = JWTTokenUtils.generate(user, "http://localhost:8080/login");

        RestAssured.given()
                .auth().oauth2(bearerToken)
                .header("Content-Type", "application/json")
                .when().post("/enterprise")
                .then()
                .statusCode(403);
    }

    @Test
    public  void testWithoutAuthCreateEnterprise(){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .when().post("/enterprise")
                .then()
                .statusCode(401);
    }

    @Test
    public  void testTokenExpiredCreateEnterprise(){
        User user = new User();
        user.id = new ObjectId();
        user.setUsername("Nome");
        user.setRoles(new HashSet<>(Arrays.asList(Roles.ENTERPRISE_OPERATION.getName())));

        String bearerToken = JWTTokenUtils.generate(user, "http://localhost:8080/login", Instant.now().minusSeconds(10));

        RestAssured.given()
                .auth().oauth2(bearerToken)
                .header("Content-Type", "application/json")
                .when().post("/enterprise")
                .then()
                .statusCode(401);
    }

    @Test
    public void testSuccessCreatedEnterprise(){
        User user = new User();
        user.id = new ObjectId();
        user.setUsername("Nome");
        user.setRoles(new HashSet<>(Arrays.asList(Roles.ADMIN.getName())));

        String bearerToken = JWTTokenUtils.generate(user, "http://localhost:8080/login");

        Enterprise enterprise = new Enterprise();
        enterprise.setName("Testee");

        RestAssured.given()
                .auth().oauth2(bearerToken)
                .header("Content-Type", "application/json")
                .body(enterprise)
                .when().post("/enterprise")
                .then()
                .statusCode(201);
    }

}
