package com.example.ecommerce.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {
    private Product book;
    private int quantity;
}
