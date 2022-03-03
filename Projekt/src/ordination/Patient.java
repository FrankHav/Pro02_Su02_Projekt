package ordination;

import java.util.ArrayList;

public class Patient {
    private String cprnr;
    private String navn;
    private double vaegt;

    //----------------------------------------------------------------
    // TODO: Link til Ordination
    //association --> 0..* Ordination
    private ArrayList<Ordination> ordinationer;

    public ArrayList<Ordination> getOrdinationer()
    {
        return new ArrayList<>(ordinationer);
    }
//TODO: Metoder (med specifikation) til at vedligeholde link til Ordination
    /** Pre: Ordinationen er ikke forbundet til en patient. */
    public void addOrdination(Ordination ordination)
    {
        this.ordinationer.add(ordination);
    }

    /** Pre: Ordinationen er forbundet til denne patient. */
    public void removeOrdination(Ordination ordination)
    {
        this.ordinationer.remove(ordination);
    }

    //----------------------------------------------------------------
    public Patient(String cprnr, String navn, double vaegt) {
        this.cprnr = cprnr;
        this.navn = navn;
        this.vaegt = vaegt;
    }

    public String getCprnr() {
        return cprnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getVaegt(){
        return vaegt;
    }

    public void setVaegt(double vaegt){
        this.vaegt = vaegt;
    }

    @Override
    public String toString(){
        return navn + "  " + cprnr;
    }

}
