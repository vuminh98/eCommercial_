package com.example.comercial.model;

import com.example.comercial.model.product.Product;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Payment payment;
    @ManyToOne
    private Product product;
    @NotNull
    @Check(constraints = "quantity >= 0")
    private Integer quantity;
}
