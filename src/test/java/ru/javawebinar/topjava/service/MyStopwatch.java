package ru.javawebinar.topjava.service;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MyStopwatch extends Stopwatch {
    private static final Logger log = getLogger(MyStopwatch.class);

    private static Map<String, Long> times = new HashMap();

    private static void logInfo(Description description, String status, long nanos) {


        String testName = description.getMethodName();

        if (testName == null) {
            System.out.println("*\n*\n*\n*\n*\n*\n*\n****************************************************************");
            times.forEach((k, v) -> System.out.format("Test %s took %d ms\n", k, TimeUnit.NANOSECONDS.toMillis(v)));
            long sum = times.values().stream()
                    .mapToLong(Number::longValue)
                    .sum();
//            int sum = times.values().stream().reduce(0, Integer::sum);
            String logStting = String.format("All tests took %d ms\n", TimeUnit.NANOSECONDS.toMillis(sum));
            System.out.println(System.lineSeparator() + logStting);
            System.out.println("****************************************************************");
            log.info(logStting);
        } else {
            String logString = String.format(String.format("*** Test %s %s, spent %d ms ***",
                    testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
            times.put(testName, nanos);
            log.info(logString);
//            System.out.println("*************************************");
//            System.out.println(logString);
//            System.out.println("*************************************");
        }

    }

    @Override
    protected void succeeded(long nanos, Description description) {
        logInfo(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

//    @Override
//    protected void finished(long nanos, Description description) {
//        logInfo(description, "finished", nanos);
//    }
}

