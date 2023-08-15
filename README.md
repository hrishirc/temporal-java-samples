# Temporal Java SDK Samples

This repository contains sample Workflow applications that demonstrate various capabilities of Temporal using the [Java SDK](https://github.com/temporalio/sdk-java).

- Temporal Server repo: https://github.com/temporalio/temporal
- Temporal Java SDK repo: https://github.com/temporalio/sdk-java
- Java SDK docs: https://docs.temporal.io/docs/java/introduction/

## Table of Contents

- [How to use](#how-to-use)
- [Temporal Web UI](temporal-web-ui)
- [Samples directory](#samples-directory)
    - [Hello samples](#Hello-samples)
    - [Scenario-based samples](#scenario-based-samples)
    - [API demonstrations](#api-demonstrations)
    - [SDK metrics](#sdk-metrics)
    - [Tracing support](#tracing-support)
- [IDE Integration](#IDE-Integration)

## How to use

1. Clone this repository:

       git clone https://github.com/temporalio/samples-java
       cd samples-java

2. Build the examples and run tests:

       ./gradlew build

3. You need a locally running Temporal Server instance to run the samples. We recommend a locally running
   version of the Temporal Server managed via [Docker Compose](https://docs.docker.com/compose/gettingstarted/):

       git clone https://github.com/temporalio/docker-compose.git
       cd  docker-compose
       docker-compose up

Note that for the "listworkflows" example you need to have the Elasticsearch feature
enabled on the Temporal Server side. To do this you can run locally with:

       git clone https://github.com/temporalio/docker-compose.git
       cd  docker-compose
       docker-compose -f docker-compose-cas-es.yml up

Alternatively you could install the Temporal Server on Kubernetes / Minicube using the [Temporal Helm charts](https://github.com/temporalio/helm-charts).
Note that in this case you should use the [Temporal CLI (tctl)](https://docs.temporal.io/docs/system-tools/tctl/) tool to create a namespace called "default":

       tctl --ns default n re