Tez ATS import tool, which can optionally download data from related entities (e.g Hive related contents would be downloaded to "additionalInfo" json).

These additionally downloaded related entities are stored in additionalInfo-*.json.  These info can be exposed via getAdditionalInfo (DAGInfo) later.

TODO: YARN related information can also be retrieved and stored. (e.g http://atsmachine:8188/ws/v1/applicationhistory/apps/application_1437197396589_0782/appattempts)


Run (example):
=============
HADOOP_CLASSPATH=/tmp/test/tez-ats-import/target/tez-0.8.0-SNAPSHOT.jar:TEZ_JARS/*:$TEZ_JARS/lib/*:$HADOOP_CLASSPATH hadoop jar /tmp/test/tez-ats-import/target/tez-0.8.0-SNAPSHOT.jar org.apache.tez.history.ATSImportTool --dagId dag_1437197396589_0783_1  --downloadDir=/tmp/test --yarnTimelineAddress http://atsmachine.com:8188

