package com.techprimers.elastic.standaloneelasticexample.resource;

import com.techprimers.elastic.standaloneelasticexample.entity.Comments;
import com.techprimers.elastic.standaloneelasticexample.service.ApisService;
import org.apache.wink.json4j.JSONException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private ApisService apisService;

    public UsersResource(ApisService apisService) throws UnknownHostException {
        this.apisService = apisService;
    }


    @GetMapping("/comments")
    public void addComment() throws IOException, JSONException {
        List<Comments> comments = new ArrayList<>();
        List<Map<String, Object>> list = apisService.getComments();
        System.out.println("Comentarios: " + list.size());

        for (int j = 0; j < 10; j++) {
            int comm = 0;
            LocalDateTime ini = LocalDateTime.now();
            Long init = System.currentTimeMillis();
            for (int i = 0; i < list.size(); i++) {
                comm++;
                try {
                    Comments comentario = new Comments();
                    comentario.setLikes(Integer.parseInt(list.get(i).get("likes").toString()));
                    comentario.setText(list.get(i).get("comment").toString());
                    comments.add(comentario);
                } catch (Exception e) {
                }
            }
            Long end = System.currentTimeMillis();
            LocalDateTime ended = LocalDateTime.now();
            System.out.println("Medición " + (j + 1) + " " + (end - init) + " Comments " + comm);
            long seconds = ChronoUnit.MILLIS.between(ini, ended);
            System.out.println(seconds);
//            System.out.println(comments.size());
        }
    }
}
