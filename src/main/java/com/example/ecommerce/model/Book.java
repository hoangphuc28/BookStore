package com.example.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Trường này không được để trống!")
    @Column(name="title")
    private String title;
    @NotBlank(message = "Trường này không được để trống!")
    @Column(name="author")
    private String author;


    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 10000, message = "Giá sản phẩm không được bé hơn 10000")
    @Max(value = 999999999, message = "Giá sản phẩm không được quá 999999999")
    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String image;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @Min(value = 0, message = "Số lượng sản phẩm phải lớn hơn 0")
    @Max(value = 100, message = "Số lượng sản phẩm không được quá 100")
    @Column
    private int quantity;

    @Column(name="isPublish")
    private Boolean isPublish;

    @Column(name = "description", length = 250, nullable = true)
    private String description;
}
