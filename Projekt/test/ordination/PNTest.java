package ordination;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class PNTest
{
    private Patient patient;
    private double antalEnheder;

    @BeforeEach
    public void setUpBeforeEach()
    {
        this.patient = new Patient("0912931956", "Mads jul", 80);
        this.antalEnheder = 5;
    }

    @Test
    void PN_KorrektOprettelse()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 10);
        LocalDate slutDen = LocalDate.of(2022, 3, 10);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);

        //Act


        //Assert
    }

    @Test
    void PN_SlutDenFørStartDen()
    {
        //Arrange
        LocalDate startDen = LocalDate.of(2022, 3, 10);
        LocalDate slutDen = LocalDate.of(2022, 3, 10);
        PN PN = new PN(startDen, slutDen, patient, antalEnheder);

        //Act


        //Assert
    }
}