package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class RequestHandler implements com.amazonaws.services.lambda.runtime.RequestHandler<Object, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(Object input, Context context) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody("{\"me\":\"cool\"}")
                .withIsBase64Encoded(false);
    }
}
