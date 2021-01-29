package br.com.nhamnham.model;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;

import java.security.spec.KeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@MongoEntity(collection = "user")
public class User extends PanacheMongoEntity {

    private String name;
    private String username;
    private String email;
    private String password;
    @BsonProperty("enterprise_id")
    private String enterpriseId;
    private Set<String> roles;
    private LocalDateTime created;
    private LocalDateTime updated;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public static void insert(User user){
        user.setPassword(Sha2Crypt.sha256Crypt(user.getPassword().getBytes(), "$5$1") );
        user.setCreated(LocalDateTime.now());
        User.persist(user);
    }

    public static Optional<User> findByUsernameAndPassword(final String username, final String password){
       return find("{ username: ?1, password: ?2 }", username, Sha2Crypt.sha256Crypt(password.getBytes(), "$5$1")).firstResultOptional();
    }

    public static Stream<User> findByEnterprise(Integer enterpriseId){
        return find("enterprise_id", enterpriseId).stream();
    }

    public static boolean exists(String username){
        return User.find("username", username).count() > 0;
    }
}
