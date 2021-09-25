package com.mypackage.testrenue;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;

public class Indicators {
    public void getSize (ArrayList<String> arrayList) {
        System.out.print("Количество найденных строк: " + arrayList.size() + ". ");
    }

    public void getTime (long start, long finish) {
        System.out.println("Время, затраченное на поиск: " + (finish - start) + " мс.");
    }

    public void getMemory () {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println("Использовано " + memoryMXBean.getHeapMemoryUsage().getUsed() / 1048576 +
                " МБ хипа для работы приложения.");
    }
}
