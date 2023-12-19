package com.solvd.hospital;

import com.solvd.individuals.workers.Staff;

public interface HospitalInterface {
    Staff callStaff();

    void recruitStaff(Staff staff);

    void fireStaff(Staff staff);
}
