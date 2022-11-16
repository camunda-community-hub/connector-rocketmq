/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.rocketmq.model;

import io.camunda.connector.api.annotation.Secret;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RocketMqRequest {

  @Valid @NotNull @Secret private RocketMqAuthentication authentication;
  @Valid @NotNull @Secret private RocketMqTopic topic;
  @Valid @NotNull private RocketMqMessage message;

  public RocketMqAuthentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(final RocketMqAuthentication authentication) {
    this.authentication = authentication;
  }

  public RocketMqTopic getTopic() {
    return topic;
  }

  public void setTopic(final RocketMqTopic topic) {
    this.topic = topic;
  }

  public void setMessage(RocketMqMessage message) {
    this.message = message;
  }

  public RocketMqMessage getMessage() {
    return message;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RocketMqRequest that = (RocketMqRequest) o;
    return authentication.equals(that.authentication)
        && topic.equals(that.topic)
        && message.equals(that.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(authentication, topic, message);
  }

  @Override
  public String toString() {
    return "RocketConnectorRequest{"
        + "authentication="
        + authentication
        + ", topic="
        + topic
        + ", message="
        + message
        + '}';
  }
}
