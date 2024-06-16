package com.debbech.divide.utils;

import com.debbech.divide.entity.User;
import com.debbech.divide.entity.enumer.Processing;
import com.debbech.divide.entity.receipt.Receipt;
import com.debbech.divide.entity.receipt.ReceiptData;
import com.debbech.divide.entity.receipt.ReceiptItem;
import com.debbech.divide.processor.models.ExtractedData;
import com.debbech.divide.processor.models.ExtractedItem;
import com.debbech.divide.processor.models.Order;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverters {

    public static ReceiptItem convert(ExtractedItem item){
        ReceiptItem ri = new ReceiptItem();
        ri.setDescription(item.getDescription());
        ri.setDiscount(item.getDiscount());
        ri.setTax(item.getTax());
        ri.setQuantity(item.getQuantity());
        ri.setText(item.getText());
        ri.setTotal(item.getTotal());
        ri.setWeight(item.getWeight());
        ri.setFullDescription(item.getFullDescription());
        return ri;
    }

    public static ReceiptData convert(ExtractedData data){
        ReceiptData rd = new ReceiptData();

        rd.setDiscount(data.getDiscount());
        rd.setTotal(data.getTotal());
        rd.setDeliveryDate(data.getDeliveryDate());
        rd.setSubtotal(data.getSubtotal());
        rd.setImgTumbUrl(data.getImgTumbUrl());
        rd.setVendorName(data.getVendorName());
        rd.setReference_number(data.getReference_number());
        List<ReceiptItem> list = new ArrayList<>();

        for(ExtractedItem ei : data.getLineItems()){
            ReceiptItem ri = convert(ei);
            list.add(ri);
        }
        rd.setLineItems(list);
        return rd;
    }

    public static Receipt convert(String id, Order o) {
        Receipt r = new Receipt();
        r.setOurReference(id);
        switch(o.getIsProcessing()){
            case ONGOING  :r.setIsProcessing(Processing.ONGOING); break;
            case DONE: r.setIsProcessing(Processing.DONE); break;
            case FAILED: r.setIsProcessing(Processing.FAILED); break;
            case NOT_READY: r.setIsProcessing(Processing.NOT_READY); break;
        }
        r.setFailureReason(o.getFailureReason());
        r.setReceiptImageFileName(o.getReceiptImageFileName());

        if(o.getExtractedData()
                != null)
            r.setReceiptData(convert(o.getExtractedData()));
        else
            r.setReceiptData(null);

        User u = new User();
        u.setUid(o.getUidInitiator());
        r.setInitiator(u);
        return r;
    }
}
