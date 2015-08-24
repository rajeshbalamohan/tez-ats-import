This is an extension of ATSImportTool in tez.  It tries to download all information pertaining to an application

1. Wait till the app is over & Fetch app details from YARN / Hive / Tez.
2. Given an appId, download all dags pertaining to it. E.g In HiveServer2, a session might have 100s of DAGs. This tool downloads all of them if needed.
3. Given an appId, download specific set of dags.
4. Ability to download from different timeline server and RM server.
5. Every dag downloaded would be saved in separate file. Each of these files can be analyzed separately.

These additionally downloaded related entities are stored in additionalInfo.json in the downloaded zip file.  These info can be exposed via getAdditionalInfo (DAGInfo) later.

YARN related information are also be retrieved and stored. (e.g http://atsmachine:8188/ws/v1/applicationhistory/apps/application_1437197396589_0782/appattempts)

Build & Install:
================
Run "mvn clean package"
Copy "tez-ats-import-0.8.0-SNAPSHOT.jar" to whichever machine you want to run it from. 


Usage:
======  
  yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar
  
  Options
    --appId <appId>                               AppId that needs to be downloaded
    --batchSize <batchSize>                       Optional. batch size for downloading data
    --dagId <dagId>                               DagIds that needs to be downloaded. Can specify multiple dagIds as --dagId dag1 --dagId dag2..
    --downloadDir <downloadDir>                   Download directory where data needs to be downloaded
    --help                                        print help
    --yarnRMStatusAddress <yarnRMStatusAddress>   Optional. YARN RM Status address (e.g http://rm:8188)
    --yarnTimelineAddress <yarnTimelineAddress>   Optional. ATS address (e.g http://clusterATSNode:8188)
  
  *IMPORTANT*
  With ATS V1.5, you might want to download all tasks in single fetch itself (to avoid download issues).
  In such cases, memory pressure would be more. To mitigate this, run command with 'YARN_CLIENT_OPTS="-Xmx4g $YARN_CLIENT_OPTS" '. 
  Also, specify "--batchSize 10000". Refer examples below.
  
  
Examples:
=========
  - Print usage
    yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar
  
  - Download all dags in the application
    yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar --appId application_1439860407967_0084 --downloadDir /tmp/test
  
  - Download all dags in the application with ATS V 1.5 enabled (In this case, we need to download all tasks at-once)
    YARN_CLIENT_OPTS="-Xmx4g $YARN_CLIENT_OPTS" yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar --appId application_1439860407967_0084 --downloadDir /tmp/test --batchSize 100000
  
  - Download only one dag in the application
    yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar --appId application_1439860407967_0091 --dagId dag_1439860407967_0091_2 --downloadDir /tmp/test
  
  - Download multiple dags in the application
    yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar --appId application_1439860407967_0091 --dagId dag_1439860407967_0091_1 --dagId dag_1439860407967_0091_2 --downloadDir /tmp/test
    
  - Download from different TimelineServer
    yarn jar tez-ats-import-0.8.0-SNAPSHOT.jar --appId application_1439220865134_0100 --downloadDir /tmp/test/ --yarnTimelineAddress http://diff_ATS_Server:8188 --yarnRMStatusAddress http://diff_RM_cluster_machine:8088


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

