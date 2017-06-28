package com.sdj_jewellers.model;

/**
 * Created by Administrator on 6/22/2017.
 */

public class CartModel {
    public String cartId;
    public String cart_postId;
    public String cart_postName;
    public String cart_postExcerpt;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCart_postId() {
        return cart_postId;
    }

    public void setCart_postId(String cart_postId) {
        this.cart_postId = cart_postId;
    }

    public String getCart_postName() {
        return cart_postName;
    }

    public void setCart_postName(String cart_postName) {
        this.cart_postName = cart_postName;
    }

    public String getCart_postExcerpt() {
        return cart_postExcerpt;
    }

    public void setCart_postExcerpt(String cart_postExcerpt) {
        this.cart_postExcerpt = cart_postExcerpt;
    }

    public String getCart_imageUrl() {
        return cart_imageUrl;
    }

    public void setCart_imageUrl(String cart_imageUrl) {
        this.cart_imageUrl = cart_imageUrl;
    }

    public String getCart_categoryId() {
        return cart_categoryId;
    }

    public void setCart_categoryId(String cart_categoryId) {
        this.cart_categoryId = cart_categoryId;
    }

    public String getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public String cart_imageUrl;
    public String cart_categoryId;
    public String cart_quantity;
}
