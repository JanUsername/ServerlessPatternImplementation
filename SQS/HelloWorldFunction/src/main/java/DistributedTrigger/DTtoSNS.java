package DistributedTrigger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
/*
TODO: Comment, find a an example where 3 services need working on one thing
 */
//Dt -> Distributed Trigger
public class DTtoSNS implements RequestHandler<Object, Object> {
     private static final String TOPIC_ARN_EMAIL = "arn:aws:sns:us-west-2:733474897506:DistributedTrigger";
    private static AmazonSNSClient snsClient = null;

    @Override
    public Object handleRequest(Object input, Context context) {

        Map<String, String> dtFanOut = new Hashtable<>();
        dtFanOut.put("Football", "ContentA");
        dtFanOut.put("Basketball", "ContentB");
        dtFanOut.put("IceHockey", "ContentC");

         snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard().build();

        //interates thorugh the Map and publishes through the SNS client the content individually which will create the FanOut
        Set<String> keys = dtFanOut.keySet();
        for (String key : keys) {
            snsClient.publish(TOPIC_ARN_EMAIL, dtFanOut.get(key), key);

        }
        return null;
    }
}
