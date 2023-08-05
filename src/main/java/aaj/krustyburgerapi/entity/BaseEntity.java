package aaj.krustyburgerapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {
  @JsonProperty("id")
  protected Long id;
  @JsonProperty("date_create")
  protected Instant dateCreate;
  @JsonProperty("date_update")
  protected Instant dateUpdate;
  @JsonProperty("user_create")
  private Long userCreate;
  @JsonProperty("user_update")
  private Long userUpdate;

  @Column(name = "user_update", nullable = false)
  public Long getUserUpdate() {
    return userUpdate;
  }

  public BaseEntity setUserUpdate(Long userUpdate) {
    this.userUpdate = userUpdate;
    return this;
  }

  @Column(name = "user_create", nullable = false)
  public Long getUserCreate() {
    return userCreate;
  }

  public BaseEntity setUserCreate(Long userCreate) {
    this.userCreate = userCreate;
    return this;
  }

  @Column(name = "date_update", nullable = false)
  @JdbcTypeCode(SqlTypes.TIMESTAMP)
  @UpdateTimestamp
  public Instant getDateUpdate() {
    return dateUpdate;
  }

  public BaseEntity setDateUpdate(Instant dateUpdate) {
    this.dateUpdate = dateUpdate;
    return this;
  }

  @Column(name = "date_create", nullable = false)
  @JdbcTypeCode(SqlTypes.TIMESTAMP)
  @CreationTimestamp
  public Instant getDateCreate() {
    return dateCreate;
  }

  public BaseEntity setDateCreate(Instant dateCreate) {
    this.dateCreate = dateCreate;
    return this;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  public Long getId() {
    return id;
  }

  public BaseEntity setId(Long id) {
    this.id = id;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    BaseEntity that = (BaseEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" +
        "id = " + id + ", " +
        "dateCreated = " + dateCreate + ", " +
        "dateUpdated = " + dateUpdate + ")";
  }
}
