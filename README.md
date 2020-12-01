# Task_Manager
A distributed task manager which used to substract a given integer a from integer b in order to reach goal 0 with random time interval between 10 to 30 

## Technology Stack
- Redis 
- Apache Kafka
- Apache Kafka Source Connector 
- PostgresSQL
- Java 8
- Spring Boot
- Docker
- Maven

## Docker Enviroment Setup

### 1. Create docker network
Please create a docker network by below command.
```sh
$ docker network create --driver bridge taskmanager-network
```

### 2. Postgres Database Setup

Create a postgresql volume for store data

```sh
$ docker create -v /var/lib/postgresql/data --name Task_PostgresData --network=taskmanager-network alpine
```

Create postgres db image

```sh
$ docker run -p 5433:5432 --name task_postgres -e POSTGRES_PASSWORD=admin -d --volumes-from Task_PostgresData --network=taskmanager-network postgres
```

Open pgAdmin and create new database called task_manager

Open SQL script and execute below scripts

```sh
CREATE TABLE TASK_STATUS (
    STATUS_ID serial PRIMARY KEY,
    STATUS_NAME varchar NOT NULL
);


INSERT INTO TASK_STATUS (STATUS_NAME) VALUES
('SUCCESS'),
('IN_PROGRESS'),
('ERROR');

CREATE TABLE TASK (
    TASK_ID serial PRIMARY KEY,
    TASK_GROUP_ID varchar NOT NULL,
    GOAL INT NOT NULL,
    STEP INT NOT NULL,
	INTERVAL INT NOT NULL,
	STATUS_ID INT NOT NULL,
	START_DATE TIMESTAMP,
	FOREIGN KEY (STATUS_ID) REFERENCES TASK_STATUS (STATUS_ID) ON DELETE CASCADE  ON UPDATE CASCADE
);

CREATE INDEX CONCURRENTLY TASK_GROUP_ID_INDEX ON TASK (TASK_GROUP_ID);

CREATE TABLE TASK_PROGRESS (
    TASK_PROGRESS_ID serial PRIMARY KEY,
    TASK_ID INT NOT NULL,
    VALUE INT NOT NULL,
    PROCESS_TIME TIMESTAMP,
	FOREIGN KEY (TASK_ID) REFERENCES TASK (TASK_ID) ON DELETE CASCADE  ON UPDATE CASCADE
);

```
Alter wal_level in order to create source event listener in Kafka

```sh
ALTER SYSTEM SET wal_level = logical;
```

Restart postgres instance.


### 3. Apache Kafka docker image

 - Go to [Docker Kafka](./Docker_Kafka)  
 - Run below command 
```sh 
$ docker-compose up -d 
```

### 4. Debezium source connector Setup

We need to install debezium source connector

```sh
$ docker run -it --rm --name connect --network taskmanager-network -p 8083:8083 -e GROUP_ID=1 -e CONFIG_STORAGE_TOPIC=my_connect_configs -e OFFSET_STORAGE_TOPIC=my_connect_offsets -e STATUS_STORAGE_TOPIC=my_connect_statuses -e BOOTSTRAP_SERVERS=kafka:9092 --link zookeeper:zookeeper --link kafka:kafka --link task_postgres:postgres debezium/connect:1.3

```

### 5. Configure PostGres Source Connector

``` sh
curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "task_manager-connector",  
  "config": {
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector", 
    "database.hostname": "postgres", 
    "database.port": "5432", 
    "database.user": "postgres", 
    "database.password": "admin", 
    "database.dbname" : "task_manager", 
    "database.server.name": "task_manager", 
    "slot.name" : "public_task_manager_slot",
    "plugin.name": "pgoutput"

  }
}'
```

### 6. Create task progress status kafka topic

```sh
$ bin/kafka-topics.sh --create --topic updated_task_progress --bootstrap-server localhost:9092

```
