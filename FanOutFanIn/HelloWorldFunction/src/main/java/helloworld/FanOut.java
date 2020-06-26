package helloworld;


import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class FanOut {
    /*
        The Topic ARN for the SNS we are created online
     */
    private static final String TOPIC_ARN_EMAIL = "arn:aws:sns:us-west-2:733474897506:LambdaTest";
    private static AmazonSNSClient snsClient = null;

    public static void main(String[] args) {
        /*
        A  Map which we will individually send out; this could be switched with anything even other AWS Events
         */
        Map<String, String> fanOutList = new Hashtable<>();
        fanOutList.put("Subject1", "ContentA");
        fanOutList.put("Subject2", "ContentB");
        fanOutList.put("Subject3", "ContentC");
        fanOutList.put("Subject4", "ContentD");

        //builds the SNSClient
        snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard().build();

        //interates thorugh the Map and publishes through the SNS client the content individually which will create the FanOut
        Set<String> keys = fanOutList.keySet();
        for (String key : keys) {
            snsClient.publish(TOPIC_ARN_EMAIL, fanOutList.get(key), key);

        }
    }
}