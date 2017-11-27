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

package com.bgh.myopeninvoice.db.test;

import com.bgh.myopeninvoice.db.DatabaseConfiguration;
import com.bgh.myopeninvoice.db.repository.ReportsRepository;
import com.bgh.myopeninvoice.db.model.ReportsEntity;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * Created by bcavlin on 20/06/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = DatabaseConfiguration.class)
public class RepositoriesTest {

    @Autowired
    private ReportsRepository reportsRepository;

    @Test
    public void testReportsRepository(){
        Assert.assertNotNull(reportsRepository);
        final ArrayList<ReportsEntity> reportsEntities = Lists.newArrayList(reportsRepository.findAll());
        Assert.assertNotNull(reportsEntities);
    }
}
