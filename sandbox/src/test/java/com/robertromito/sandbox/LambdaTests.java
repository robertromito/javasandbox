package com.robertromito.sandbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntToLongFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

public class LambdaTests {

    @Test
    void capturedVariablesMustBeFinal() {
        final var i = 10;
        Function<Integer, Integer> sut = x -> {
            return x + i;
        };
        assertEquals(20, sut.apply(10));
    }

    @Test
    void useFunctionForSingleArgSingleReturn() {
        Function<Integer, Integer> sut = x -> {
            return x * 2;
        };
        assertEquals(8, sut.apply(4));
    }

    @Test
    void usePredicateToTestASingleValue() {
        Predicate<String> sut = x -> {
            return x.equals("Happy");
        };
        assertTrue(sut.test("Happy"));
        assertFalse(sut.negate().test("Happy"));
    }

    @Test
    void usePredicateWhenFilteringAList() {
        Predicate<Integer> sut = x -> {
            return x % 2 == 0;
        };
        var list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(5, list.stream().filter(sut).count());
    }

    @Test
    void consumerIsUsedForSideEffects() {
        var list = new ArrayList<String>();
        Consumer<List<String>> sut = x -> {
            x.add("a");
        };
        assertEquals(0, list.size());
        sut.accept(list);
        assertEquals("a", list.get(0));
    }

    @Test
    void useSupplierToProduceValuesGivenNoInput() {
        Supplier<Integer> sut = () -> {
            return 10;
        };

        assertEquals(10, sut.get());
    }

    @Test
    void usePrimitiveInterfacesToAvoidBoxing() {
        IntPredicate p = x -> { return x == 10;};
        assertTrue(p.test(10));

        IntToLongFunction f1  = x -> { return Long.valueOf(x);};
        assertEquals(10L, f1.applyAsLong(10));

        ToIntFunction<String> f2 = x -> { return Integer.parseInt(x);};
        assertEquals(10, f2.applyAsInt("10"));

        BooleanSupplier b = () -> { return Boolean.FALSE;};
        assertFalse(b.getAsBoolean());
    }
}
