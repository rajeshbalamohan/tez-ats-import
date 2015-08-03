Tez ATS import tool, which can optionally download data from related entities (e.g Hive related contents would be downloaded to "additionalInfo" json).

These additionally downloaded related entities are stored in additionalInfo-*.json.  These info can be exposed via getAdditionalInfo (DAGInfo) later.

TODO: YARN related information can also be retrieved and stored. (e.g http://atsmachine:8188/ws/v1/applicationhistory/apps/application_1437197396589_0782/appattempts)
