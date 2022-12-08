package org.example.notion;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotionAPI {
    private String databaseid;
    private ObjectMapper om = new ObjectMapper();
    public NotionAPI(String databaseid) {
    this.databaseid=databaseid;
    }

    public void call(String jobname, String status,String joburl) throws IOException {
        Root root = new Root();
        root.parent = new Parent();
        root.parent.database_id = databaseid;
        root.icon = new Icon();
        root.icon.emoji = "😀";
        root.cover = new Cover();
        root.cover.external = new External();
        root.cover.external.url = "https://upload.wikimedia.org/wikipedia/commons/6/62/Tuscankale.jpg";
        root.properties = new Properties();
        root.properties.jobName = new JobName();
        Title title = new Title();
        title.type="text";

        Text text = new Text();
        text.content = jobname;
        title.text=text;

        root.properties.jobName.title = new ArrayList<>();
        root.properties.jobName.title.add(title);

        root.properties.jobLink = new JobLink();
        RichText richText = new RichText();
        richText.type = "text";
        Text link = new Text();
        link.content = joburl;

        richText.text =link;
        root.properties.jobLink.rich_text = new ArrayList<>();
        root.properties.jobLink.rich_text.add(richText);

        root.properties.status = new Status();
        root.properties.status.select = new Select();
        root.properties.status.select.name = status;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String content = om.writeValueAsString(root);
        System.out.println(content);
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://api.notion.com/v1/pages")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + System.getenv("NotionApiKey"))
                .addHeader("Content-Type", "application/json")
                .addHeader("Notion-Version", "2022-06-28")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
