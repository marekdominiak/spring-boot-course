package pl.dominussoft.springbootcourse.app.domain;


import lombok.*;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import pl.dominussoft.springbootcourse.app.domain.base.AggregateRoot;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table("CUSTOMER_ORDER")
@ToString
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Order extends AggregateRoot {

    UUID userId;

    LocalDate orderedAt;

    @MappedCollection(idColumn = "ORDER_ID")
    Set<OrderLine> lines = new HashSet<>();

    public Order(@NonNull UUID userId, @NonNull LocalDate orderedAt) {
        this.userId = userId;
        this.orderedAt = orderedAt;
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }
}
