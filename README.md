# KAFKA Basics

[http://kafka.apache.org/downloads](http://kafka.apache.org/downloads)

## Run

### Default Config

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
...
...
bin/kafka-server-start.sh config/server.properties
```

## Commands

### Create new topic

`kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic <topic-name>`

## List Topics

`kafka-topics.sh --list --bootstrap-server localhost:9092`

`kafka-topics.sh --describe --bootstrap-server localhost:9092`

## Produce

```bash
kafka-console-producer.sh --broker-list localhost:9092 --topic <topic-name>
message..
message..
```

## Consumer

* Only new messages

`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name>`

* All messages

`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <topic-name> --from-beginning`

## Change partitions number

`kafka-topics.sh --alter --zookeeper localhost:2181 --topic <topic-name> --partitions 3`

## Check Groups Consume

`kafka-consumer-groups.sh --all-groups  --bootstrap-server localhost:9092 --describe`

## Purge

`bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic <topic-name>`