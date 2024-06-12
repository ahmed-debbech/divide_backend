package com.debbech.divide.processor.data;

import com.debbech.divide.processor.models.Order;

public interface IOrderLiveDb {
    boolean set(String id, Order order);
    Order get(String id);
}
