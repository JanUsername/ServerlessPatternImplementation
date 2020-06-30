package PollingEventProcessor;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.util.Iterator;

public class DynamoDBUtility {

    public static DynamoDB connectToDB() {
        final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);
        return dynamoDB;
    }

    //query with statements
    public static ItemCollection<QueryOutcome> queryTable(DynamoDB dynamoDB, String tableName) {
        Table table = dynamoDB.getTable(tableName);
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("TheState = :s_id")
                .withValueMap(new ValueMap()
                        .withString(":s_id", "StateA"));
        ItemCollection<QueryOutcome> items = table.query(spec);
        return items;
    }

    public static String getField(Iterator<Item> iterator)  {//throws JsonProcessingException
        while (iterator.hasNext()) {
            Item item = iterator.next();
            String resultAsJsonString = item.toJSON();
            System.out.println(resultAsJsonString);
            ObjectNode node = null;
            try {
                node = new ObjectMapper().readValue(resultAsJsonString, ObjectNode.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            final String fieldName = "CycleCount";
            System.out.println(node.get(fieldName).toString());
             if (node.has(fieldName)) {
                return node.get(fieldName).toString();
             } else{
                 return "field empty";
             }
        }
       return "no data found";
    }
}
