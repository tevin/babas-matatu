package babasmatatu.hackerthon.com.babasmatatu.api_models.whatclientsendstoscores;

/**
 * Created by munene on 4/7/2018.
 */
public class Legs
{
    private String highestLoudness;

    private String distance;

    private String start;

    private String description;

    private String endStopId;

    private String timeTaken;

    private String startStopId;

    private Waypoints[] waypoints;

    private Steps[] steps;

    private String wimtLineID;

    private String end;

    private String legType;

    public String getHighestLoudness ()
    {
        return highestLoudness;
    }

    public void setHighestLoudness (String highestLoudness)
    {
        this.highestLoudness = highestLoudness;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getEndStopId ()
    {
        return endStopId;
    }

    public void setEndStopId (String endStopId)
    {
        this.endStopId = endStopId;
    }

    public String getTimeTaken ()
    {
        return timeTaken;
    }

    public void setTimeTaken (String timeTaken)
    {
        this.timeTaken = timeTaken;
    }

    public String getStartStopId ()
    {
        return startStopId;
    }

    public void setStartStopId (String startStopId)
    {
        this.startStopId = startStopId;
    }

    public Waypoints[] getWaypoints ()
    {
        return waypoints;
    }

    public void setWaypoints (Waypoints[] waypoints)
    {
        this.waypoints = waypoints;
    }

    public Steps[] getSteps ()
    {
        return steps;
    }

    public void setSteps (Steps[] steps)
    {
        this.steps = steps;
    }

    public String getWimtLineID ()
    {
        return wimtLineID;
    }

    public void setWimtLineID (String wimtLineID)
    {
        this.wimtLineID = wimtLineID;
    }

    public String getEnd ()
    {
        return end;
    }

    public void setEnd (String end)
    {
        this.end = end;
    }

    public String getLegType ()
    {
        return legType;
    }

    public void setLegType (String legType)
    {
        this.legType = legType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [highestLoudness = "+highestLoudness+", distance = "+distance+", start = "+start+", description = "+description+", endStopId = "+endStopId+", timeTaken = "+timeTaken+", startStopId = "+startStopId+", waypoints = "+waypoints+", steps = "+steps+", wimtLineID = "+wimtLineID+", end = "+end+", legType = "+legType+"]";
    }
}
