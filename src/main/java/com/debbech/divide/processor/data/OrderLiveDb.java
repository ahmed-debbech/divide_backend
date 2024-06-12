package com.debbech.divide.processor.data;

import com.debbech.divide.processor.models.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderLiveDb implements IOrderLiveDb{

    Map<String, Order> orders = new HashMap<>();

    @Override
    public boolean set(String id, Order order) {
        try {
            orders.put(id, order);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Order get(String id) {
        try {
            return orders.get(id);
        }catch(Exception e){
            return null;
        }
    }
}
