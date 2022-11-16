/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.rocketmq.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import io.camunda.connector.rocketmq.suppliers.GsonSupplier;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocketMqMessage {
  private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqMessage.class);
  @NotNull private Object body;
  private String tag;
  private String key;

  public byte[] getBodyAsByteArray() {
    if (body instanceof String) {
      try {
        JsonElement jsonElement =
            GsonSupplier.gson()
                .fromJson(StringEscapeUtils.unescapeJson(body.toString()), JsonElement.class);
        if (jsonElement.isJsonPrimitive()) {
          return ((String) body).getBytes();
        } else {
          body = jsonElement;
        }
      } catch (JsonSyntaxException e) {
        // this is plain text value, and not JSON. For example, "some input text".
        LOGGER.debug("Expected exception when parsing a plain text value : {}", body, e);
        return body.toString().getBytes();
      }
    }
    return Optional.of(body)
        .map(GsonSupplier.gson()::toJson)
        .map(StringEscapeUtils::unescapeJson)
        .map(String::getBytes)
        .orElseThrow(() -> new RuntimeException("Parse error to byte array"));
  }

  public Object getBody() {
    return body;
  }

  public void setbody(Object body) {
    this.body = body;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RocketMqMessage that = (RocketMqMessage) o;
    return body.equals(that.body) && tag.equals(that.tag) && key.equals(that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(body, tag);
  }

  @Override
  public String toString() {
    return "RocketMqMessage{" + ", body=" + body + ", tag=" + tag + ", key=" + key + '}';
  }
}
