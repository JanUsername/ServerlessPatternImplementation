package PollingEventProcessor;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handler for requests to Lambda function.
 */
public class PollingEventProcessor implements RequestHandler<Object, Object> {

    private AmazonDynamoDB client = null;
    private DynamoDB dynamoDB = null;
    private final String tableName = "StateCheck";
    private final String fieldName = "CycleCount";
    private final String state = "\"Original State\"";
    private String resultAsJsonString;
    private QuerySpec spec = null;
    private ItemCollection<QueryOutcome> items;
    private Iterator<Item> iterator;
    private Item item = null;
    private ObjectNode node;

    @Override
    public Object handleRequest(Object o, Context context) {
        dynamoDB = DynamoDBConnect.connectToDB();
        iterator = DynamoDBConnect.queryTable(dynamoDB, tableName).iterator();
        /*
        while (iterator.hasNext()) {
            item = iterator.next();
            resultAsJsonString = item.toJSON();
        }

        try {
            node = new ObjectMapper().readValue(resultAsJsonString, ObjectNode.class);
            if (node.has(fieldName)) {
                System.out.println( node.get(fieldName) + " " + state);
                if(node.get(fieldName).toString().equals(state)){
                    System.out.println("equal");
                }else{
                    System.out.println("not equal");
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        resultAsJsonString = DynamoDBConnect.getField(iterator);
        System.out.println(resultAsJsonString);
        if(resultAsJsonString.equals(state)){
            System.out.println("equals yay");
        }else{
            System.out.println("not equal");
        }
        return null;
    }
}
