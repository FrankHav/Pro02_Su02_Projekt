package ordination;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PNTest
{
    private Patient patient;
    private double antalEnheder;
    private LocalDate startDen;
    private LocalDate slutDen;

    @BeforeEach
    public void setUpBeforeEach()
    {
        this.patient = new Patient("0912931956", "Mads jul", 80);
        this.antalEnheder = 5;
        this.startDen = LocalDate.of(2022, 3, 4);
        this.slutDen = LocalDate.of(2022, 3, 7);
    }

    @Test
    void PN_korrektOprettelse()
    {
        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        antalEnheder = 5;
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 7);

        //Act
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);

        //Assert
        assertTrue(patient.getOrdinationer().contains(PN));
        assertEquals(5, PN.getAntalEnheder());
        assertEquals(PN.getStartDen(), startDen);
        assertEquals(PN.getSlutDen(), slutDen);
    }

    @Test
    void PN_sammeStartOgSlutDato()
    {
        //Arrange
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 4);

        //Act
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);

        //Assert
        assertEquals(PN.getStartDen(), startDen);
        assertEquals(PN.getSlutDen(), slutDen);
    }

    @Test
    void PN_slutDenFørStartDen()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 5);
        LocalDate slutDen = LocalDate.of(2022, 2, 5);

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        });
        assertTrue(exception.getMessage().contains("Slut dato er før start dato"));

        // TODO:
        // Test for at PN ikke er blevet oprettet


    }

    @Test
    void PN_antalEnhederMinus()
    {
        //Arrange
        antalEnheder = -1;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        });
        assertTrue(exception.getMessage().contains("Antal enheder er minus"));

        // TODO:
        // Test for at PN ikke er blevet oprettet
    }

    @Test
    void PN_patientNull()
    {
        //Arrange
        patient = null;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        });
        assertTrue(exception.getMessage().contains("Patient er null"));

        // TODO:
        // Test for at PN ikke er blevet oprettet
    }
}