package org.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.notion.NotionAPI;

import java.io.IOException;
import java.util.Map;

public class RequestHandler implements com.amazonaws.services.lambda.runtime.RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Map<String, String> headers = Map.of("Access-Control-Request-Method", "OPTIONS,POST,GET", "Access-Control-Allow-Headers", "Content-Type", "Access-Control-Allow-Origin", "*");
        if(input.getHttpMethod().equals("POST")){
            Map circle = null;
            try {
                circle = mapper.readValue(input.getBody(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String job = (String) circle.get("job_name");
            String joburl = (String) circle.get("job_url");
            String status = (String) circle.get ("status");
            String database_id = (String) circle.get ("database_id");
            NotionAPI notionapi=new NotionAPI(database_id);
            try {
                notionapi.call(job,status, joburl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Map<String, String> body = Map.of("me", job);
            String jsonStr = null;
            try {
                jsonStr = mapper.writeValueAsString(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(jsonStr)
                    .withIsBase64Encoded(false)
                    .withHeaders(headers);
        }
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody("{\"me\":\"cool\"}")
                .withIsBase64Encoded(false)
                .withHeaders(headers);
    }
}
