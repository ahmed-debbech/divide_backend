package com.debbech.divide.processor;

import com.debbech.divide.processor.models.ExtractedData;
import com.debbech.divide.processor.models.ExtractedItem;
import com.debbech.divide.services.impl.AuthService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderProcessorUtils {
    static String generateId(){
        StringBuilder sb = new StringBuilder();
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = lowercase.toUpperCase();
        String digits = "0123456789";

        String allChars = lowercase + uppercase + digits;

        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
    static String generateFileName(String orderId){
        String uid = AuthService.getLoggedInUser();
        return uid + "-" + orderId;
    }

    public static ExtractedData convert(JSONObject object) throws Exception {
        if(!object.get("document_type").equals("receipt") || (object.getJSONArray("line_items") == null))
            throw new Exception("the uploaded picture does not look like a receipt");
        if(object.isNull("line_items")) throw new Exception("This receipts looks empty!");

        ExtractedData ed = new ExtractedData();

        ed.setVendorName(object.isNull("vendor")? null
                : object.getJSONObject("vendor").isNull("name")? null
                : object.getJSONObject("vendor").get("name").toString() );

        ed.setDeliveryDate(object.isNull("date")? null
                : object.get("date").toString());

        ed.setReference_number(object.isNull("reference_number")? null
                : object.get("reference_number").toString());

        ed.setDiscount(object.isNull("discount")? null
                : object.getDouble("discount"));

        ed.setImgTumbUrl(object.isNull("img_thumbnail_url")? null
                : object.get("img_thumbnail_url").toString());

        ed.setTotal(object.isNull("total")? null
                : object.getDouble("total"));

        List<ExtractedItem> extractedItems = new ArrayList<>();
        JSONArray jarray = object.getJSONArray("line_items");
        for(int i=0; i<=jarray.length()-1; i++){
            ExtractedItem ei = new ExtractedItem();

            ei.setDescription(jarray.getJSONObject(i).isNull("description") ? null
                    : jarray.getJSONObject(i).get("description").toString());

            ei.setDiscount(jarray.getJSONObject(i).isNull("discount") ? null
                    : jarray.getJSONObject(i).getDouble("discount"));

            ei.setTotal(jarray.getJSONObject(i).isNull("total") ? null
                    : jarray.getJSONObject(i).getDouble("total"));

            ei.setFullDescription(jarray.getJSONObject(i).isNull("fullDescription") ? null
                    : jarray.getJSONObject(i).get("fullDescription").toString());

            ei.setText(jarray.getJSONObject(i).isNull("text") ? null
                    : jarray.getJSONObject(i).get("text").toString());

            ei.setQuantity(jarray.getJSONObject(i).isNull("quantity") ? null
                    : jarray.getJSONObject(i).getDouble("quantity"));

            ei.setWeight(jarray.getJSONObject(i).isNull("weight") ? null
                    : jarray.getJSONObject(i).getDouble("weight"));

            ei.setTax(jarray.getJSONObject(i).isNull("tax") ? null
                    : jarray.getJSONObject(i).getDouble("tax"));

            extractedItems.add(ei);
        }
        ed.setLineItems(extractedItems);

        return ed;
    }
}
