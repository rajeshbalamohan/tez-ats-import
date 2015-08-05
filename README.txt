Tez ATS import tool, which can optionally download data from related entities (e.g Hive related contents would be downloaded to "additionalInfo" json).

These additionally downloaded related entities are stored in additionalInfo.json in the downloaded zip file.  These info can be exposed via getAdditionalInfo (DAGInfo) later.

YARN related information are also be retrieved and stored. (e.g http://atsmachine:8188/ws/v1/applicationhistory/apps/application_1437197396589_0782/appattempts)

Run (example):
=============
DO NOT FORGET TO SPECIFY "org.apache.tez.history.ATSImportTool_V2"

HADOOP_CLASSPATH=TEZ_JARS/*:$TEZ_JARS/lib/*:$HADOOP_CLASSPATH hadoop jar /tmp/test/tez-ats-import/target/tez-0.8.0-SNAPSHOT.jar org.apache.tez.history.ATSImportTool_V2 --dagId dag_1437197396589_0783_1  --downloadDir=/tmp/test --yarnTimelineAddress http://atsmachine.com:8188

In case hadoop config already has timeline address, ignore specifying yarnTimelineAddress from the above example.

Tez related jars are needed as it ATSImportTool relies on TezDAGID.


Example additionalInfo.json:
===========================
//In case hive related information is present, it would be populated in "hive" tag (e.g explain plan etc). In case both are not available, errors are ignored and empty additionalInfo file would be created.

{"additionalInfo": {
    "hive": {},
    "yarn": {
        "app": [{
            "appId": "application_1437197396589_0864",
            "currentAppAttemptId": "appattempt_1437197396589_0864_000001",
            "user": "gopal",
            "name": "HIVE-c6500b16-670f-402c-b137-813924da75e3",
            "queue": "default",
            "type": "TEZ",
            "host": "n111",
            "rpcPort": 37172,
            "appState": "FINISHED",
            "runningContainers": 0,
            "progress": 100,
            "diagnosticsInfo": "Session stats:submittedDAGs=6, successfulDAGs=6, failedDAGs=0, killedDAGs=0\n",
            "originalTrackingUrl": "N\/A",
            "trackingUrl": "http:\/\/n106:8088\/proxy\/application_1437197396589_0864\/",
            "finalAppStatus": "SUCCEEDED",
            "submittedTime": 1438739334983,
            "startedTime": 1438739334983,
            "finishedTime": 1438740093396,
            "elapsedTime": 758413
        }],
        "appAttempt": [{
            "appAttemptId": "appattempt_1437197396589_0864_000001",
            "host": "n111",
            "rpcPort": 37172,
            "trackingUrl": "http:\/\/n106:8088\/proxy\/application_1437197396589_0864\/",
            "originalTrackingUrl": "N\/A",
            "diagnosticsInfo": "Session stats:submittedDAGs=6, successfulDAGs=6, failedDAGs=0, killedDAGs=0\n",
            "appAttemptState": "FINISHED",
            "amContainerId": "container_e07_1437197396589_0864_01_000001",
            "startedTime": 0,
            "finishedTime": 0
        }],
        "containers": [{"container": [
            {
                "containerId": "container_e07_1437197396589_0864_01_000067",
                "allocatedMB": 8192,
                "allocatedVCores": 1,
                "assignedNodeId": "n123:45454",
                "priority": 2,
                "startedTime": 1438739840783,
                "finishedTime": 1438739841194,
                "elapsedTime": 411,
                "diagnosticsInfo": "Container released by application",
                "logUrl": "http:\/\/n106-10:8188\/applicationhistory\/logs\/n123-10:45454\/container_e07_1437197396589_0864_01_000067\/container_e07_1437197396589_0864_01_000067\/gopal",
                "containerExitStatus": -100,
                "containerState": "COMPLETE",
                "nodeHttpAddress": "http:\/\/n123:8042"
            },
....
...
