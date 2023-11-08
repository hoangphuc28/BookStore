package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.CartProduct;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartProduct> cartItems;
    public CartService() {
        cartItems = new ArrayList<>();
    }

    public void addProduct(Product book) {
        for (CartProduct item : cartItems) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        CartProduct newItem = new CartProduct();
        newItem.setBook(book);
        newItem.setQuantity(1);
        cartItems.add(newItem);
    }
    public void remove(Long id) {
        for(var item : cartItems) {
            if(item.getBook().getId()==id) {
                cartItems.remove(item);
                break;
            }
        }
    }
    public void update(Long id, int quantity) {
        for(int i = 0; i < this.cartItems.size(); i++) {
            if(this.cartItems.get(i).getBook().getId()==id) {
                this.cartItems.get(i).setQuantity(quantity);
                break;
            }
        }
    }

    public List<CartProduct> getCartItems() {
        return cartItems;
    }
    public void removeAll() {
          cartItems = new ArrayList<>();
    }
}
