package com.debbech.divide.processor.scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import veryfi.Client;
import veryfi.VeryfiClientFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class Scanner implements IScanner{

    @Value("${scanner.veryfi.clientId}")
    private String clientId;
    @Value("${scanner.veryfi.clientSecret}")
    private String clientSecret;
    @Value("${scanner.veryfi.username}")
    private String username;
    @Value("${scanner.veryfi.apiKey}")
    private String apiKey;

    @Override
    public JSONObject execute(String imagePath) throws Exception {
        String clientId = this.clientId;
        String clientSecret = this.clientSecret;
        String username = this.username;
        String apiKey = this.apiKey;

        Client client = VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey);
        List<String> categories = Arrays.asList("Advertising & Marketing", "Automotive");
        String jsonResponse = client.processDocument(imagePath, categories, false, null);
        JSONObject jo = new JSONObject(jsonResponse);

        return jo;
    }
}