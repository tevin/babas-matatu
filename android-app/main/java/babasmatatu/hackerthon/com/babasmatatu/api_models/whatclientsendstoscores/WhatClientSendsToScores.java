package babasmatatu.hackerthon.com.babasmatatu.api_models.whatclientsendstoscores;

/**
 * Created by munene on 4/7/2018.
 */
public class WhatClientSendsToScores
{
    private String id;

    private Journey journey;

    private String user;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Journey getJourney ()
    {
        return journey;
    }

    public void setJourney (Journey journey)
    {
        this.journey = journey;
    }

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", journey = "+journey+", user = "+user+"]";
    }
}

