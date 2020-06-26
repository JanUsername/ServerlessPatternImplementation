package ScalableWebhook;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
/*
TODO: comment and general refactoring
 */
public class LambdaToSQS implements RequestHandler<Object, Object> {

    private static final String QUEUE_NAME = "ScalableWebhook";
    public Object handleRequest(final Object input, final Context context) {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(queueUrl)
                .withEntries(
                        new SendMessageBatchRequestEntry(
                                "msg_2", "Hello from message Lamba 1"),
                        new SendMessageBatchRequestEntry(
                                "msg_3", "Hello from message Lambda 2")
                                .withDelaySeconds(1));
        sqs.sendMessageBatch(send_batch_request);
        return null;
    }
}


