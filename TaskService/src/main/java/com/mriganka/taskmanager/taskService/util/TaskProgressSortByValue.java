package com.mriganka.taskmanager.taskService.util;

import com.mriganka.taskmanager.taskService.model.TaskProgress;

import java.util.Comparator;

public class TaskProgressSortByValue implements Comparator<TaskProgress> {

    public enum SortOrder {ASCENDING, DESCENDING}

    private SortOrder sortOrder;

    public TaskProgressSortByValue(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(TaskProgress o1, TaskProgress o2) {
        Integer value1 = o1.getValue();
        Integer value2 = o2.getValue();
        int compare = value1.compareTo(value2);
        if (sortOrder == SortOrder.ASCENDING) {
            return compare;
        } else {
            return compare * (-1);
        }
    }
}
