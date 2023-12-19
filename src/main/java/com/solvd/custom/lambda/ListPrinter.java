package com.solvd.custom.lambda;

import java.util.List;
import java.util.function.Consumer;

public class ListPrinter {
    public static <T> void printListElements(List<T> list) {
        Consumer<T> printElement = element -> System.out.println("Element: " + element);
        list.forEach(printElement);
    }
}