package aaj.krustyburgerapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ResponseEntityWrapperDTO<T> {
  @JsonProperty("message")
  private String message;
  @JsonProperty("status")
  private String status;
  @JsonProperty("data")
  private T data;

  public String getMessage() {
    return message;
  }

  public ResponseEntityWrapperDTO<T> setMessage(String message) {
    this.message = message;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public ResponseEntityWrapperDTO<T> setStatus(String status) {
    this.status = status;
    return this;
  }

  public T getData() {
    return data;
  }

  public ResponseEntityWrapperDTO<T> setData(T data) {
    this.data = data;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseEntityWrapperDTO<?> that = (ResponseEntityWrapperDTO<?>) o;
    return Objects.equals(message, that.message) && Objects.equals(status, that.status) && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, status, data);
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("ResponseEntityWrapperDTO{");
    sb.append("message='").append(message).append('\'');
    sb.append(", status='").append(status).append('\'');
    sb.append(", data=").append(data);
    sb.append('}');
    return sb.toString();
  }
}
