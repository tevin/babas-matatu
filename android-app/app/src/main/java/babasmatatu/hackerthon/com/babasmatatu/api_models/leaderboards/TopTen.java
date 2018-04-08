package babasmatatu.hackerthon.com.babasmatatu.api_models.leaderboards;

/**
 * Created by munene on 4/8/2018.
 */
public class TopTen
{
    private String name;

    private String score;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", score = "+score+"]";
    }
}