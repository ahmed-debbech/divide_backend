package com.debbech.divide.processor.data;

import com.debbech.divide.processor.models.Order;

import java.util.List;
import java.util.Map;

public interface IOrderLiveDb {
    boolean set(String id, Order order);
    Order get(String id);
    List<Map.Entry<String, Order>> getAll();
    void delete(String id);
}
