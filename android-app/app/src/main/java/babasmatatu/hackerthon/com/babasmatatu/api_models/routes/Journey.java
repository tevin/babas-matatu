package babasmatatu.hackerthon.com.babasmatatu.api_models.routes;

/**
 * Created by munene on 4/7/2018.
 */
public class Journey
{
    private String id;

    private Steps[] steps;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Steps[] getSteps ()
    {
        return steps;
    }

    public void setSteps (Steps[] steps)
    {
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", steps = "+steps+"]";
    }
}
