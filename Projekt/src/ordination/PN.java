package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PN extends Ordination{

    private double antalEnheder;
    private List<LocalDate> datoerForGivetDosis = new ArrayList<>();

    //----------------------------------------------------------------------------------


    public PN(LocalDate startDen, LocalDate slutDen, Patient patient, double antalEnheder)
    {
        super(startDen, slutDen, patient);
        this.antalEnheder = antalEnheder;
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        // TODO

        if ((givesDen.isAfter(getStartDen()) || givesDen.isEqual(getStartDen()))
                && givesDen.isBefore(getSlutDen()) || givesDen.isEqual(getSlutDen()))
        {
            datoerForGivetDosis.add(givesDen);
            return true;
        }
        else
            return false;
    }

    public double doegnDosis() {
        // TODO
        //(antal gange ordinationen er anvendt * antal enheder) / (antal dage mellem første og sidste givning)
        LocalDate første = datoerForGivetDosis.get(0);
        LocalDate sidste = datoerForGivetDosis.get(datoerForGivetDosis.size()-1);
        long dageImellemFørsteOgSidste = ChronoUnit.DAYS.between(første, sidste) + 1;

        return getAntalGangeGivet() * antalEnheder / dageImellemFørsteOgSidste;
    }


    public double samletDosis() {
        // TODO
        //Returnerer den totale dosis der er givet i den periode ordinationen er gyldig
        return getAntalGangeGivet() * antalEnheder;
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        // TODO
        return datoerForGivetDosis.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

    @Override
    public String getType()
    {
        return "PN";
    }
}
