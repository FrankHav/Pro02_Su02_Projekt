package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DagligSkaev extends Ordination{

    private LocalTime tid;
    private Laegemiddel laegemiddel;
    private LocalTime[] klokkeSlet;
    private double[] antalEnheder;

    //-----------------------------------------------------------------------------------------------------

    public DagligSkaev(LocalDate startDen, LocalDate slutDen,LocalTime klokkeSlet[], double[] antalEnheder, Patient patient) {
        super(startDen, slutDen, patient);
        this.antalEnheder = antalEnheder;
        for (int i = 0; i<klokkeSlet.length; i++){
            opretDosis(klokkeSlet[i],antalEnheder[i]);
        }
    }

    // Composition: --> 0..* Dosis
    private final ArrayList<Dosis> doser = new ArrayList<>();

    public ArrayList<Dosis> getDoser(){
        return new ArrayList<>(doser);
    }


    public Dosis opretDosis(LocalTime tid, double antalEnheder) {
        Dosis dosis = new Dosis(tid, antalEnheder);
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
