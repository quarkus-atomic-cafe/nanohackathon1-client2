package io.quarkus;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;

//Initialized a databe schema in case of a local DB test 
@ApplicationScoped
public class DBInitializer {

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "false")
    boolean schemaCreate;

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @PostConstruct
    void config() {

        System.out.println("config");

        if (schemaCreate) {
            initdb();
        }
    }

    void onStart(@Observes StartupEvent ev) {

        initdb();
    }

    private void initdb() {

        System.out.println("Init DB");

        client.query("DROP TABLE IF EXISTS hackathontarget").execute().flatMap(
                r -> client.query("CREATE TABLE hackathontarget (token VARCHAR(255), name VARCHAR(255))").execute())
                .await().indefinitely();
    }

}
