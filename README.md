# Camunda RocketMQ Connector

Find the user documentation in our [Camunda Platform 8 Docs](https://docs.camunda.io/docs/components/integration-framework/connectors/out-of-the-box-connectors/rocketmq/).

## Build

```bash
mvn clean package
```

## API

### Input

```json
{
  "authentication":{
    "accessKey":"secrets.ACCESS_KEY",
    "secretKey":"secrets.SECRET_KEY"
  },
  "topic":{
    "namesrvAddr": "0.0.0.0:9876",
    "groupName": "producerGroup",
    "topicName": "test"
  },
  "message": {
    "body":{
      "data":"ok"
    },
    "tag": "*",
    "key": "key"
  }
}
```

### Output

```json
{
  "result": {
    "messageId": "c158a652-c3e3-5511-a565-fd01a05c0c45"
  }
}
```

## Test locally

Run unit tests

```bash
mvn clean verify
```

### Test as local Job Worker

Use
the [Camunda Job Worker Connector Run-Time](https://github.com/camunda/connector-framework/tree/main/runtime-job-worker)
to run your function as a local Job Worker.

## Element Template

The element templates can be found in
the [element-templates/rocketmq-connector.json](element-templates/rocketmq-connector.json) file.

## Build a release

Trigger the [release action](./.github/workflows/RELEASE.yml) manually with the version `x.y.z` you want to release and the next SNAPSHOT version.
