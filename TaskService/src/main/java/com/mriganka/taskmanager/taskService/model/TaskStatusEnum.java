package com.mriganka.taskmanager.taskService.model;

public enum TaskStatusEnum {

    SUCCESS("SUCCESS"),
    IN_PROGRESS("IN_PROGRESS"),
    ERROR("ERROR");

    private final String status;

    TaskStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
