package pl.sdacademy.vending.model;

import org.junit.Test;
import pl.sdacademy.vending.util.Configuration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class VendingMachineTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExeptionWhenZeroRowsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(0L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exeption should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExeptionWhenUpToNineRowsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(10L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exeption should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExeptionWhenZeroColsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(5L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(0L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exeption should be raised");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExeptionWhenUpTo26ColsConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(5L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(27L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
        fail("Exeption should be raised");
    }

    @Test()
    public void shouldCreateTrueConfigured() {
        // Given
        Configuration config = mock(Configuration.class);
        doReturn(5L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(7L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        // When
        new VendingMachine(config);
        // Then
    }

    @Test
    public void shouldBeAbleToAddTrayToEmptySpot() {
        //Given
        Tray tray = Tray.builder("A2").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean placed = testedMachine.placeTray(tray);
        // Than
        assertTrue(placed);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }

    @Test
    public void shouldNotBeAbleToAddTrayToTakenSpot() {
        // Given
        Tray tray = Tray.builder("A2").build();
        Tray secondTray = Tray.builder("A2").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean firstTrayPlacementResult = testedMachine.placeTray(tray);
        boolean secondTrayPlacementResult = testedMachine.placeTray(secondTray);
        // Then
        assertTrue(firstTrayPlacementResult);
        assertFalse(secondTrayPlacementResult);
        assertEquals(tray, testedMachine.getTrayAtPosition(0, 1).get());
    }

    @Test
    public void shouldNotBeAbleToAddTrayToSpotOverMachine() {
        // Given
        Tray tray = Tray.builder("$$").build();
        Configuration config = mock(Configuration.class);
        doReturn(6L).when(config).getLongProperty(eq("machine.size.rows"), anyLong());
        doReturn(4L).when(config).getLongProperty(eq("machine.size.cols"), anyLong());
        VendingMachine testedMachine = new VendingMachine(config);
        // When
        boolean placed = testedMachine.placeTray(tray);
        // Then
        assertFalse(placed);
    }
}