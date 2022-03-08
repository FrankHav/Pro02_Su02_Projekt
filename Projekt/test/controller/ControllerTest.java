package controller;

import ordination.*;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest
{
    private Patient patient;
    private Laegemiddel laegemiddel;

    @BeforeEach
    public void setUpBeforeEach()
    {
        this.patient = new Patient("1234567890", "Mads jul", 52.00);
        this.laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.5,
                0.7, 1, "Styk");

    }

    //-------------------- opretDagligFastOrdination ----------------------------
    @Test
    void opretDagligFastOrdination_korrektOprettelse()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 7);
        LocalDate slutDen = LocalDate.of(2022, 3, 15);
        double morgenAntal = 1;
        double middagAntal = 1;
        double aftenAntal = 1;
        double natAntal = 1;

        int expectedArrayLength = 4;

        //Act
        DagligFast dagligFast = Controller.getController().opretDagligFastOrdination(startDen, slutDen,
                patient, laegemiddel, morgenAntal, middagAntal, aftenAntal, natAntal);

        //Assert
        assertEquals(expectedArrayLength, dagligFast.getDosisArray().length);
        assertTrue(patient.getOrdinationer().contains(dagligFast));
        assertEquals(laegemiddel, dagligFast.getLaegemiddel());
    }

    @Test
    void opretDagligFastOrdination_ugyldigData()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 7);
        LocalDate slutDen = LocalDate.of(2022, 3, 5);
        double morgenAntal = 1;
        double middagAntal = 1;
        double aftenAntal = 1;
        double natAntal = 1;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Controller.getController().opretDagligFastOrdination(startDen, slutDen, patient,
                    laegemiddel, morgenAntal, middagAntal, aftenAntal, natAntal);
        });
        assertTrue(exception.getMessage().contains("Startdato er efter slutdato"));
    }

    //-------------------- opretDagligSkaevOrdination ----------------------------
    @Test
    void opretDagligSkaevOrdination_korrektOprettelseMedLinks()
    {
        //Arrange
        this.patient = new Patient("123456-7890", "Frank Hav", 100);
        this.laegemiddel = new Laegemiddel("Paracetamol", 0.5,
                0.7, 1, "Ml");
        LocalDate startDen = LocalDate.of(2022, 3, 4);
        LocalDate slutDen = LocalDate.of(2022, 3, 10);
        LocalTime[] klokkeslet = { LocalTime.of(10, 0), LocalTime.of(20, 30)};
        double[] antalEnheder = { 2, 4 };

        //Act
        DagligSkaev dagligSkaev = Controller.getController().opretDagligSkaevOrdination(startDen, slutDen,
                patient, laegemiddel, klokkeslet, antalEnheder);

        //Assert
        assertTrue(patient.getOrdinationer().contains(dagligSkaev));
        assertEquals(laegemiddel, dagligSkaev.getLaegemiddel());
    }

    @Test
    void opretDagligSkaevOrdination_startdatoEfterSlutdatoException()
    {
        //Arrange
        this.patient = new Patient("123456-7890", "Frank Hav", 100);
        this.laegemiddel = new Laegemiddel("Paracetamol", 0.5,
                0.7, 1, "Ml");
        LocalDate startDen = LocalDate.of(2022, 3, 10);
        LocalDate slutDen = LocalDate.of(2022, 3, 4);
        LocalTime[] klokkeslet = { LocalTime.of(10, 0), LocalTime.of(20, 30)};
        double[] antalEnheder = { 2, 4 };

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Controller.getController().opretDagligSkaevOrdination(startDen, slutDen, patient,
                    laegemiddel, klokkeslet, antalEnheder);
        });
        assertTrue(exception.getMessage().contains("StartDato er efter slutDato"));
    }

    @Test
    void opretDagligSkaevOrdination_klokkesletOgEnhederIkkeSammeAntalException()
    {
        //Arrange
        this.patient = new Patient("123456-7890", "Frank Hav", 100);
        this.laegemiddel = new Laegemiddel("Paracetamol", 0.5,
                0.7, 1, "Ml");
        LocalDate startDen = LocalDate.of(2022, 3, 4);
        LocalDate slutDen = LocalDate.of(2022, 3, 10);
        LocalTime[] klokkeslet = { LocalTime.of(10, 0), LocalTime.of(20, 30)};
        double[] antalEnheder = { 2, 4, 6 };

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Controller.getController().opretDagligSkaevOrdination(startDen, slutDen, patient,
                    laegemiddel, klokkeslet, antalEnheder);
        });
        assertTrue(exception.getMessage().contains(
                "Antal elementer i klokkeSlet og antalEnheder er ikke ens"));
    }

    //-------------------- opretPNOrdination ----------------------------
    @Test
    void opretPNOrdination_korrektOprettelseMedLinks()
    {
        //Arrange
        this.laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.3,
                0.4, 5, "Styk");
        Laegemiddel laegemiddel2 = null;
        LocalDate startDen = LocalDate.of(2022, 3, 4);
        LocalDate slutDen = LocalDate.of(2022, 3, 10);
        double antal = 5;

        //Act
        PN PN = Controller.getController().opretPNOrdination(startDen, slutDen, patient,
                laegemiddel, antal);

        //Assert
        assertTrue(patient.getOrdinationer().contains(PN));
        assertEquals(laegemiddel, PN.getLaegemiddel());
    }

    @Test
    void opretPNOrdination_startDatoEfterSlutDato()
    {
        //Arrange
        this.laegemiddel = new Laegemiddel("Acetylsalicylsyre", 0.3,
                0.4, 5, "Styk");
        LocalDate startDen = LocalDate.of(2022, 3, 10);
        LocalDate slutDen = LocalDate.of(2022, 3, 4);
        double antal = 5;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Controller.getController().opretPNOrdination(startDen, slutDen, patient,
                    laegemiddel, antal);
        });
        assertTrue(exception.getMessage().contains("StartDato er efter slutDato"));
    }

    //-------------------- anbefaletDosisPrDoegn ----------------------------
    @Test
    void anbefaletDosisPrDoegn_20kg()
    {
        double expected = 10;
        patient = new Patient("120621212", "Mads", 20);
        laegemiddel = new Laegemiddel("paracetamol", 0.5, 0.7,
                0.9, "styk");
        //arrange.
        double c1 = Controller.getController().anbefaletDosisPrDoegn(patient, laegemiddel);

        assertEquals(10,c1);
    }
    @Test
    void anbefaletDosisPrDoegn_25kg_GraenseVaerdi()
    {
        double expected = 17.5;
        patient = new Patient("120621212", "Mads", 25);
        laegemiddel = new Laegemiddel("paracetamol", 0.5, 0.7,
                0.9, "styk");
        //arrange.
        double c1 = Controller.getController().anbefaletDosisPrDoegn(patient, laegemiddel);

        assertEquals(expected,c1);
    }
    @Test
    void anbefaletDosisPrDoegn_50kg()
    {
        double expected = 35;
        patient = new Patient("120621212", "Mads", 50);
        laegemiddel = new Laegemiddel("paracetamol", 0.5, 0.7,
                0.9, "styk");
        //arrange.
        double c1 = Controller.getController().anbefaletDosisPrDoegn(patient, laegemiddel);

        assertEquals(expected,c1);
    }
    @Test
    void anbefaletDosisPrDoegn_125kg_GraenseVaerdi()
    {
        double expected = 112.5;
        patient = new Patient("120621212", "Mads", 125);
        laegemiddel = new Laegemiddel("paracetamol", 0.5, 0.7,
                0.9, "styk");
        //arrange.
        double c1 = Controller.getController().anbefaletDosisPrDoegn(patient, laegemiddel);

        assertEquals(expected,c1);
    }
    @Test
    void anbefaletDosisPrDoegn_140kg()
    {
        double expected = 126;
        patient = new Patient("120621212", "Mads", 140);
        laegemiddel = new Laegemiddel("paracetamol", 0.5, 0.7,
                0.9, "styk");
        //arrange.
        double c1 = Controller.getController().anbefaletDosisPrDoegn(patient, laegemiddel);

        assertEquals(expected,c1);
    }

    //-------------------- ordinationPNAnvendt ----------------------------
    @Test
    void ordinationPNAnvendt_korrektOprettelseDatoSamtGrænseværdier()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 9, 2);
        LocalDate slutDen = LocalDate.of(2022, 9, 9);
        double antal = 5;
        PN PN = Controller.getController().opretPNOrdination(startDen, slutDen, patient,
                laegemiddel, antal);
        LocalDate dato1 = LocalDate.of(2022, 9, 4);
        LocalDate dato2 = LocalDate.of(2022, 9, 9);
        LocalDate dato3 = LocalDate.of(2022, 9, 2);

        //Act
        Controller.getController().ordinationPNAnvendt(PN, dato1);
        Controller.getController().ordinationPNAnvendt(PN, dato2);
        Controller.getController().ordinationPNAnvendt(PN, dato3);

        //Assert
        assertTrue(PN.getDatoerForGivetDosis().contains(dato1));
        assertTrue(PN.getDatoerForGivetDosis().contains(dato2));
        assertTrue(PN.getDatoerForGivetDosis().contains(dato3));
    }

    @Test
    void ordinationPNAnvendt_datoUgyldigeVærdier()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 9, 2);
        LocalDate slutDen = LocalDate.of(2022, 9, 9);
        double antal = 5;
        PN PN = Controller.getController().opretPNOrdination(startDen, slutDen, patient,
                laegemiddel, antal);
        LocalDate dato = LocalDate.of(2022, 9, 1);

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Controller.getController().ordinationPNAnvendt(PN, dato);
        });
        assertTrue(exception.getMessage().contains(
                "Datoen ikke er indenfor ordinationens gyldighedsperiode"));
    }

    //-------------------- antalOrdinationerPrVægtPrLægemiddel ----------------------------

    @Test
    void antalOrdinationerPrVægtPrLægemiddel_gyldigeMidtVærdierVægt()
    {
        //Arrange
        Controller controller = new Controller();

        Patient patient1 = controller.opretPatient("121256-0512", "Jane Jensen", 63.4);
        Patient patient2 = controller.opretPatient("070985-1153", "Finn Madsen", 83.2);
        Patient patient3 = controller.opretPatient("050972-1233", "Hans Jørgensen", 89.4);


        Laegemiddel laegemiddel1 = controller.opretLaegemiddel("Acetylsalicylsyre", 0.1,
                0.15, 0.16, "Styk");
        Laegemiddel laegemiddel2 = controller.opretLaegemiddel("Paracetamol", 1,
                1.5, 2, "Ml");
        Laegemiddel laegemiddel3 = controller.opretLaegemiddel("Fucidin", 0.025,
                0.025, 0.025, "Styk");

        PN PNOrdination1 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel1, 5);
        PN PNOrdination2 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel2, 5);
        PN PNOrdination3 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel2, 5);
        PN PNOrdination4 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel3, 5);
        PN PNOrdination5 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel1, 5);
        PN PNOrdination6 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient3, laegemiddel3, 5);

        //Act
        int actualAntal1 = controller.antalOrdinationerPrVægtPrLægemiddel(
                30, 60, laegemiddel1);
        int actualAntal2 = controller.antalOrdinationerPrVægtPrLægemiddel(
                60, 70, laegemiddel1);
        //Assert
        assertEquals(0, actualAntal1);
        assertEquals(1, actualAntal2);
    }

    @Test
    void antalOrdinationerPrVægtPrLægemiddel_gyldigeGrænseVærdierVægt()
    {
        //Arrange
        Controller controller = new Controller();

        Patient patient1 = controller.opretPatient("121256-0512", "Jane Jensen", 63.4);
        Patient patient2 = controller.opretPatient("070985-1153", "Finn Madsen", 83.2);
        Patient patient3 = controller.opretPatient("050972-1233", "Hans Jørgensen", 89.4);

        Laegemiddel laegemiddel1 = controller.opretLaegemiddel("Acetylsalicylsyre", 0.1,
                0.15, 0.16, "Styk");
        Laegemiddel laegemiddel2 = controller.opretLaegemiddel("Paracetamol", 1,
                1.5, 2, "Ml");
        Laegemiddel laegemiddel3 = controller.opretLaegemiddel("Fucidin", 0.025,
                0.025, 0.025, "Styk");

        PN PNOrdination1 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel1, 5);
        PN PNOrdination2 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel2, 5);
        PN PNOrdination3 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel2, 5);
        PN PNOrdination4 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel3, 5);
        PN PNOrdination5 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel1, 5);
        PN PNOrdination6 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient3, laegemiddel3, 5);

        //Act
        int actualAntal1 = controller.antalOrdinationerPrVægtPrLægemiddel(
                60, 63.4, laegemiddel2);
        int actualAntal2 = controller.antalOrdinationerPrVægtPrLægemiddel(
                63.4, 70, laegemiddel1);
        int actualAntal3 = controller.antalOrdinationerPrVægtPrLægemiddel(
                70, 89.4, laegemiddel3);

        //Assert
        assertEquals(1, actualAntal1);
        assertEquals(1, actualAntal2);
        assertEquals(2, actualAntal3);
    }

    @Test
    void antalOrdinationerPrVægtPrLægemiddel_vægtStartMinus()
    {
        //Arrange
        Controller controller = new Controller();

        Patient patient1 = controller.opretPatient("121256-0512", "Jane Jensen", 63.4);
        Patient patient2 = controller.opretPatient("070985-1153", "Finn Madsen", 83.2);
        Patient patient3 = controller.opretPatient("050972-1233", "Hans Jørgensen", 89.4);


        Laegemiddel laegemiddel1 = controller.opretLaegemiddel("Acetylsalicylsyre", 0.1,
                0.15, 0.16, "Styk");
        Laegemiddel laegemiddel2 = controller.opretLaegemiddel("Paracetamol", 1,
                1.5, 2, "Ml");
        Laegemiddel laegemiddel3 = controller.opretLaegemiddel("Fucidin", 0.025,
                0.025, 0.025, "Styk");

        PN PNOrdination1 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel1, 5);
        PN PNOrdination2 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient1, laegemiddel2, 5);
        PN PNOrdination3 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel2, 5);
        PN PNOrdination4 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel3, 5);
        PN PNOrdination5 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient2, laegemiddel1, 5);
        PN PNOrdination6 = controller.opretPNOrdination(
                LocalDate.of(2021, 1, 20),
                LocalDate.of(2021, 1, 25), patient3, laegemiddel3, 5);

        //Act
        int actualAntal = controller.antalOrdinationerPrVægtPrLægemiddel(
                -22, 70, laegemiddel1);

        //Assert
        assertEquals(1, actualAntal);
    }
}