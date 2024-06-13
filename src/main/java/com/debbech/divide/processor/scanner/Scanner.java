package com.debbech.divide.processor.scanner;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import veryfi.Client;
import veryfi.VeryfiClientFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class Scanner implements IScanner{
    @Override
    public void execute(String imagePath) throws Exception {
        String clientId = "vrfKwUgUD8XKPMBFS15tpwScFGCBYV9rPmPxn6t";
        String clientSecret = "64yBBOzRVSE5anvL3pKEYCYfiM9toxgOYywU8xigon7MMYaB3nLALADa4HyfSse4RZJSJOuutjG4ybVi6gJmUAIrlR1GS5i64VBEEKAxoLLhvT1sCm2obKa8id04CXj8";
        String username = "debbech.ahmed";
        String apiKey = "b63417f77345303879364aac9a22473b";
        Client client = VeryfiClientFactory.createClient(clientId, clientSecret, username, apiKey);
        List<String> categories = Arrays.asList("Advertising & Marketing", "Automotive");
        JSONObject jo = new JSONObject();
        String jsonResponse = client.processDocument(imagePath, categories, false, jo);
    }
}
