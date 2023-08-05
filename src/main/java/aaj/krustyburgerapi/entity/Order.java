package aaj.krustyburgerapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "order")
public class Order extends BaseEntity {
    @JsonProperty("customer")
    private Customer customer;


    @JsonProperty("server")
    private Server server;


    @JsonProperty("cook")
    private Cook cook;

    @JsonProperty("status")
    private String status;

    @JsonProperty("charge_value_no_vat")
    private Double chargeValueNoVat;

    @JsonProperty("charge_value_vat")
    private Double chargeValueVat;

    @Column(name = "charge_value_vat")
    public Double getChargeValueVat() {
        return chargeValueVat;
    }

    public Order setChargeValueVat(Double chargeValueVat) {
        this.chargeValueVat = chargeValueVat;
        return this;
    }

    @Column(name = "charge_value_no_vat")
    public Double getChargeValueNoVat() {
        return chargeValueNoVat;
    }

    public Order setChargeValueNoVat(Double chargeValueNoVat) {
        this.chargeValueNoVat = chargeValueNoVat;
        return this;
    }

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cook_id")
    public Cook getCook() {
        return cook;
    }

    public Order setCook(Cook cook) {
        this.cook = cook;
        return this;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id")
    public Server getServer() {
        return server;
    }

    public Order setServer(Server server) {
        this.server = server;
        return this;
    }

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
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
                "customer = " + getCustomer() + ", " +
                "server = " + getServer() + ", " +
                "cook = " + getCook() + ", " +
                "status = " + getStatus() + ", " +
                "chargeValueNoVat = " + getChargeValueNoVat() + ", " +
                "chargeValueVat = " + getChargeValueVat() + ")";
    }
}