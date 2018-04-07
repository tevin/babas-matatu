package babasmatatu.hackerthon.com.babasmatatu.api_models.whatclientsendstoscores;

/**
 * Created by munene on 4/7/2018.
 */
public class Journey
{
    private Legs[][] legs;

    public Legs[][] getLegs ()
    {
        return legs;
    }

    public void setLegs (Legs[][] legs)
    {
        this.legs = legs;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [legs = "+legs+"]";
    }
}
