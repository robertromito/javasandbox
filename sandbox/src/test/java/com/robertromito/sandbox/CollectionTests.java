package com.robertromito.sandbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CollectionTests {

    @Test
    void iteratingOverAList() {
        var sut = List.of("a", "b", "c", "d", "e");

        for (String s : sut) {
            assertNotNull(s);
        }
    }

    @Test
    void arrayListIsAZeroBasedOrderedList() {
        var sut = new ArrayList<>(Arrays.asList("b", "a", "e", "c", "d"));

        var i = 0;
        for (String s : sut) {
            assertEquals(i, sut.indexOf(s));
            i++;
        }
    }

    @Test
    void setsDoNotAllowDuplicateElements() {
        var sut = new HashSet<>(List.of(1, 2, 3));
        assertEquals(3, sut.size());

        sut.add(1);
        assertEquals(3, sut.size(), "Set size should not increase because 1 already exists in the set");

        sut.add(10);
        assertEquals(4, sut.size(), "Set size should increase because 10 is not in the set");

        sut.add(10);
        assertEquals(4, sut.size(), "Set size stays the same because 10 is already in the set");
    }

    @Test
    void mapsLetsYouAssociateKeysToValues() {
        var sut = Map.of(1, "a", 2, "b", 3, "c");
        assertEquals("b", sut.get(2));
    }

    @Test
    void initializeACollectionUsingDoubleBraces() {
        var sut = new HashMap<>() {
            {
                put(1, "a");
                put(2, "b");
                put(3, "c");
            }
        };

        assertEquals("c", sut.get(3));
    }

    @Test
    void initializeAnArrayListUsingDoubleBraces() {
        var sut = new ArrayList<>() {
            {
                add(10);
                add(20);
                add(30);
            }
        };

        assertEquals(20, sut.get(1));
    }

    @Test
    void arrayDequeForADoubleEndedQueue() {
        var sut = new ArrayDeque<>(List.of("a", "b", "c", "d", "e"));

        assertEquals(5, sut.size());
        assertEquals("a", sut.getFirst());
        assertEquals("e", sut.getLast());

        assertEquals("a", sut.remove());
        assertEquals("b", sut.getFirst());

        assertEquals("e", sut.removeLast());

        assertEquals("d", sut.getLast());
        assertEquals("b", sut.getFirst());
    }

    @Test
    void youCanMakeAListImmutable() {
        var list = new ArrayList<>(List.of("a", "b", "c"));
        var sut = Collections.unmodifiableList(list);

        assertThrows(UnsupportedOperationException.class, () -> {
            sut.add("d");
        });
    }

    @Test
    void youCanMakeAMapImmutable() {
        var map = Map.of(1, "a", 2, "b", 3, "c");
        var sut = Collections.unmodifiableMap(map);

        assertThrows(UnsupportedOperationException.class, () -> {
            sut.put(4, "d");
        });
    }

    @Test
    void youCanBinarySearch(){
        var sut = List.of("a", "b", "c", "d", "e", "f", "g");
        assertEquals(3, Collections.binarySearch(sut, "d"));
    }

    @Test
    void youCanCountFrequency(){
        var sut = List.of(1, 2, 3, 4, 5, 1, 1, 1, 4, 4, 4, 4);
        assertEquals(1, Collections.frequency(sut, 2));
        assertEquals(4, Collections.frequency(sut, 1));
        assertEquals(5, Collections.frequency(sut, 4));
    }
}
