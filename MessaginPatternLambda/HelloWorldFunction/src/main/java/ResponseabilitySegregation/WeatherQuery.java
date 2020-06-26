package ResponseabilitySegregation;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;
import java.util.stream.Collectors;
/*
Query the Dynmodb table with the tableName Weather RS ans Sysouts the results
 */

public class WeatherQuery implements RequestHandler<Object, Object> {

    private AmazonDynamoDB dynamoDB = null;
    private final String tableName = "WeatherRS";


    @Override
    public Object handleRequest(Object o, Context context) {
       dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();

       final ScanRequest scanRequest = new ScanRequest()
               .withTableName(tableName)
               .withLimit(10);
       final ScanResult scanResult = dynamoDB.scan(scanRequest);

       final List<WeatherPojo> events = scanResult.getItems().stream().map(item -> new WeatherPojo(
               item.get("location").getS(),
               Double.parseDouble(item.get("temperature").getN()),
               Long.parseLong(item.get("timestamp").getN()),
               Integer.parseInt(item.get("sunhour").getN())
       )).collect(Collectors.toList());

        for (WeatherPojo event:events) {
            System.out.println(event);
        }
        return null;
    }
}
