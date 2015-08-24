package org.apache.tez.history;

import org.apache.hadoop.yarn.api.records.ApplicationId;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Utils {
  public static final String DAG = "dag";

  public static ApplicationId getAppIdFromDAGId(String dagId) {
    int appId = 0;
    String[] split = dagId.split("_");
    if (split.length != 4 || !dagId.startsWith(DAG + "_")) {
      throw new IllegalArgumentException("Invalid DAG Id format : " + dagId);
    }
    String rmId = split[1];
    try {
      appId = Integer.parseInt(split[2]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error while parsing App Id '"
          + split[2] + "' of DAG Id : " + dagId);
    }
    try {
      int id = Integer.parseInt(split[3]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Error while parsing Id '" + split[3]
          + "' of DAG Id : " + dagId);
    }
    return ApplicationId.newInstance(Long.parseLong(rmId), appId);
  }
}
