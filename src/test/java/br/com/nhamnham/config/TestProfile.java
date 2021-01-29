package br.com.nhamnham.config;

import br.com.nhamnham.container.MongoDbContainer;
import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class TestProfile implements QuarkusTestProfile {

    @Override
    public Map<String, String> getConfigOverrides() {
        String urlMongoDb = "mongodb://"+MongoDbContainer.MONGODB_HOST+":"+MongoDbContainer.MONGODB_PORT+"nhamnham?retryWrites=true&w=majority";
        return Collections.singletonMap("quarkus.mongodb.connection-string", urlMongoDb);
    }

}
