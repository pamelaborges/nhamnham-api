package br.com.nhamnham.container;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Collections;
import java.util.Map;

public class MongoDbResource implements QuarkusTestResourceLifecycleManager {

    MongoDbContainer mongoDbContainer = new MongoDbContainer();

    @Override
    public Map<String, String> start() {
        mongoDbContainer.start();
        return Collections.singletonMap("quarkus.mongodb.connection-string", "mongodb://" +mongoDbContainer.getHost() + ":"+ mongoDbContainer.getPort());
    }

    @Override
    public void stop() {
        mongoDbContainer.stop();
    }
}
