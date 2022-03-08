package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class DagligFastTest {
        private Patient patient;
        private LocalDate startDen;
        private LocalDate slutDen;
        private double morgenAntal;
        private double middagAntal;
        private double aftenAntal;
        private double natAntal;
        private Dosis[] dosisArray = new Dosis[4];


    @BeforeEach
        public void setUpBeforeEach()
        {
            this.patient = new Patient("0912931956", "Mads jul", 80);
            this.startDen = LocalDate.of(2022, 3, 4);
            this.slutDen = LocalDate.of(2022, 3, 7);
            this.morgenAntal = 4;
            this.middagAntal = 6;
            this.aftenAntal = 2;
            this.natAntal = 7;
        }

    @Test
    void dagligFast_KorrektOprettelse()
    {
        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        this.morgenAntal = 4;
        this.middagAntal = 6;
        this.aftenAntal = 2;
        this.natAntal = 7;
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 7);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        //Assert
        assertTrue(patient.getOrdinationer().contains(dagligFast));
        assertEquals(dagligFast.getStartDen(), startDen);
        assertEquals(dagligFast.getSlutDen(), slutDen);
        //hvert index er tilsvarende et tidspunkt på døgnet. eks. er indeks[0] svarrende til morgenantal, indeks[1] er svarrende til middagantal osv.
        assertEquals(dagligFast.getDosisArray()[0].getAntal(), 4);
        assertEquals(dagligFast.getDosisArray()[1].getAntal(), 6);
        assertEquals(dagligFast.getDosisArray()[2].getAntal(), 2);
        assertEquals(dagligFast.getDosisArray()[3].getAntal(), 7);
    }

    @Test
    void dagligFast_sammeStartOgSlutDato()
    {
        //Arrange
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 4);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);

        //Assert
        assertEquals(dagligFast.getStartDen(), startDen);
        assertEquals(dagligFast.getSlutDen(), slutDen);
    }

    @Test
    void dagligFast_slutDenFørStartDen()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 5);
        LocalDate slutDen = LocalDate.of(2022, 2, 5);

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        });
        assertTrue(exception.getMessage().contains("Slut dato er før start dato"));
    }

    @Test
    void dagligFast_antalEnhederMinus()
    {
        //Arrange
        morgenAntal = -1;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);        });
        assertTrue(exception.getMessage().contains("Antal enheder er minus"));
    }


    @Test
    void dagligFast_patientNull()
    {
        //Arrange
        patient = null;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        });
        assertTrue(exception.getMessage().contains("Patient er null"));
    }

    @Test
    void dagligFast_OpretDosis(){
        //Arrange
        double morgenAntal = 5;

        //act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);

        dagligFast.createDosis(LocalTime.of(6, 0), morgenAntal, 0);

        //assert
        assertEquals(dagligFast.getDosisArray()[0].getAntal(), 5);
    }
    @Test
    void dagligFast_OpretDosisMed0Antal(){
        //Arrange
        double morgenAntal = 0;

        //act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);

        dagligFast.createDosis(LocalTime.of(6, 0), morgenAntal, 0);

        //assert
        assertEquals(dagligFast.getDosisArray()[0].getAntal(), 0);
    }

    @Test
    void dagligFast_samletDosis_4KorrekteDossier(){
        //Arrange
        morgenAntal = 2;
        middagAntal = 4;
        aftenAntal = 8;
        natAntal = 2;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 7);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = 112;
        //Assert
        assertEquals(expected, dagligFast.samletDosis());
    }

    @Test
    void dagligFast_samletDosis_4dossierMed0(){
        //Arrange
        morgenAntal = 0;
        middagAntal = 0;
        aftenAntal = 0;
        natAntal = 0;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 7);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = 0;
        //Assert
        assertEquals(expected, dagligFast.samletDosis());
    }

    @Test
    void dagligFast_samletDosis_4KorrekteDossier_Med1DagMellem(){
        //Arrange
        morgenAntal = 2;
        middagAntal = 4;
        aftenAntal = 8;
        natAntal = 2;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 1);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = 16;
        //Assert
        assertEquals(expected, dagligFast.samletDosis());
    }

    @Test
    void dagligFast_samletDosis_MedNegativeEnheder(){
        //Arrange
        morgenAntal = -2;
        middagAntal = -4;
        aftenAntal = -8;
        natAntal = -2;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 7);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = -112;
        //Assert
        assertEquals(expected, dagligFast.samletDosis());
    }

    //----------------------------------------------------------
    @Test
    void dagligFast_doegnDosis_4KorrekteDossier(){
        //Arrange
        morgenAntal = 2;
        middagAntal = 4;
        aftenAntal = 8;
        natAntal = 2;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 6);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = 16;
        //Assert
        assertEquals(expected, dagligFast.doegnDosis());
    }

    @Test
    void dagligFast_doegnDosis_4dossierMed0(){
        //Arrange
        morgenAntal = 0;
        middagAntal = 0;
        aftenAntal = 0;
        natAntal = 0;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 6);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = 0;
        //Assert
        assertEquals(expected, dagligFast.doegnDosis());
    }

    @Test
    void dagligFast_doegnDosis_MedNegativeEnheder(){
        //Arrange
        morgenAntal = -2;
        middagAntal = -4;
        aftenAntal = -8;
        natAntal = -2;
        startDen = LocalDate.of(2022, 1, 1);
        slutDen = LocalDate.of(2022, 1, 6);

        //Act
        DagligFast dagligFast = new DagligFast(startDen, slutDen, patient, morgenAntal, middagAntal, aftenAntal, natAntal);
        double expected = -16;
        //Assert
        assertEquals(expected, dagligFast.doegnDosis());
    }
}
