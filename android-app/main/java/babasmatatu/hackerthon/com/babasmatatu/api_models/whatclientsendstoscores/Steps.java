package babasmatatu.hackerthon.com.babasmatatu.api_models.whatclientsendstoscores;

/**
 * Created by munene on 4/7/2018.
 */
public class Steps
{
    private String instructions;

    private String location;

    public String getInstructions ()
    {
        return instructions;
    }

    public void setInstructions (String instructions)
    {
        this.instructions = instructions;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [instructions = "+instructions+", location = "+location+"]";
    }
}
