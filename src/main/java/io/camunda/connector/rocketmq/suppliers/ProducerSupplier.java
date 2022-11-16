/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.rocketmq.suppliers;

import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.RPCHook;

public class ProducerSupplier {
  private static final int DELIVERY_TIMEOUT_MS_RECOMMENDED_VALUE = 45000;

  public DefaultMQProducer createProducer(
      final String accessKey,
      final String secretKey,
      final String groupName,
      final String namesrvAddr) {
    final var producer = new DefaultMQProducer(groupName, getAclRPCHook(accessKey, secretKey));
    producer.setNamesrvAddr(namesrvAddr);
    producer.setSendMsgTimeout(DELIVERY_TIMEOUT_MS_RECOMMENDED_VALUE);
    return producer;
  }

  private static RPCHook getAclRPCHook(final String accessKey, final String secretKey) {
    if (accessKey.isEmpty() || secretKey.isEmpty()) {
      return null;
    }
    return new AclClientRPCHook(new SessionCredentials(accessKey, secretKey));
  }
}
