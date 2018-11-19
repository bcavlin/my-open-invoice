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

/**
 * Created by bcavlin on 07/05/17.
 */

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * A Report object has a byte representation of the report output that can be
 * used to write to any output stream. This class is designed around the concept
 * of using ByteArrayOutputStreams to write PDFs to an output stream.
 */
@Getter
@RequiredArgsConstructor
public abstract class Report {

    @NonNull
    protected String name;

    @NonNull
    protected byte[] template;

    @NonNull
    protected Map<String, Object> parameters;

    protected ByteArrayOutputStream reportContent;

    @NonNull
    protected ReportRunner reportRunner;


    /**
     * This is the processing method for a Report. Once the report is ran it
     * populates an internal field with a ByteArrayOutputStream of the
     * report content generated during the run process.
     *
     * @return Returns itself with the report content output stream created.
     */
    public abstract Report runReport();

}
