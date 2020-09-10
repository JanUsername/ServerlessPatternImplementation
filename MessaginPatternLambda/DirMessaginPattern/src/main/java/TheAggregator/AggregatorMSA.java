package TheAggregator;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AggregatorMSA implements RequestHandler<Object,Object> {

    private ObjectMapper objectMapper = null;
    private String input;
    private String returnValue;
    @Override
    public Object handleRequest(Object i, Context context) {
        objectMapper = new ObjectMapper();
        try {
            input = objectMapper.writeValueAsString(i);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(input);
        returnValue = input + " processed by AggregatorMSA";
        System.out.println(returnValue);
        return returnValue;
    }
}
