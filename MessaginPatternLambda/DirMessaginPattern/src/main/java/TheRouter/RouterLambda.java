package TheRouter;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RouterLambda implements RequestHandler<Object, Object> {
    private AWSLambda lambdaClient = null;
    private InvokeRequest request = null;
    private InvokeResult result = null;
    //Our Lambda ARN which we want to call
    private static String functionNameA = "arn:aws:lambda:us-west-2:733474897506:function:RouterTaskA";
    private static String functionNameB = "arn:aws:lambda:us-west-2:733474897506:function:RouterTaskB";
    private static String functionNameC = "arn:aws:lambda:us-west-2:733474897506:function:RouterTaskC";
    //The payload as POJO will be transformed to a valid JSON String
    private ObjectMapper objectMapper = null;
    private String payload;
    private RouterPayloadPojo routerPayload;

    //TODO Void Context?
    @Override
    public Object handleRequest(Object o, Context context) {

        //routerPayload = new RouterPayloadPojo("A", "ContentA");
        routerPayload = new RouterPayloadPojo("B", "ContentB");
        //routerPayload = new RouterPayloadPojo("C", "ContentC");
        lambdaClient = AWSLambdaClientBuilder.defaultClient();
        request = new InvokeRequest();

        /*
        The payload of a lambda has to be a valid Json String so we map the Pojo Object to a one
         */
        objectMapper = new ObjectMapper();
        try {
            payload = objectMapper.writeValueAsString(routerPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*
        Depending on which Lambda we want, we create the request with the Lambda ARN
        Here we invoke always Async, seen by Invocation Type Event
         */
        switch (routerPayload.getRouterEndPoint()) {
            case "A":
                request.withFunctionName(functionNameA)
                        .withInvocationType(InvocationType.Event)
                        .withPayload(payload);
                break;
            case "B":
                request.withFunctionName(functionNameB)
                        .withInvocationType(InvocationType.Event)
                        .withPayload(payload);
                break;
            case "C":
                request.withFunctionName(functionNameC)
                        .withInvocationType(InvocationType.Event)
                        .withPayload(payload);
                break;
        }
        //Invoking the Request
        result = lambdaClient.invoke(request);
        //TODO Use standard API? or create an own? leave null for less verbose?
        return null;
    }
}
