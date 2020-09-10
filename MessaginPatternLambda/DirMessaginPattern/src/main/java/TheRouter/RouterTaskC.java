package TheRouter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
Receiving end of our Router, simple a System out println to see if it worked. Obviously this pattern is meant for more difficult tasks
 */
public class RouterTaskC implements RequestHandler<Object, Object> {

    private ObjectMapper objectMapper = null;
    private String input;

    @Override
    public Object handleRequest(Object i, Context context) {
        objectMapper = new ObjectMapper();
        try {
            input = objectMapper.writeValueAsString(i);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(input);
        return null;
    }
}
