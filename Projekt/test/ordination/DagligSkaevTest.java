package ordination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DagligSkaevTest {

    private Patient patient;
    private double[] antalEnheder = new double[3];
    private LocalDate startDen;
    private LocalDate slutDen;
    private LocalTime[] klokkeSlet = new LocalTime[3];

    @BeforeEach
    public void setUpBeforeEach_DagligSkaevConstructorBasisData()
    {
        this.patient = new Patient("0912931956", "Mads jul", 80);
        this.antalEnheder = new double[]{3,2,2} ;
        this.startDen = LocalDate.of(2022, 3, 4);
        this.slutDen = LocalDate.of(2022, 3, 7);
        this.klokkeSlet = new LocalTime[]{LocalTime.of(12, 00),LocalTime.of(13, 30),LocalTime.of(14, 00)};
    }

    //-------------------- DagligSkaev(constructor) ----------------------------

    @Test
    void DagligSkaev_korrektOprettelse() {
        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        antalEnheder = new double[]{3, 2, 2};
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 7);
        klokkeSlet = new LocalTime[]{LocalTime.of(12, 00), LocalTime.of(13, 30), LocalTime.of(14, 00)};

        //Act
        DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);


        //Assert
        assertTrue(patient.getOrdinationer().contains(DS));
        assertEquals(3, DS.getDoser().size());
        assertEquals(DS.getStartDen(), startDen);
        assertEquals(DS.getSlutDen(), slutDen);

    }

    @Test
    void Dagligskaev_klokkeSlet_ikkeSammeLængdeSom_antalEnheder(){
        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        antalEnheder = new double[]{3, 2};
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 7);
        klokkeSlet = new LocalTime[]{LocalTime.of(12, 00), LocalTime.of(13, 30), LocalTime.of(14, 00)};

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);
        });
        assertTrue(exception.getMessage().contains("Antal elementer i klokkeSlet og antalEnheder er ikke ens"));

    }


    @Test
    void dagligSkaev_antalEnhederMinus()
    {
        //Arrange
        antalEnheder = new double[]{-1};
        klokkeSlet = new LocalTime[] {LocalTime.of(12, 30)};

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);
        });
        assertTrue(exception.getMessage().contains("Antal enheder er minus"));
    }

    @Test
    void dagligSkaev_antalEnheder0()
    {
        //Arrange
        antalEnheder = new double[]{0};
        klokkeSlet = new LocalTime[] {LocalTime.of(12, 30)};

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);
        });
        assertTrue(exception.getMessage().contains("Antal enheder er 0"));
    }



    @Test
    void DS_patientNull()
    {
        //Arrange
        patient = null;

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            DagligSkaev DS = new DagligSkaev(startDen,slutDen,klokkeSlet,antalEnheder,patient);
        });
        assertTrue(exception.getMessage().contains("Patient er null"));
    }


    @Test
    void samletDosis_antalEnheder0()
    {
        //Arrange
        antalEnheder = new double[]{0};
        klokkeSlet = new LocalTime[] {LocalTime.of(12, 30)};
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        DagligSkaev DS = new DagligSkaev(startDen,slutDen,klokkeSlet,antalEnheder,patient);;

        double expected = 0;

        //Act
        double actual = DS.samletDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void samletDosis_antalEnheder7() {

        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        antalEnheder = new double[]{3, 2, 2};
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 7);
        klokkeSlet = new LocalTime[]{LocalTime.of(12, 00), LocalTime.of(13, 30), LocalTime.of(14, 00)};
        DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);
        double expected = 7;

        //Act
        double actual = DS.samletDosis();


        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void doegnDosis_antalEnheder0()
    {
        //Arrange
        antalEnheder = new double[]{0};
        klokkeSlet = new LocalTime[] {LocalTime.of(12, 30)};
        startDen = LocalDate.of(2022, 3, 2);
        slutDen = LocalDate.of(2022, 3, 12);
        DagligSkaev DS = new DagligSkaev(startDen,slutDen,klokkeSlet,antalEnheder,patient);;

        double expected = 0;

        //Act
        double actual = DS.doegnDosis();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void doegnDosis_7enheder_over7dage() {

        //Arrange
        patient = new Patient("0912931956", "Mads jul", 80);
        antalEnheder = new double[]{3, 2, 2};
        startDen = LocalDate.of(2022, 3, 1);
        slutDen = LocalDate.of(2022, 3, 7);
        klokkeSlet = new LocalTime[]{LocalTime.of(12, 00), LocalTime.of(13, 30), LocalTime.of(14, 00)};
        DagligSkaev DS = new DagligSkaev(startDen, slutDen, klokkeSlet, antalEnheder, patient);
        double expected = 1;

        //Act
        double actual = DS.doegnDosis();

        //Assert
        assertEquals(expected, actual);
    }


    @Test
    void DS_sammeStartOgSlutDato()
    {
        //Arrange
        startDen = LocalDate.of(2022, 3, 4);
        slutDen = LocalDate.of(2022, 3, 4);

        //Act
        DagligSkaev DS = new DagligSkaev(startDen,slutDen,klokkeSlet,antalEnheder,patient);

        //Assert
        assertEquals(DS.getStartDen(), startDen);
        assertEquals(DS.getSlutDen(), slutDen);
    }

}