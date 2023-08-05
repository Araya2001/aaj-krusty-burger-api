package aaj.krustyburgerapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "server")
public class Server extends BaseEntity {

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @Column(name = "status", nullable = false)
    public String getStatus() {
        return status;
    }

    public Server setStatus(String status) {
        this.status = status;
        return this;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public Server setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Server server = (Server) o;
        return getId() != null && Objects.equals(getId(), server.getId());
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
                "name = " + getName() + ", " +
                "status = " + getStatus() + ")";
    }
}