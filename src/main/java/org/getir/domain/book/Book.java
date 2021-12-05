package org.getir.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.getir.domain.TimestampEntity;
import org.getir.domain.order.OrderItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints={
  @UniqueConstraint(columnNames = {"name", "author"})
})
public class Book extends TimestampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String author;
    private Integer stock;
    private BigDecimal price;

    @OneToMany(mappedBy = "productOrder", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderItem> orderItem;

}
