/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.reporting;

import java.util.Map;

/** Created by bcavlin on 07/05/17. */
public class BIRTReport extends Report {

  public BIRTReport(
      String name,
      Map<String, Object> reportParameters,
      byte[] template,
      ReportRunner reportRunner) {
    super(name, template, reportParameters, reportRunner);
  }

  @Override
  public Report runReport() {
    this.reportContent = reportRunner.runReport(this);
    return this;
  }
}
