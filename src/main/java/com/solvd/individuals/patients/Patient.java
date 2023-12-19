package com.solvd.individuals.patients;

import com.solvd.custom.enums.Status;
import com.solvd.custom.interfaces.PatientManagementInterface;
import com.solvd.custom.enums.Gender;
import com.solvd.individuals.Person;

import com.solvd.individuals.workers.Doctor;
import com.solvd.individuals.workers.Staff;
import com.solvd.custom.enums.DayOfWeek;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Represents a patient admitted to the hospital.
//Contains attributes such as the patient's name and age.
//Inherits from the hospital.Hospital class, so it also has the attributes of the hospital.Hospital class.

public final class Patient extends Person implements PatientManagementInterface {
    private final String patientId;
    private Status status;
    private Doctor doctor;
    private LocalTime time;
    private DayOfWeek dayOfWeek;

    List<Doctor> myDoctors = new ArrayList<>(); //new

    public Patient(String name, String surname, int age, Gender gender, String patientId) {
        super(name, surname, age, gender);
        this.patientId = patientId;
        this.status = Status.SICK;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void welcome() {
        System.out.println("Good morning!");
    }

    @Override
    public void goodbye() {
        System.out.println("Goodbye!");
    }

    @Override
    public String getDetails() {
        return "I am a Patient.";
    }

    @Override
    public void bookAppointment(Staff staff, DayOfWeek dayOfWeek, LocalTime localTime) {
        staff.admitPatient(this, dayOfWeek, localTime);
    }

    @Override
    public void cancelAppointment(Staff staff) {
        staff.dischargePatient(this);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", status=" + status +
                ", doctor=" + doctor +
                ", time=" + time +
                ", dayOfWeek=" + dayOfWeek +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId) && status == patient.status && Objects.equals(doctor, patient.doctor) && Objects.equals(time, patient.time) && dayOfWeek == patient.dayOfWeek;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, status, doctor, time, dayOfWeek);
    }
}
