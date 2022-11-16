/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. Licensed under a proprietary license.
 * See the License.txt file for more information. You may not use this file
 * except in compliance with the proprietary license.
 */
package io.camunda.connector.rocketmq;

import com.google.gson.Gson;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.rocketmq.model.RocketMqRequest;
import io.camunda.connector.rocketmq.model.RocketMqResult;
import io.camunda.connector.rocketmq.suppliers.GsonSupplier;
import io.camunda.connector.rocketmq.suppliers.ProducerSupplier;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "ROCKETMQ",
    inputVariables = {"authentication", "topic", "message"},
    type = "io.camunda:rocket-mq:1")
public class RocketMqConnectorFunction implements OutboundConnectorFunction {
  private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqConnectorFunction.class);

  private final ProducerSupplier producerSupplier;
  private final Gson gson;

  public RocketMqConnectorFunction() {
    producerSupplier = new ProducerSupplier();
    gson = GsonSupplier.gson();
  }

  @Override
  public Object execute(final OutboundConnectorContext context) {
    final var variables = context.getVariables();
    LOGGER.debug("Executing RocketMQ connector with variables : {}", variables);
    final var request = gson.fromJson(variables, RocketMqRequest.class);
    context.validate(request);
    context.replaceSecrets(request);
    return new RocketMqResult(sendMsgToRocket(request).getMsgId());
  }

  private SendResult sendMsgToRocket(RocketMqRequest request) {
    MQProducer producer = null;
    final var authentication = request.getAuthentication();
    final var topic = request.getTopic();
    final var msg = request.getMessage();
    try {
      producer =
          producerSupplier.createProducer(
              authentication.getAccessKey(),
              authentication.getSecretKey(),
              topic.getGroupName(),
              topic.getNamesrvAddr());
      producer.start();

      final var message =
          new Message(topic.getTopicName(), msg.getTag(), msg.getKey(), msg.getBodyAsByteArray());
      return producer.send(message);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (producer != null) {
        producer.shutdown();
      }
    }
  }
}
