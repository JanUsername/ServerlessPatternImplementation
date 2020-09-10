package FrugalConsumer;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/*
TODO: comment and general refactoring
 */
public class AWSDocHandlerSQS implements RequestHandler<SQSEvent, Void>{

    private final String BUCKETNAME = "fanoutpattern";
    private static AmazonS3 s3  = null;

    @Override
    public Void handleRequest(SQSEvent event, Context context)
    {
        AmazonS3 s3 =  AmazonS3ClientBuilder.standard().build();
        for(SQSMessage msg : event.getRecords()){
            s3.putObject(BUCKETNAME, msg.getMessageId(), msg.getBody());
            System.out.println((msg.getBody()));
        }
        return null;
    }
}