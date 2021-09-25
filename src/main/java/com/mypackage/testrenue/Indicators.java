package com.mypackage.testrenue;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;

public class Indicators {
    private final List<String> arrayList;
    private final long start;
    private final long finish;

    public Indicators(List<String> arrayList, long start, long finish) {
        this.arrayList = arrayList;
        this.start = start;
        this.finish = finish;
    }

    public void getSize () {
        System.out.print("Количество найденных строк: " + arrayList.size() + ". ");
    }

    public void getTime () {
        System.out.println("Время, затраченное на поиск: " + (finish - start) + " мс.");
    }

    public void getMemory () {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println("Использовано " + memoryMXBean.getHeapMemoryUsage().getUsed() / 1048576 +
                " МБ хипа для работы приложения.");
    }
}
