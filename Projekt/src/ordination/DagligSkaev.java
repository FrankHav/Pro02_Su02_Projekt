package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{
    // TODO

    private LocalDate startDen;
    private LocalDate slutDen;
    private LocalTime tid;
    private Laegemiddel laegemiddel;
    private LocalTime[] klokkeSlet;
    private double antal;

    public DagligSkaev(LocalDate startDen, LocalDate slutDen,LocalTime tid, double antal,
                       Laegemiddel laegemiddel) {
        super(startDen, slutDen);
        this.laegemiddel = laegemiddel;
        this.antal = antal;
        this.tid = tid;

    }


    public void opretDosis(LocalTime tid, double antal,LocalDate startDen,LocalDate slutDen, Patient patient,
                           Laegemiddel laegemiddel) {
    Dosis dosis = new Dosis(tid, antal);
    dosiser.add(dosis);

            // TODO
    }

    private final ArrayList<Dosis> dosiser = new ArrayList<>();

    public ArrayList<Dosis> getDosiser(){
        return new ArrayList<>(dosiser);
    }

    @Override
    public double samletDosis() {
        double dosisAntal = 0;
        for (Dosis dosis : dosiser)
           dosisAntal += dosis.getAntal();
        return dosisAntal;
    }

    @Override
    public double doegnDosis() {
        double dosisAntal = 0;
        for (Dosis dosis : dosiser)
            if (dosis.)

        return 0;
    }

    @Override
    public String getType() {
        return null;
    }
}
