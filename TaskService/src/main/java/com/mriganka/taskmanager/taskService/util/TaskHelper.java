package com.mriganka.taskmanager.taskService.util;

import java.util.Random;
import java.util.UUID;

public class TaskHelper {

    private static final int max = 30;
    private static final int min = 10;

    public static String randomGroupIdGenerator() {
        return UUID.randomUUID().toString();
    }

    public static int randomInterval() {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
