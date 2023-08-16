## Versioning Issue Check

Run the following command to start the sample:

```bash
 ./gradlew -q execute -PmainClass=io.temporal.samples.versionmarkercheck.VersionMarkerCheckStarter
```

Then rerun the same command to start the worker again.

Then the last thing would be to use the following command to verify non-determinism. 

```
tctl wf query -w '${first_workflow_id}' --qt 'dummy'
```
