package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DagligFast extends Ordination {
    // TODO
    private Dosis[] dosisArray = new Dosis[4];

    //
    public DagligFast(LocalDate startDen, LocalDate slutDen, Patient patient, double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        super(startDen, slutDen,patient);

        createDosis(LocalTime.of(6, 0), morgenAntal,0);
        createDosis(LocalTime.of(12, 0), middagAntal, 1);
        createDosis(LocalTime.of(18, 0), aftenAntal, 2);
        createDosis(LocalTime.of(0, 0), natAntal, 3);
    }

    public Dosis createDosis(LocalTime tid, double antal, int index){
        Dosis dosis = new Dosis(tid, antal);
        dosisArray[index] = dosis;
        return dosis;
    }

    public Dosis[] getDosisArray() {
        return dosisArray;
    }

    //Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
    @Override
    public double samletDosis()
    {
        long dageMellem = ChronoUnit.DAYS.between(getStartDen(), getSlutDen()) + 1;

        double samletAntal = 0;
        for (Dosis dosis : dosisArray)
            samletAntal += dosis.getAntal();

        return dageMellem * samletAntal;
    }

    //Returnerer den samlede dosis givet pr dag
    @Override
    public double doegnDosis() {
        int sum = 0;
        for (int i = 0; i < dosisArray.length; i++){
            sum += dosisArray[i].getAntal();
        }
        return sum;
    }

    @Override
    public String getType() {
        return "Dagligfast";
    }

    //commentar fra Jesper
    //Mads test
}
