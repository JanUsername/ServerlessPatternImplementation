package ResponseabilitySegregation;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/*
TODO: Input from API?
Inserts a Weather into da DynamoDB
 */
public class WeatherInsert implements RequestHandler<Object, Object> {

    private DynamoDB dynamoDB = null;
    private final String tableName = "WeatherRS";
    private WeatherPojo weather;

    @Override
    public Object handleRequest(Object o, Context context) {
        weather = new WeatherPojo("Merano", 28, System.currentTimeMillis(), 4);
        dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
        final Table table = dynamoDB.getTable(tableName);
        final Item item = new Item()
                .withPrimaryKey("ID", "2")
                .withString("location", weather.location)
                .withDouble("temperature", weather.temperature)
                .withLong("timestamp", weather.timestamp)
                .withInt("sunhour", weather.sunhour);
        table.putItem(item);

        return null;
    }
}
