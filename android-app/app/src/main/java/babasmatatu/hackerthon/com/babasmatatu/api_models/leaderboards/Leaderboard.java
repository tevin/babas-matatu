package babasmatatu.hackerthon.com.babasmatatu.api_models.leaderboards;

/**
 * Created by munene on 4/8/2018.
 */
public class Leaderboard
{
    private TopTen[] topTen;

    public TopTen[] getTopTen ()
    {
        return topTen;
    }

    public void setTopTen (TopTen[] topTen)
    {
        this.topTen = topTen;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [topTen = "+topTen+"]";
    }
}
