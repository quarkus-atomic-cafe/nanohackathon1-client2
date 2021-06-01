package io.quarkus;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

// Define the Pojo to deserialize the Json msg from Kafaka to  
public class SourceObjectDeserializer extends JsonbDeserializer<SourceObject> {
    public SourceObjectDeserializer(){
        // pass the class to the parent.
        super(SourceObject.class);
    }
}
