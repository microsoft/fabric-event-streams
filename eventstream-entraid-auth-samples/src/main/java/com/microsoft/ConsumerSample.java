package com.microsoft;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;

import java.io.IOException;

public class ConsumerSample {
    private static final String eventHubNameSpace = "yuljineh.servicebus.windows.net";
    private static final String eventHubName = "aadtest";
    private static final String consumerGroupName = "yuljin";

    /**
     * Run the consumer sample code.
     *
     * @param args No args needed for this sample.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Using AZURE_CLIENT_ID: " + System.getenv("AZURE_CLIENT_ID"));
        System.out.println("Using AZURE_TENANT_ID: " + System.getenv("AZURE_TENANT_ID"));
        System.out.println("Using AZURE_CLIENT_CERTIFICATE_PATH: " + System.getenv("AZURE_CLIENT_CERTIFICATE_PATH"));
        System.out.println("Using AZURE_CLIENT_CERTIFICATE_PASSWORD: " + System.getenv("AZURE_CLIENT_CERTIFICATE_PASSWORD"));

        receiveEvents();
        System.exit(0);
    }

    /**
     * Code sample for publishing events.
     */
    public static void receiveEvents() {
        // The default credential checks environment variables for configuration.
        // For Details, please refer to https://learn.microsoft.com/en-us/java/api/overview/azure/identity-readme?view=azure-java-stable#environment-variables
        TokenCredential credential = new DefaultAzureCredentialBuilder().build();

        // Create a consumer client to receive events from the event hub.
        EventHubConsumerAsyncClient consumer = new EventHubClientBuilder()
                .credential(eventHubNameSpace, eventHubName, credential)
                .consumerGroup(consumerGroupName)
                .buildAsyncConsumerClient();

        // Receive all events.
        consumer.receive().subscribe(event -> {
            // Process each event as it arrives.
            System.out.println("Receiving event: " + event.getData().getBodyAsString());
        });

        // Wait for the user to press enter to stop the consumer client.
        System.out.println("Press any key to stop");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }

        System.out.println("Stopping the consumer client");
        consumer.close();
        System.out.println("Consumer client stopped");
    }
}
