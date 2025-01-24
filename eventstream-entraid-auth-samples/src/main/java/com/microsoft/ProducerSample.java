package com.microsoft;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

import java.util.Arrays;
import java.util.List;

public class ProducerSample {
    private static final String eventHubNameSpace = "yuljineh.servicebus.windows.net";
    private static final String eventHubName = "aadtest";

    /**
     * Run the producer sample code.
     *
     * @param args No args needed for this sample.
     */
    public static void main(String[] args) {
        System.out.println("Using AZURE_CLIENT_ID: " + System.getenv("AZURE_CLIENT_ID"));
        System.out.println("Using AZURE_TENANT_ID: " + System.getenv("AZURE_TENANT_ID"));
        System.out.println("Using AZURE_CLIENT_CERTIFICATE_PATH: " + System.getenv("AZURE_CLIENT_CERTIFICATE_PATH"));
        System.out.println("Using AZURE_CLIENT_CERTIFICATE_PASSWORD: " + System.getenv("AZURE_CLIENT_CERTIFICATE_PASSWORD"));

        publishEvents();
        System.exit(0);
    }

    /**
     * Code sample for publishing events.
     *
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public static void publishEvents() throws IllegalArgumentException {
        // The default credential checks environment variables for configuration.
        // For Details, please refer to https://learn.microsoft.com/en-us/java/api/overview/azure/identity-readme?view=azure-java-stable#environment-variables
        TokenCredential credential = new DefaultAzureCredentialBuilder().build();

        // Create a producer client to send events to the event hub.
        EventHubProducerClient producer = new EventHubClientBuilder()
                .credential(eventHubNameSpace, eventHubName, credential)
                .buildProducerClient();

        // Generate a list of events to send.
        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));

        // Create an event data batch.
        EventDataBatch eventDataBatch = producer.createBatch();

        // Loop over the list of events and send them in batches.
        for (EventData eventData : allEvents) {
            System.out.println("Sending Event: " + eventData.getBodyAsString());

            // Try to add the event to the batch.
            if (!eventDataBatch.tryAdd(eventData)) {
                // If the batch is full, send it and then create a new batch.
                System.out.println("Batch is full, sending batch");

                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }

        // Send the last batch of remaining events.
        if (eventDataBatch.getCount() > 0) {
            System.out.println("Sending last batch");
            producer.send(eventDataBatch);
        }

        producer.close();
    }
}
