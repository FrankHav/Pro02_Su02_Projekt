package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{
    // TODO

    private LocalDate startDen;
    private LocalDate slutDen;
    private Patient patient;
    private Laegemiddel laegemiddel;
    private LocalTime[] klokkeSlet;
    private double[] antalEnheder;


    public void opretDosis(LocalTime tid, double antal,LocalDate startDen,LocalDate slutDen, Patient patient,
                           Laegemiddel laegemiddel) {
    Dosis dosis = new Dosis(tid, antal);
    dosiser.add(dosis);
        // TODO
    }

    private final ArrayList<Dosis> dosiser = new ArrayList<>();


    @Override
    public double samletDosis() {
        return 0;
    }

    @Override
    public double doegnDosis() {
        return 0;
    }

    @Override
    public String getType() {
        return null;
    }
}
