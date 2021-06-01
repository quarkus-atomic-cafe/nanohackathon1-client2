package io.quarkus;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

// Pojo for the message from Kafka as well as for saving to the DB
@RegisterForReflection
public class SourceObject {

    public String token;

    public String name;

    public SourceObject() {
    }

    public SourceObject(String token) {
        this.token = token;
    }

    public SourceObject(String token, String name) {

        this.token = token;
        this.name = name;
    }

    public static SourceObject from(Long token) {
        return new SourceObject(token.toString());
    }


    @Override
    public String toString() {
        return "SourceObject [token=" + token + "]";
    }

    public Uni<String> save(PgPool client) {
        return client.preparedQuery("INSERT INTO hackathontarget (token, name) VALUES ($1,$2) RETURNING token")
                .execute(Tuple.of(token, name)).onItem()
                .transform(pgRowSet -> pgRowSet.iterator().next().getString("token"))
                .onItem().invoke(i -> System.out.println("Received item " + i))
                .onCancellation().invoke(() -> System.out.println("The downstream does not want our items anymore!"))
                .onFailure().invoke(t -> System.out.println("Oh no! We received a failure: " + t.getMessage()))
                .onFailure().retry().atMost(3);

    }

    public void setName(String string) {
        this.name = string;
    }
}
