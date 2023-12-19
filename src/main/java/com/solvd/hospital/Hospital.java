package com.solvd.hospital;

import com.solvd.custom.enums.Gender;
import com.solvd.custom.interfaces.HospitalInterface;
import com.solvd.custom.lambda.CustomLambdaFunctions;
import com.solvd.individuals.patients.Patient;
import com.solvd.individuals.workers.Doctor;
import com.solvd.individuals.workers.Staff;
import com.solvd.custom.enums.DayOfWeek;
import com.solvd.individuals.workers.WorkingHours;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//Represents the basic structure of a hospital, containing attributes like name and location.
//Provides a general view of a hospital.
public final class Hospital extends Building implements HospitalInterface {
    private final List<Staff> staffList = new LinkedList<>(); //new
    private final Map<Doctor, Patient> doctorPatient = new HashMap<>(); //new
    private static final Logger logger = LogManager.getLogger(Hospital.class);

    static {
        System.out.println("Welcome to our Hospital!\n");
    }

    public Hospital(String name, String city, String address) {
        super(name, city, address);
        logger.info("Hospital: " + this);
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    @Override
    public Staff callStaff() {
        List<Staff> nonDoctors = staffList.stream()
                .filter(staff -> !(staff instanceof Doctor))
                .toList();

        // Select a random doctor from the filtered list
        if (!nonDoctors.isEmpty()) {
            Random random = new Random();
            return nonDoctors.get(random.nextInt(nonDoctors.size()));
        }
        return null;
    }

    @Override
    public void recruitStaff(Staff staff){
        this.staffList.add(staff);
        if(staff instanceof Doctor){
            logger.info(staff.getName() + " had been hired as a Doctor!");
        } else {
            logger.info(staff.getName() + " had been hired as a Staff!");
        }
    }

    @Override
    public void fireStaff(Staff staff) {
        this.staffList.remove(staff);
        logger.info("Farewell " + staff.getName() +". It was a pleasure working with you!");
    }


    public Doctor chooseDoctor(DayOfWeek dayOfWeek, LocalTime localTime){
        List<Doctor> doctorsList = staffList.stream()
                .filter(staff -> staff instanceof Doctor)
                .map(staff -> (Doctor) staff)
                .filter(doctor -> doctor.isSlotAvailable(dayOfWeek, localTime))
                .toList();

        // Select a random doctor from the filtered list
        if (!doctorsList.isEmpty()) {
            Random random = new Random();
            return doctorsList.get(random.nextInt(doctorsList.size()));
        }
         return null;
    }

    public void lambdaFromFunctionalInterfaces(Doctor doctor1, Doctor doctor2, Doctor doctor3){
        //Predicate Lambda: Checking for available slots in the doctor's schedule.
        Predicate<WorkingHours> isSlotAvailable = wh ->
                wh.getDayOfWeek() == DayOfWeek.TUESDAY && wh.getStartTime().equals(LocalTime.of(16, 0));

        boolean isAvailable = doctor2.getWorkingHoursList().stream().anyMatch(isSlotAvailable);
        System.out.println("Is slot available on Tuesday at 16:00 for Doctor 2? " + isAvailable);

        //Consumer Lambda: Displaying doctor availability.
        Consumer<WorkingHours> displayAvailability = wh ->
                System.out.println(doctor1.getName() + " is available on " + wh.getDayOfWeek() +
                        " from " + wh.getStartTime() + " to " + wh.getEndTime());

        System.out.println("Doctor 1's availability:");
        doctor1.getWorkingHoursList().forEach(displayAvailability);

        //Function Lambda: Mapping doctor details to their names.
        Function<Staff, String> mapToName = Staff::getName;

        List<String> doctorNames = Arrays.asList(doctor1, doctor2, doctor3).stream()
                .map(mapToName)
                .toList();

        System.out.println("Doctor names: " + doctorNames);

        //Supplier Lambda: Creating a new instance of WorkingHours.
        Supplier<WorkingHours> createWorkingHours = () ->
                new WorkingHours(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(20, 0));

        WorkingHours newWorkingHours = createWorkingHours.get();
        System.out.println("Newly created working hours: " + newWorkingHours);

        //Comparator Lambda: Sorting the doctor's available slots.
        doctor3.getWorkingHoursList().sort(Comparator.comparing(WorkingHours::getDayOfWeek));

        System.out.println("Sorted Doctor 3's available slots:");
        doctor3.getWorkingHoursList().forEach(System.out::println);
    }

    public void callCustomLambda(){
        // Using the filterStaffByGender method to filter staff by gender
        List<Staff> filteredStaffByGender = CustomLambdaFunctions.filterStaffByGender(
                staffList, Gender.MALE);
        System.out.println("Filtered Staff by Gender (Male): " + filteredStaffByGender);

        // Using the mapStaffNames method to map staff to their names
        List<String> staffNames = CustomLambdaFunctions.mapStaffNames(staffList);
        System.out.println("Staff Names: " + staffNames);

        // Using the calculateAverageAge method to calculate the average age of staff
        double averageAge = CustomLambdaFunctions.calculateAverageAge(staffList);
        System.out.println("Average Age of Staff: " + averageAge);
    }

    public void streamOperationsExample() {
        //Filtering by gender (non-terminal operation)
        List<Staff> maleStaff = staffList.stream()
                .filter(staff -> staff.getGender() == Gender.MALE)
                .collect(Collectors.toList());

        //Mapping names to uppercase (non-terminal operation)
        List<String> staffNamesUpperCase = staffList.stream()
                .map(staff -> staff.getName().toUpperCase())
                .collect(Collectors.toList());

        //Counting staff members by age (terminal operation)
        long staffUnderThirtyCount = staffList.stream()
                .filter(staff -> staff.getAge() < 30)
                .count();

        //Sorting staff by name (non-terminal and terminal operation)
        List<Staff> sortedStaffByName = staffList.stream()
                .sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());

        //Grouping staff by gender (terminal operation)
        // Assuming you have a Map<Gender, List<Staff>> in your class for the result
        Map<Gender, List<Staff>> staffByGender = staffList.stream()
                .collect(Collectors.groupingBy(Staff::getGender));

        //Find the youngest staff member (terminal operation)
        Optional<Staff> youngestStaff = staffList.stream()
                .min(Comparator.comparingInt(Staff::getAge));

        System.out.println("Male Staff: " + maleStaff);
        System.out.println("Staff Names in Uppercase: " + staffNamesUpperCase);
        System.out.println("Number of Staff Members Under 30: " + staffUnderThirtyCount);
        System.out.println("Sorted Staff by Name: " + sortedStaffByName);
        System.out.println("Staff grouped by Gender: " + staffByGender);
        System.out.println("Youngest Staff Member: " + youngestStaff.orElse(null));
    }



    @Override
    public String toString() {
        return super.toString();
    }
}

