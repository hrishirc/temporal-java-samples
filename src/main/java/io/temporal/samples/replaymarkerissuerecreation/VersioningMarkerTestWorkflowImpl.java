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

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class VersioningMarkerTestWorkflowImpl implements VersioningMarkerTestWorkflow {

  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofHours(1))
          // disable retries for example to run faster
          .build();

  private final MarkerRecordTestActivity activities =
      Workflow.newActivityStub(MarkerRecordTestActivity.class, options);

  @Override
  public void execute() {
    int version =
        io.temporal.workflow.Workflow.getVersion(
            "testMarker", io.temporal.workflow.Workflow.DEFAULT_VERSION, 1);
    io.temporal.workflow.Workflow.await(() -> false);
  }

  @Override
  public void versionedSignal() {
    int version =
        io.temporal.workflow.Workflow.getVersion(
            "testMarker", io.temporal.workflow.Workflow.DEFAULT_VERSION, 1);
    if (version == 1) {
      activities.testAlternate();
    } else {
      activities.testMain();
    }

    System.out.println(version);
  }

  @Override
  public long dummy() {
    return 0;
  }
}
