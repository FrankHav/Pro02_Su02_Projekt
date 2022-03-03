package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Ordination {
    private LocalDate startDen;
    private LocalDate slutDen;

    //----------------------------------------------------------------
    // TODO Link til Laegemiddel
    //association --> 0..1 Laegemiddel
    private Laegemiddel laegemiddel;

    public void setLaegemiddel(Laegemiddel laegemiddel)
    {
        this.laegemiddel = laegemiddel;
    }

    public void removeLaegemiddel(Laegemiddel laegemiddel)
    {
        this.laegemiddel = null;
    }

    //----------------------------------------------------------------
    // TODO constructor (med specifikation)
    //Opretter on ordination med en startdato og en slutdato
    public Ordination(LocalDate startDen, LocalDate slutDen)
    {
        this.startDen = startDen;
        this.slutDen = slutDen;
    }

    public LocalDate getStartDen() {
        return startDen;
    }	

    public LocalDate getSlutDen() {
        return slutDen;
    }

    /**
     * Antal hele dage mellem startdato og slutdato. Begge dage inklusive.
     * @return antal dage ordinationen gælder for
     */
    public int antalDage() {
        return (int) ChronoUnit.DAYS.between(startDen, slutDen) + 1;
    }

    @Override
    public String toString() {
        return startDen.toString();
    }

    /**
     * Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
     * @return
     */
    public abstract double samletDosis();

    /**
     * Returnerer den gennemsnitlige dosis givet pr dag i den periode ordinationen er gyldig
     * @return
     */
    public abstract double doegnDosis();

    /**
     * Returnerer ordinationstypen som en String
     * @return
     */
    public abstract String getType();
}
