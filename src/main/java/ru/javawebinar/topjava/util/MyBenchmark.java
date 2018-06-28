package ru.javawebinar.topjava.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @State(Scope.Thread)
    public static class MyState {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 500)
        );

    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
//    Throughput	Measures the number of operations per second, meaning the number of times per second your benchmark method could be executed.
//    Average Time	Measures the average time it takes for the benchmark method to execute (a single execution).
//    Sample Time	Measures how long time it takes for the benchmark method to execute, including max, min time etc.
//    Single Shot Time	Measures how long time a single benchmark method execution takes to run. This is good to test how it performs under a cold start (no JVM warm up).
//    All	Measures all of the above.
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testMethod(MyState state, Blackhole blackhole) {
        List<UserMealWithExceed> list = UserMealsUtil.getFilteredWithExceeded(state.mealList,
                LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        blackhole.consume(list);
    }
}
