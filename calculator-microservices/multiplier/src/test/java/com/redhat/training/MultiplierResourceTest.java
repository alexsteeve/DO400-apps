package com.redhat.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.redhat.training.service.SolverService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class MultiplierResourceTest {
    
    SolverService solverService;
    MultiplierResource multiplierResource;

    @BeforeEach
    public void setup() {
        solverService = mock(SolverService.class);
        multiplierResource = new MultiplierResource(solverService);
    }

    @Test
    public void simpleMultipleOfPositiveValuesTest() {
        when(solverService.solve("4")).thenReturn(4.0f);
        when(solverService.solve("20")).thenReturn(20.0f);
        Float result = multiplierResource.multiply("4", "20");
        assertEquals(80.0f, result);
    }

    @Test
    public void simpleMultipleOfNegativeAndPositiveValuesTest() {
        when(solverService.solve("-4")).thenReturn(-4.0f);
        when(solverService.solve("20")).thenReturn(20.0f);
        Float result = multiplierResource.multiply("-4", "20");
        assertEquals(-80.0f, result);
    }
    @Test
    public void MultiplicationInvalidValuesThrowsErrorTest() {
        when(solverService.solve("a")).thenThrow(new NumberFormatException("Invalid number"));
        when(solverService.solve("20")).thenReturn(20.0f);
        Executable multiplication = () -> multiplierResource.multiply("a" , "20");
        assertThrows(NumberFormatException.class, multiplication);
    }

}
