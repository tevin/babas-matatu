package babasmatatu.hackerthon.com.babasmatatu.api_models.whatclientsendstoscores;

/**
 * Created by munene on 4/7/2018.
 */
public class Waypoints
{
    private String location;

    private String stopId;

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getStopId ()
    {
        return stopId;
    }

    public void setStopId (String stopId)
    {
        this.stopId = stopId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [location = "+location+", stopId = "+stopId+"]";
    }
}
