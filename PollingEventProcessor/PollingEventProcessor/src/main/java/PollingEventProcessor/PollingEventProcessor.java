package PollingEventProcessor;

import java.util.*;

import com.amazonaws.services.dynamodbv2.document.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Handler for requests to Lambda function.
 */
public class PollingEventProcessor implements RequestHandler<Object, Object> {

    private DynamoDB dynamoDB = null;
    private final String tableName = "StateCheck";
    private final String fieldName = "CycleCount";
    private final String state = "\"Original State\"";
    private String resultAsJsonString;

    private Iterator<Item> iterator;
    private Item item = null;

    @Override
    public Object handleRequest(Object o, Context context) {
        dynamoDB = DynamoDBUtility.connectToDB();
        iterator = DynamoDBUtility.queryTable(dynamoDB, tableName).iterator();
        resultAsJsonString = DynamoDBUtility.getField(iterator);
        if(resultAsJsonString.equals(state)){
            System.out.println("equals yay");
        }else{
            System.out.println("not equal");
        }
        return null;
    }
}
