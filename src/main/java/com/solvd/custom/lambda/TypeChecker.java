package com.solvd.custom.lambda;

import java.util.function.Predicate;

public class TypeChecker {
    public static <T> Predicate<T> checkType(Class<T> clazz) {
        return element -> element.getClass().equals(clazz);
    }
}