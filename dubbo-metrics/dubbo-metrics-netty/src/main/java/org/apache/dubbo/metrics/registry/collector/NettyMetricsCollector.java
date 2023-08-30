/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.metrics.registry.collector;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.config.context.ConfigManager;
import org.apache.dubbo.metrics.collector.CombMetricsCollector;
import org.apache.dubbo.metrics.collector.MetricsCollector;
import org.apache.dubbo.metrics.data.ApplicationStatComposite;
import org.apache.dubbo.metrics.data.BaseStatComposite;
import org.apache.dubbo.metrics.data.RtStatComposite;
import org.apache.dubbo.metrics.data.ServiceStatComposite;
import org.apache.dubbo.metrics.model.MetricsCategory;
import org.apache.dubbo.metrics.model.sample.MetricSample;
import org.apache.dubbo.metrics.registry.NettyMetricsConstants;
import org.apache.dubbo.metrics.registry.event.NettyEvent;
import org.apache.dubbo.metrics.registry.event.NettySubDispatcher;
import org.apache.dubbo.rpc.model.ApplicationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Netty implementation of {@link MetricsCollector}
 */
@Activate
public class NettyMetricsCollector extends CombMetricsCollector<NettyEvent> {

    private Boolean collectEnabled = null;
    private final ApplicationModel applicationModel;

    public NettyMetricsCollector(ApplicationModel applicationModel) {
        super(new BaseStatComposite(applicationModel) {
            @Override
            protected void init(ApplicationStatComposite applicationStatComposite) {
                super.init(applicationStatComposite);
                applicationStatComposite.init(NettyMetricsConstants.APP_LEVEL_KEYS);
            }

            @Override
            protected void init(ServiceStatComposite serviceStatComposite) {
                super.init(serviceStatComposite);
            }

            @Override
            protected void init(RtStatComposite rtStatComposite) {
                super.init(rtStatComposite);
            }
        });
        super.setEventMulticaster(new NettySubDispatcher(this));
        this.applicationModel = applicationModel;
    }

    public void setCollectEnabled(Boolean collectEnabled) {
        if (collectEnabled != null) {
            this.collectEnabled = collectEnabled;
        }
    }

    @Override
    public boolean isCollectEnabled() {
        if (collectEnabled == null) {
            ConfigManager configManager = applicationModel.getApplicationConfigManager();
            configManager.getMetrics().ifPresent(metricsConfig -> setCollectEnabled(metricsConfig.getEnableNetty()));
        }
        return Optional.ofNullable(collectEnabled).orElse(true);
    }

    @Override
    public List<MetricSample> collect() {
        List<MetricSample> list = new ArrayList<>();
        if (!isCollectEnabled()) {
            return list;
        }
        list.addAll(super.export(MetricsCategory.NETTY));
        return list;
    }
}
