package com.mypackage.testrenue;

import static java.lang.String.format;

public final class Configuration {
    private int columnNumber;

    public int getColumnNumbers() {
        return columnNumber;
    }

    public void setColumnNumbers(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public String toString() {
        return format("Колонка для поиска: %s\n", columnNumber);
    }
}
