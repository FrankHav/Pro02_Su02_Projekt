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
    public void setUpBeforeEach_PNConstructorBasisData()
    {
        this.patient = new Patient("0912931956", "Mads jul", 80);
        this.antalEnheder = 5;
        this.startDen = LocalDate.of(2022, 3, 4);
        this.slutDen = LocalDate.of(2022, 3, 7);
    }

    //-------------------- PN(constructor) ----------------------------
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

    //-------------------- givDosis ----------------------------
    @Test
    void givDosis_korrekteGrænseværdier_4og7()
    {
        //Arrange
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        LocalDate givesDen1 = LocalDate.of(2022, 3, 4);
        LocalDate givesDen2 = LocalDate.of(2022, 3, 7);

        //Act
        boolean givesDenBoolean1 = PN.givDosis(givesDen1);
        boolean givesDenBoolean2 = PN.givDosis(givesDen2);

        //Assert
        assertTrue(givesDenBoolean1);
        assertTrue(PN.getDatoerForGivetDosis().contains(givesDen1));

        assertTrue(givesDenBoolean2);
        assertTrue(PN.getDatoerForGivetDosis().contains(givesDen2));
    }

    @Test
    void givDosis_ugyldigeGrænseværdier_3og8()
    {
        //Arrange
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        LocalDate givesDen1 = LocalDate.of(2022, 3, 3);
        LocalDate givesDen2 = LocalDate.of(2022, 3, 8);

        //Act
        boolean givesDenBoolean1 = PN.givDosis(givesDen1);
        boolean givesDenBoolean2 = PN.givDosis(givesDen2);

        //Assert
        assertFalse(givesDenBoolean1);
        assertFalse(PN.getDatoerForGivetDosis().contains(givesDen1));

        assertFalse(givesDenBoolean2);
        assertFalse(PN.getDatoerForGivetDosis().contains(givesDen2));
    }

    //-------------------- doegnDosis ----------------------------
    @Test
    void doegnDosis_7dageMellemFørsteOgSidste()
    {
        //Arrange
        antalEnheder = 4;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        PN.givDosis(LocalDate.of(2022, 3, 4));
        PN.givDosis(LocalDate.of(2022, 3, 10));

        double expected = (double) 4*2/7;

        //Act
        double tal = PN.doegnDosis();

        //Assert
        assertEquals(expected, tal);
    }

    @Test
    void doegnDosis_1dagMellemFørsteOgSidste()
    {
        //Arrange
        antalEnheder = 4;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        PN.givDosis(LocalDate.of(2022, 3, 4));
        PN.givDosis(LocalDate.of(2022, 3, 4));

        double expected = 8;

        //Act
        double actual = PN.doegnDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void doegnDosis_ingenGivedeDoser()
    {
        //Arrange
        antalEnheder = 4;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
//        PN.givDosis(LocalDate.of(2022, 3, 4));
//        PN.givDosis(LocalDate.of(2022, 3, 4));

        double expected = 0;

        //Act
        double actual = PN.doegnDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void doegnDosis_antalEnheder0()
    {
        //Arrange
        antalEnheder = 0;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        PN.givDosis(LocalDate.of(2022, 3, 4));
        PN.givDosis(LocalDate.of(2022, 3, 10));

        double expected = 0;

        //Act
        double tal = PN.doegnDosis();

        //Assert
        assertEquals(expected, tal);
    }

    //-------------------- samletDosis ----------------------------
    @Test
    void samletDosis_2GangeGivet()
    {
        //Arrange
        antalEnheder = 4;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        PN.givDosis(LocalDate.of(2022, 3, 4));
        PN.givDosis(LocalDate.of(2022, 3, 10));

        double expected = 8;

        //Act
        double actual = PN.samletDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void samletDosis_0GangeGivet()
    {
        //Arrange
        antalEnheder = 8;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
//        PN.givDosis(LocalDate.of(2022, 3, 4));
//        PN.givDosis(LocalDate.of(2022, 3, 10));

        double expected = 0;

        //Act
        double actual = PN.samletDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void samletDosis_antalEnheder0()
    {
        //Arrange
        antalEnheder = 0;
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);
        PN.givDosis(LocalDate.of(2022, 3, 4));
        PN.givDosis(LocalDate.of(2022, 3, 10));

        double expected = 0;

        //Act
        double actual = PN.samletDosis();

        //Assert
        assertEquals(expected, actual);
    }
}