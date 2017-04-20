package org.basic.bean;

import org.basic.bean.Product;

/**
 * Created by aa444 on 2017/4/8.
 */
public class CartItem {
    private int cartItemID;
    private Product p;
    private String userID;
    private int count;
    private int size;

    public int getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(int cartItemID) {
        this.cartItemID = cartItemID;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
