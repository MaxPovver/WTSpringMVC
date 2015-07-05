package com.maxpovver.worktracker.utils;

/**
 * Created by Maxim Chistov on 05.07.15.
 */
public interface IWorkDaysProvider {
    /**
     * Returns days you have to work in current month.
     * Obviously should differ from country to country and
     * some exceptions I can't think of now
     * @return days you have to work in current month, zero by default
     */
    default int getWorkdays() { return 0; }
}
