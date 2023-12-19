package com.solvd.custom.lambda;
import com.solvd.custom.enums.Gender;
import com.solvd.individuals.workers.Staff;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class CustomLambdaFunctions {

    //filters a list of staff based on their gender
    public static List<Staff> filterStaffByGender(List<Staff> staffList, Gender gender) {
        Predicate<Staff> filterByGender = staff -> staff.getGender() == gender;
        return staffList.stream().filter(filterByGender).toList();
    }

    //maps a list of staff to their names
    public static List<String> mapStaffNames(List<Staff> staffList) {
        Function<Staff, String> mapToName = Staff::getName;
        return staffList.stream().map(mapToName).toList();
    }

    //calculates the average age of the staff.
    public static double calculateAverageAge(List<Staff> staffList) {
        ToDoubleFunction<Staff> toDoubleAge = staff -> staff.getAge();
        return staffList.stream().collect(Collectors.averagingDouble(toDoubleAge));
    }
}
