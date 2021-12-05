package org.getir.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.getir.domain.TimestampEntity;
import org.getir.domain.book.Book;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ProductOrder productOrder;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Book book;

    private BigDecimal totalPrice;

    private int productCount;

}
