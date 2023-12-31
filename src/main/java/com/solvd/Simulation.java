package com.solvd;

import com.solvd.custom.lambda.CustomLambdaFunctions;
import com.solvd.hospital.Hospital;
import com.solvd.custom.enums.Gender;
import com.solvd.individuals.patients.Patient;
import com.solvd.individuals.workers.Doctor;
import com.solvd.individuals.workers.Staff;
import com.solvd.custom.enums.DayOfWeek;
import com.solvd.individuals.workers.WorkingHours;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//1. Use at least 5 lambda functions from the java.util.function package.
//2. Create 3 custom Lambda functions with generics.
//3. Create 5 complex Enums(with fields, methods, blocks)

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    public static void sampleSimulation(Hospital hospital, Patient patient,
                                        DayOfWeek dayOfWeek, LocalTime localTime){

        Staff staff = hospital.callStaff();

        if (staff!=null){
            System.out.println("task-8");
            logger.info("Staff helping today: " + staff.getName());
            staff.welcome();
            patient.bookAppointment(staff, dayOfWeek, localTime); //adds patient to random doctor
            patient.cancelAppointment(staff); //cancels previous appointment
            patient.bookAppointment(staff, DayOfWeek.MONDAY, LocalTime.of(15,0)); //appoints patient again
            Doctor doctor = patient.getDoctor();
            doctor.checkAvailability();
            doctor.heal(patient);
            staff.dischargePatient(patient);
        }

    }

    public static void main(String[] args) {
        //new hospital
        Hospital hospital = new Hospital(
                "Medicover",
                "Warsaw",
                "Aleje Jerozolimskie 123"
        );

        //new patient
        Patient patient1 = new Patient(
                "Bob",
                "Bobinski",
                25,
                Gender.MALE,
                "P-0001"
        );

        //new staff
        Doctor doctor1 = new Doctor(
                "John",
                "Oetker",
                45,
                Gender.MALE,
                "DR-0001",
                hospital

        );

        Staff staff1 = new Staff(
                "Peter",
                "Doolittle",
                42,
                Gender.MALE,
                "ST-0002",
                hospital
        );

        Staff staff2 = new Staff(
                "Leila",
                "Doobigger",
                42,
                Gender.MALE,
                "ST-0005",
                hospital
        );

        Doctor doctor2 = new Doctor(
                "Emma",
                "Parker",
                45,
                Gender.FEMALE,
                "DR-0002",
                hospital
        );

        Doctor doctor3 = new Doctor(
                "Alex",
                "McDonald",
                45,
                Gender.FEMALE,
                "DR-0002",
                hospital
        );

        System.out.println("\n---------- Hiring new staff -----------");
        hospital.recruitStaff(doctor1); //adds doctor to staff
        hospital.recruitStaff(doctor2); //adds doctor to staff
        hospital.recruitStaff(staff1); //adds worker to staff
        hospital.recruitStaff(staff2); //adds worker to staff
        hospital.recruitStaff(doctor3); //adds doctor to staff

        //sample working hours
        WorkingHours wh1 = new WorkingHours(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh2 = new WorkingHours(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh3 = new WorkingHours(DayOfWeek.THURSDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh4 = new WorkingHours(DayOfWeek.FRIDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh5 = new WorkingHours(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
        WorkingHours wh6 = new WorkingHours(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(22, 0));
        WorkingHours wh7 = new WorkingHours(DayOfWeek.MONDAY, LocalTime.of(15, 0), LocalTime.of(23, 0));
        WorkingHours wh8 = new WorkingHours(DayOfWeek.WEDNESDAY, LocalTime.of(6, 0), LocalTime.of(14, 0));
        WorkingHours wh9 = new WorkingHours(DayOfWeek.SATURDAY, LocalTime.of(6, 0), LocalTime.of(14, 0));


        doctor1.addWorkingHours(wh7, wh3, wh4, wh5);
        doctor2.addWorkingHours(wh1, wh2, wh5);
        doctor3.addWorkingHours(wh1, wh4, wh6, wh8, wh9);

        System.out.println("\n------ Doctors' schedules: -------");
        doctor1.checkAvailability();
        doctor2.checkAvailability();
        doctor3.checkAvailability();


        System.out.println("\n----------- Simulation: A patient named Bob wants to get an appointment with a doctor" +
                " for Tuesday at 16:00, but then changes to Monday 15:00. -----------");
        sampleSimulation(hospital, patient1, DayOfWeek.TUESDAY, LocalTime.of(16, 0));

        System.out.println("\n-------- Dr. Emma retires -------");
        hospital.fireStaff(doctor2);

        System.out.println("\n-------- Task 8 -------");

        hospital.lambdaFromFunctionalInterfaces(doctor1, doctor2, doctor3);
        hospital.callCustomLambda();

        System.out.println("\n-------- Task 9 -------");
        System.out.println("STREAMING");
        hospital.streamOperationsExample();
        System.out.println("REFLECTION");
        try {
            // Accessing information about fields
            Field[] fields = Doctor.class.getDeclaredFields();
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                System.out.println("Field: " + field.getName());
                System.out.println("Type: " + field.getType().getSimpleName());
                System.out.println("Modifiers: " + Modifier.toString(modifiers));
            }

            // Creating object using reflection - Constructor and Method invocation
            Class<?> doctorClass = Doctor.class;
            Constructor<?> doctorConstructor = doctorClass.getDeclaredConstructor(String.class, String.class, int.class, Gender.class, String.class, Hospital.class);
            doctorConstructor.setAccessible(true); // Make it accessible if it's private
            Doctor newDoctor = (Doctor) doctorConstructor.newInstance("Jane", "Smith", 35, Gender.FEMALE, "DR-0003", hospital);

            Method healMethod = doctorClass.getDeclaredMethod("heal", Patient.class);
            healMethod.setAccessible(true); // Make it accessible if it's private
            Patient newPatient = new Patient("Alice", "Johnson", 30, Gender.FEMALE, "P-0002");
            healMethod.invoke(newDoctor, newPatient);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}