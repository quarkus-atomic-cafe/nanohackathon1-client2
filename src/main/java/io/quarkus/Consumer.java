package io.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

// Reads messages from the Kafak topic and saves it into a database
@ApplicationScoped
public class Consumer {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;


    @ConfigProperty(name = "ENV", defaultValue="env_not_set")
    String name;

    int msgCounter = 0;
    @Incoming("hackathon-in")                                                                                                          
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING) 
    public void process(SourceObject sourceObject) {
                 

        System.out.println("Recieved msg # " + msgCounter +  " -> " + sourceObject);

        sourceObject.setName(name);
        sourceObject.save(client).subscribe().with(item -> System.out.println("Saved to Database # " + msgCounter++ +  " -> " + sourceObject));

        System.out.println("Processing done");    

    }
}
