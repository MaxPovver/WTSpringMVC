package com.maxpovver.worktracker.utils;

import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by Maxim Chistov on 05.07.15.
 */
@Service
public class RussiaWorkdaysProvider implements IWorkDaysProvider {
    //TODO: load it from db
    //TODO: make class that writes correct dates into db
    private final int[] months = new int[]{0,15, 19, 21, 22, 18, 21, 23, 21, 22, 22, 20, 23};
    //TODO: KOSTYL HERE:
    @Override
    public int getWorkdays() {
        return months[Calendar.getInstance().get(Calendar.MONTH)];
    }
}
