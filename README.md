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
