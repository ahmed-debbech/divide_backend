package com.debbech.divide.processor;

import com.debbech.divide.processor.models.Order;
import com.debbech.divide.services.interfaces.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderExporter {

    public List<Map.Entry<String, Order>> exportedOrders = new ArrayList<>();

    public void export(String id, Order o){
        exportedOrders.add(Map.entry(id, o));
    }
}
