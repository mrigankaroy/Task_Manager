package com.mriganka.taskmanager.TaskEngine.model;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatusEnum {

    SUCCESS("SUCCESS", 1),
    IN_PROGRESS("IN_PROGRESS", 2),
    ERROR("ERROR", 3);

    private static Map<Integer, TaskStatusEnum> mMap;

    private final String status;

    private final int statusId;

    TaskStatusEnum(String status, int statusId) {
        this.status = status;
        this.statusId = statusId;
    }

    public static TaskStatusEnum getById(int id) {
        if (mMap == null) {
            initializeMapping();
        }
        if (mMap.containsKey(id)) {
            return mMap.get(id);
        }
        return null;
    }

    private static void initializeMapping() {
        mMap = new HashMap<>();
        for (TaskStatusEnum s : TaskStatusEnum.values()) {
            mMap.put(s.statusId, s);
        }
    }

    public String getStatus() {
        return status;
    }

}
