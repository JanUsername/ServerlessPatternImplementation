package FrugalConsumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;

/*
TODO: comment and general refactoring
 */
public class DirectToSQS {
    private static final String QUEUE_NAME = "ScalableWebhook";


    public static void main(String[] args) {
        //Build the client and get the Url for the SQS you created online
        //Build the client and get the Url for the SQS you created online
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();
        System.out.println(queueUrl);

        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(queueUrl)
                .withEntries(
                        new SendMessageBatchRequestEntry(
                                "filenameLambda1", "Hello from message 1"),
                        new SendMessageBatchRequestEntry(
                                "filenameLambda12", "Hello from message 2")
                                .withDelaySeconds(1));
        sqs.sendMessageBatch(send_batch_request);


    }
}
