package com.solvd.custom.interfaces;
import com.solvd.individuals.workers.Staff;
import com.solvd.custom.enums.DayOfWeek;

import java.time.LocalTime;
public interface PatientManagementInterface {
    void bookAppointment(Staff staff, DayOfWeek dayOfWeek, LocalTime localTime);
    void cancelAppointment(Staff staff);

}
