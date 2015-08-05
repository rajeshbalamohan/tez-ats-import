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
    "hive": {
	...
	...
	otherinfo: {
STATUS: false,
QUERY: "{"queryText":"\nselect * from tmp2 left semi join tmp1 where c1 = id and c0 = q","queryPlan":{"STAGE DEPENDENCIES":{"Stage-1":{"ROOT STAGE":"TRUE"},"Stage-0":{"DEPENDENT STAGES":"Stage-1"}},"STAGE PLANS":{"Stage-1":{"Tez":{"Vertices:":{"Map 2":{"Execution mode:":"vectorized","Map Operator Tree:":[{"TableScan":{"alias:":"tmp1","Statistics:":"Num rows: 10 Data size: 2585 Basic stats: COMPLETE Column stats: NONE","filterExpr:":"(c1 is not null and c0 is not null) (type: boolean)","children":{"Filter Operator":{"Statistics:":"Num rows: 3 Data size: 775 Basic stats: COMPLETE Column stats: NONE","children":{"Reduce Output Operator":{"value expressions:":"d (type: string)","sort order:":"++","Statistics:":"Num rows: 3 Data size: 775 Basic stats: COMPLETE Column stats: NONE","Map-reduce partition columns:":"c1 (type: string), c0 (type: string)","key expressions:":"c1 (type: string), c0 (type: string)"}},"predicate:":"(c1 is not null and c0 is not null) (type: boolean)"}}}}]},"Map 1":{"Execution mode:":"vectorized","Map Operator Tree:":[{"TableScan":{"alias:":"tmp2","Statistics:":"Num rows: 5 Data size: 1295 Basic stats: COMPLETE Column stats: NONE","filterExpr:":"(id is not null and q is not null) (type: boolean)","children":{"Filter Operator":{"Statistics:":"Num rows: 2 Data size: 518 Basic stats: COMPLETE Column stats: NONE","children":{"Map Join Operator":{"HybridGraceHashJoin:":"true","Statistics:":"Num rows: 3 Data size: 852 Basic stats: COMPLETE Column stats: NONE","keys:":{"0":"id (type: string), q (type: string)","1":"c1 (type: string), c0 (type: string)"},"input vertices:":{"1":"Map 2"},"children":{"Filter Operator":{"Statistics:":"Num rows: 1 Data size: 284 Basic stats: COMPLETE Column stats: NONE","children":{"Select Operator":{"Statistics:":"Num rows: 1 Data size: 284 Basic stats: COMPLETE Column stats: NONE","children":{"File Output Operator":{"compressed:":"false","Statistics:":"Num rows: 1 Data size: 284 Basic stats: COMPLETE Column stats: NONE","table:":{"input format:":"org.apache.hadoop.mapred.TextInputFormat","output format:":"org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat","serde:":"org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe"}}},"outputColumnNames:":["_col0","_col1","_col2","_col3","_col4","_col5"],"expressions:":"_col0 (type: string), _col1 (type: string), _col2 (type: string), _col6 (type: string), _col7 (type: string), _col8 (type: string)"}},"predicate:":"((_col8 = _col0) and (_col6 = _col2)) (type: boolean)"}},"condition map:":[{"":"Left Semi Join 0 to 1"}],"outputColumnNames:":["_col0","_col1","_col2","_col6","_col7","_col8"]}},"predicate:":"(id is not null and q is not null) (type: boolean)"}}}}]}},"Edges:":{"Map 1":{"parent":"Map 2","type":"BROADCAST_EDGE"}},"DagName:":"gopal_20150803012837_b7583726-7d58-4945-a05b-abf0dbb8acb5:6"}},"Stage-0":{"Fetch Operator":{"Processor Tree:":{"ListSink":{}},"limit:":"-1"}}}}}",
TEZ: true,
MAPRED: false
}
...
...
	},
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
