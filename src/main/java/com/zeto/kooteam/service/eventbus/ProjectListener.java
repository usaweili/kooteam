package com.zeto.kooteam.service.eventbus;

import com.google.common.eventbus.Subscribe;
import com.zeto.Zen;
import com.zeto.ZenConditionKit;
import com.zeto.ZenData;
import com.zeto.domain.ZenCondition;
import com.zeto.driver.ZenStorageEngine;
import com.zeto.kooteam.service.eventbus.model.ProjectModel;

public class ProjectListener {
    private static final String thingTable = "thing";
    private static final String executor = "patch/project";

    @Subscribe
    public void execute(ProjectModel model) {
        ZenStorageEngine storageEngine = Zen.getStorageEngine();
        ZenCondition unfinishCon = ZenConditionKit.And().eq("projectId", model.getProjectId()).eq("status", 0);
        long unfinish = storageEngine.count(thingTable, unfinishCon);

        ZenCondition finishedCon = ZenConditionKit.And().eq("projectId", model.getProjectId()).eq("status", 1);
        long finished = storageEngine.count(thingTable, finishedCon);
        ZenData params = ZenData.put("_id", model.getProjectId()).
                set("unfinish", unfinish + "").set("finished", finished + "");
        storageEngine.execute(executor, params, null);
    }
}
