package com.delivery2go.order;

import com.delivery2go.base.models.Order;

/**
* Created by ansel on 17/08/2016.
*/
public interface OnOrderChangeListener {
    void onOrderChanged();

    void onOrderStateChange(int state);
}
