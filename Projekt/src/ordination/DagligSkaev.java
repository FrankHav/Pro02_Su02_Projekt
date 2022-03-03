package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    private LocalTime tid;
    private Laegemiddel laegemiddel;
    private LocalTime[] klokkeSlet;
    private double antal;

    //-----------------------------------------------------------------------------------------------------

    public DagligSkaev(LocalDate startDen, LocalDate slutDen,LocalTime tid, double antal, Patient patient) {
        super(startDen, slutDen, patient);
        this.antal = antal;
        this.tid = tid;

    }

    // Composition: --> 0..* Dosis
    private final ArrayList<Dosis> doser = new ArrayList<>();

    public ArrayList<Dosis> getDoser(){
        return new ArrayList<>(doser);
    }


    public Dosis opretDosis(LocalTime tid, double antal,LocalDate startDen,LocalDate slutDen, Patient patient,
                           Laegemiddel laegemiddel) {
        Dosis dosis = new Dosis(tid, antal);
        doser.add(dosis);
        return dosis;
    }

    public void removeDosis(Dosis dosis){
        if(doser.contains(dosis)){
            doser.remove(dosis);
        }

    }


    @Override
    public double samletDosis() {
        double dosisAntal = 0;
        for (Dosis dosis : doser)
           dosisAntal += dosis.getAntal();
        return dosisAntal;
    }

    @Override
    public double doegnDosis() {
        double dosisAntal = 0;
        for (Dosis dosis : doser)
            dosisAntal += dosis.getAntal();
        return  dosisAntal/antalDage();
    }

    @Override
    public String getType() {
        return "SKAEV";
    }

}
