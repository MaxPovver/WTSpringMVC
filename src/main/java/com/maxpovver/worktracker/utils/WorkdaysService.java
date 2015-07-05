package com.maxpovver.worktracker.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Maxim Chistov on 05.07.15.
 */
@Service
public class WorkdaysService {
    private static IWorkDaysProvider wdProvider;
    public static IWorkDaysProvider get() {
        return wdProvider;
    }

    @Autowired
    private void setWorkdaysProvider(IWorkDaysProvider p) {
        wdProvider = p;
    }
}
