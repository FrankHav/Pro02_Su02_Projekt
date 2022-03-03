package ordination;

import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast extends Ordination {
    // TODO
    private Dosis morgenAntal;
    private double middagAntal;
    private double aftenAntal;
    private double natAntal;
    private Dosis[] dosisArray = new Dosis[4];

    public DagligFast(LocalDate startDen, LocalDate slutDen, double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        super(startDen, slutDen);
        if (morgenAntal>0){
            createDosis(LocalTime.of(8, 0), morgenAntal,0);
        }
        this.dosisArray[0] = cmorgenAntal;
        this.middagAntal = middagAntal;
        this.aftenAntal = aftenAntal;
        this.natAntal = natAntal;
    }

    public Dosis createDosis(LocalTime tid, double antal, int index){
        Dosis dosis = new Dosis(tid, antal);
        dosisArray[dosisArray.length-1] = dosis;
        return dosis;
    }

    public Dosis[] getDosisArray() {
        return dosisArray;
    }

    @Override
    public double samletDosis() {
        int samletDos = 0;
        for (int i = 0; i < dosisArray.length;i++){
            samletDos += dosisArray[i];
        }
        return ;
    }

    @Override
    public double doegnDosis() {
        return 0;
    }

    @Override
    public String getType() {
        return null;
    }

    //commentar fra Jesper
    //Mads test
}
