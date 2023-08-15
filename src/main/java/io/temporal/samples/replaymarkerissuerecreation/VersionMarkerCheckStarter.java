/*
 *  Copyright (c) 2020 Temporal Technologies, Inc. All Rights Reserved
 *
 *  Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *  Modifications copyright (C) 2017 Uber Technologies, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"). You may not
 *  use this file except in compliance with the License. A copy of the License is
 *  located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 *  or in the "license" file accompanying this file. This file is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *  express or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package io.temporal.samples.replaymarkerissuerecreation;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class VersionMarkerCheckStarter {

  static final String TASK_QUEUE = "VersioningMarkerReplayCheck";

  @SuppressWarnings("CatchAndPrintStackTrace")
  public static void main(String[] args) {
    // gRPC stubs wrapper that talks to the local docker instance of temporal service.
    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    // client that can be used to start and signal workflows
    WorkflowClient client = WorkflowClient.newInstance(service);

    // worker factory that can be used to create workers for specific task queues
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Worker that listens on a task queue and hosts both workflow and activity implementations.
    Worker worker = factory.newWorker(TASK_QUEUE);

    // Workflows are stateful. So you need a type to create instances.
    worker.registerWorkflowImplementationTypes(VersioningMarkerTestWorkflowImpl.class);

    // Activities are stateless and thread safe. So a shared instance is used.
    MarkerRecordTestActivity markerRecordTestActivity = new MarkerRecordTestActivityImpl();
    worker.registerActivitiesImplementations(markerRecordTestActivity);

    // Start all workers created by this factory.
    factory.start();
    System.out.println("Worker started for task queue: " + TASK_QUEUE);

    // now we can start running instances of our saga - its state will be persisted
    WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
    VersioningMarkerTestWorkflow workflow =
        client.newWorkflowStub(VersioningMarkerTestWorkflow.class, options);

    WorkflowClient.start(workflow::execute);
    workflow.versionedSignal();
  }
}
