package com.solvd.custom.lambda;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElementTransformer {
    public static <T, R> List<R> transformList(List<T> list, Function<T, R> transformer) {
        return list.stream().map(transformer).collect(Collectors.toList());
    }
}
