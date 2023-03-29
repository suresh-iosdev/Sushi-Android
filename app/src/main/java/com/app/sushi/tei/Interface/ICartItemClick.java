package com.app.sushi.tei.Interface;

import android.view.View;

import com.app.sushi.tei.Model.Cart.Cart;


/**
 * Created by root on 9/4/18.
 */

public interface ICartItemClick {

    public void deleteCartItem(View view, int position);
    public void updateCartItem(View view, int position, int quantity);

    public void updateOverallCartItems(View view, int position, Cart cart);

    void  makeapicall(String productId, Cart cartProduct, String type, int position);






}
