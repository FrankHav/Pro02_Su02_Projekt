package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DagligFast extends Ordination {
    // TODO
    private double morgenAntal;
    private double middagAntal;
    private double aftenAntal;
    private double natAntal;
    private Dosis[] dosisArray = new Dosis[4];

    //
    public DagligFast(LocalDate startDen, LocalDate slutDen, Patient patient, double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        super(startDen, slutDen,patient);
        if (morgenAntal > 0)
            createDosis(LocalTime.of(6, 0), morgenAntal,0);

        if (middagAntal > 0)
            createDosis(LocalTime.of(12, 0), middagAntal, 1);

        if (aftenAntal > 0 )
            createDosis(LocalTime.of(18, 0), aftenAntal, 2);

       if (natAntal > 0)
           createDosis(LocalTime.of(24, 0), natAntal, 3);
    }

    public Dosis createDosis(LocalTime tid, double antal, int index){
        Dosis dosis = new Dosis(tid, antal);
        dosisArray[index] = dosis;
        return dosis;
    }

    public Dosis[] getDosisArray() {
        return dosisArray;
    }

    @Override
    public double samletDosis() {
        long dageMellem = ChronoUnit.DAYS.between(getStartDen(), getSlutDen());
        return dageMellem*4;
    }

    @Override
    public double doegnDosis() {
        int sum = 0;
        int count = 0;
        for (int i = 0; i < dosisArray.length; i++){
            sum += dosisArray[i].getAntal();
            count++;
        }
        return sum/count;
    }

    @Override
    public String getType() {
        return "Dagligfast";
    }

    //commentar fra Jesper
    //Mads test
}
