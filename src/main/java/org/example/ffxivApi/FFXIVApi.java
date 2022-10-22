package org.example.ffxivApi;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.JSONFile;
import org.example.ffxivApi.models.Character;
import org.example.ffxivApi.models.ResultContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FFXIVApi {
    private String apiKey = "";
    private static final String URL = "https://xivapi.com";

    private static FFXIVApi ffxivAPI = null;

    private FFXIVApi()
    {
        this.apiKey = JSONFile.getJSONValueFromFile("ffxivAPIKey", "keys.json");
    }

    public static FFXIVApi getInstance(){
        if(ffxivAPI == null)
            ffxivAPI = new FFXIVApi();

        return ffxivAPI;
    }


    public List<Character> getCharacters(String name, String server) {

        try
        {
            String searchUrl = buildURL(String.format("/character/search?name=%s&server=%s", name, server));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(URI.create(searchUrl)).header("accept", "application/json").build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            Map<String, String> json = JSONFile.parseJsonStringForMap(body);
            ObjectMapper mapper = new ObjectMapper();

            try(JsonParser parser = new JsonFactory().createParser(body);) {
                JavaType type = mapper.getTypeFactory().constructParametricType(ResultContainer.class, Character.class);
//                ResultContainer<Character> characterResultContainer = mapper.readValue(body, new TypeReference<ResultContainer<Character>>() {});
                ResultContainer<Character> characterResultContainer = mapper.readValue(body, type);

                System.out.println(characterResultContainer);

                return characterResultContainer.getResults();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    public String getFreeCompanyid(String searchTerm) {
        return "";
    }

    public String buildURL(String searchFields){
        return URL + searchFields.replaceAll(" ", "+") + "&private_key=" + apiKey;
    }
}
