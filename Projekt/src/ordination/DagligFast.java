package ordination;

import java.time.LocalDate;

public class DagligFast extends Ordination {
    // TODO
    private double morgenAntal;
    private double middagAntal;
    private double aftenAntal;
    private double natAntal;
    private Dosis[] dosisArray = new Dosis[4];

    public DagligFast(double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        this.morgenAntal = morgenAntal;
        this.middagAntal = middagAntal;
        this.aftenAntal = aftenAntal;
        this.natAntal = natAntal;
    }

    public void createDosis(){

    }

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

    //commentar fra Jesper
    //Mads test
}
