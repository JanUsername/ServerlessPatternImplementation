package PackageFanOutFanIn;


import java.util.HashMap;
import java.util.Map;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * Handler for requests to Lambda function.
 */
public class FanIn implements RequestHandler<SNSEvent, Object> {

    private final String BUCKETNAME = "fanoutpattern";
    private static AmazonS3 s3  = null;

    public Object handleRequest(final SNSEvent input, final Context context) {

        /*
        creates a S3 Client so you can access the service
         */

        AmazonS3 s3 =  AmazonS3ClientBuilder.standard().build();

        /*
        content = The body in the message, writes in the file
        filename = get's the filename from the message subject and call the s3 file it
         */
        String content = input.getRecords().get(0).getSNS().getMessage();
        String filename = input.getRecords().get(0).getSNS().getSubject();

        //puts everything in the bucket

        s3.putObject(BUCKETNAME,filename , content);
        //creates and return the Gateway response
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String output = String.format("{ \"message\": \"sns recived\", \"location\": \"oregon west\" }");
        return new GatewayResponse(output, headers, 200);
    }
}

