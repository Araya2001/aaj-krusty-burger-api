package aaj.krustyburgerapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @JsonProperty("order")
    private Order order;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("value_no_vat")
    private Double valueNoVat;

    @JsonProperty("value_vat")
    private Double valueVat;

    @Column(name = "value_vat")
    public Double getValueVat() {
        return valueVat;
    }

    public OrderItem setValueVat(Double valueVat) {
        this.valueVat = valueVat;
        return this;
    }

    @Column(name = "value_no_vat")
    public Double getValueNoVat() {
        return valueNoVat;
    }

    public OrderItem setValueNoVat(Double valueNoVat) {
        this.valueNoVat = valueNoVat;
        return this;
    }

    @Column(name = "quantity", nullable = false)
    public Double getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public OrderItem setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    public Order getOrder() {
        return order;
    }

    public OrderItem setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() != null && Objects.equals(getId(), orderItem.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "dateCreate = " + getDateCreate() + ", " +
                "dateUpdate = " + getDateUpdate() + ", " +
                "userCreate = " + getUserCreate() + ", " +
                "userUpdate = " + getUserUpdate() + ", " +
                "order = " + getOrder() + ", " +
                "name = " + getName() + ", " +
                "quantity = " + getQuantity() + ", " +
                "valueNoVat = " + getValueNoVat() + ", " +
                "valueVat = " + getValueVat() + ")";
    }
}