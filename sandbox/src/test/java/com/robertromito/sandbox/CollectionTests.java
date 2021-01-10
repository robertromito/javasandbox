package com.robertromito.sandbox;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CollectionTests {

    @Test
    void iteratingOverAList() {
        var sut = List.of("a", "b", "c", "d", "e");
        for(String s: sut) {
            assertNotNull(s);
        }
    }

    
}
