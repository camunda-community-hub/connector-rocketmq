/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.rocketmq.model;

import io.camunda.connector.api.annotation.Secret;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;

public class RocketMqTopic {
  @NotEmpty @Secret private String namesrvAddr;
  @NotEmpty @Secret private String groupName;
  @NotEmpty @Secret private String topicName;

  public String getNamesrvAddr() {
    return namesrvAddr;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setNamesrvAddr(String namesrvAddr) {
    this.namesrvAddr = namesrvAddr;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getTopicName() {
    return topicName;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RocketMqTopic that = (RocketMqTopic) o;
    return namesrvAddr.equals(that.namesrvAddr)
        && groupName.equals(that.getGroupName())
        && topicName.equals(that.topicName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namesrvAddr, groupName, topicName);
  }

  @Override
  public String toString() {
    return "RocketMqTopic{"
        + "namesrvAddr='"
        + namesrvAddr
        + '\''
        + ", groupName='"
        + groupName
        + '\''
        + ", topicName='"
        + topicName
        + '\''
        + '}';
  }
}
