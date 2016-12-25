package com.karlchu.medicalorder.core.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hieu on 6/30/2016.
 */
public class IDGenerator {
    private static AtomicInteger id = new AtomicInteger(0);

    public static int newId() {
        return id.incrementAndGet();
    }
}
