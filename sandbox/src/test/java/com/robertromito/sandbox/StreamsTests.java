package com.robertromito.sandbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamsTests {

    @Test
    void youCanCreateAnEmptyStream() {
        assertEquals(0, Stream.empty().count());
    }

    @Test
    void youCanCreateAStreamFromAnyCollection() {
        assertEquals(3, List.of(1, 2, 3).stream().count());
        assertEquals(6, Set.of(1, 2, 3, 4, 5, 6).stream().count());
    }

    @Test
    void youCanUseStreamOfToo() {
        assertEquals(4, Stream.of(1, 2, 3, 4).count());
    }

    @Test
    void createStreamsWithStreamBuilder() {
        var sut = Stream.<String>builder().add("a").add("b").add("c").build();

        assertEquals(3, sut.count());
    }

    @Test
    void useStreamGenerateWithASupplier() {
        assertEquals(5, Stream.generate(() -> "test").limit(5).count());
    }

    @Test
    void useStreamIterateToProduceCalculatedValues() {
        var sut = Stream.iterate(10, n -> n * 2).limit(5).toArray();
        assertEquals(5, sut.length);
        assertEquals(10, sut[0]);
        assertEquals(20, sut[1]);
        assertEquals(40, sut[2]);
        assertEquals(80, sut[3]);
        assertEquals(160, sut[4]);
    }

    @Test
    void youCanCreatePrimitiveStreams() {
        assertEquals(4, IntStream.range(1, 5).count());
        assertEquals(5, LongStream.rangeClosed(1, 5).count());
    }

    @Test
    void youCanCreateRandomNumericStreams() {
        var random = new Random();
        assertEquals(3, random.doubles(3).count());
        assertEquals(10, random.ints(10).count());
    }

    @Test
    void streamsCannotBeReusedAfterTerminalOperation() {
        var sut = Stream.of("a", "b", "c").filter(x -> x.equals("b"));

        assertEquals("b", sut.findFirst().get());

        assertThrows(IllegalStateException.class, () -> sut.findAny().get());
    }

    @Test
    void streamPipelinesConsistOfThreeParts() {

        // Part 1: Source stream
        var source = IntStream.rangeClosed(1, 10);

        // Part 2: Intermediate/modified source stream
        var modified = source.filter(i -> i % 2 == 0).map(i -> i * 2);

        // Part 3: Terminal operation
        var finalValue = modified.sum();

        assertEquals(60, finalValue);
    }

    @Test
    void reduceStreamSizeBeforeElementOperations() {
        var counter = new ArrayList<Integer>();

        Consumer<List<Integer>> fx = x -> {
            x.add(1);
        };

        Stream.of(1, 2, 3, 4, 5, 6).map(e -> {
            fx.accept(counter);
            return e;
        }).skip(2).count();
        assertEquals(6, counter.size());

        counter.clear();

        Stream.of(1, 2, 3, 4, 5, 6).skip(3).map(e -> {
            fx.accept(counter);
            return e;
        }).count();
        assertEquals(3, counter.size());
    }

    @Test
    void useReduceForACustomTerminalOperation() {
        assertEquals(21, IntStream.rangeClosed(1, 6).reduce((x,y) -> x + y).getAsInt());
        assertEquals(31, IntStream.rangeClosed(1, 6).reduce(10, (x,y) -> x + y));


    }

}
