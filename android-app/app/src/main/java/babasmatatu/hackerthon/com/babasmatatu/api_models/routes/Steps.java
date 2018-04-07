package babasmatatu.hackerthon.com.babasmatatu.api_models.routes;

/**
 * Created by munene on 4/7/2018.
 */
public class Steps
{
    private String distance;

    private String instructions;

    private String start;

    private String line;

    private String waypoints;

    private String type;

    private String end;

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getInstructions ()
    {
        return instructions;
    }

    public void setInstructions (String instructions)
    {
        this.instructions = instructions;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getLine ()
    {
        return line;
    }

    public void setLine (String line)
    {
        this.line = line;
    }

    public String getWaypoints ()
    {
        return waypoints;
    }

    public void setWaypoints (String waypoints)
    {
        this.waypoints = waypoints;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getEnd ()
    {
        return end;
    }

    public void setEnd (String end)
    {
        this.end = end;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [distance = "+distance+", instructions = "+instructions+", start = "+start+", line = "+line+", waypoints = "+waypoints+", type = "+type+", end = "+end+"]";
    }
}
