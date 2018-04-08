package babasmatatu.hackerthon.com.babasmatatu.helpers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import babasmatatu.hackerthon.com.babasmatatu.R;
import babasmatatu.hackerthon.com.babasmatatu.api_models.leaderboards.Leaderboard;
import babasmatatu.hackerthon.com.babasmatatu.api_models.leaderboards.TopTen;
import butterknife.BindView;
import butterknife.ButterKnife;

import static babasmatatu.hackerthon.com.babasmatatu.helpers.ProgressExtension.setDialog;
import static babasmatatu.hackerthon.com.babasmatatu.helpers.StringExtension.isNullOrWhitespace;

/**
 * Created by munene on 4/8/2018.
 */
public class LeaderboardActivity extends AppCompatActivity {
    @BindView(R.id.leaderBoardRecyclerView)
    RecyclerView leaderBoardRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);

        String url = "https://babas-matatu.herokuapp.com/leaderboard/emissions";
        setDialog(this, true, "Please wait...");

        final StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            setDialog(LeaderboardActivity.this, false, null);

                            if (!isNullOrWhitespace(response)) {
                                ObjectMapper mapper = new ObjectMapper();
                                try {
                                    List<TopTen> topTens = mapper.readValue(response, new TypeReference<List<TopTen>>() {
                                    });

                                    LeaderboardAdapter mAdapter = new LeaderboardAdapter(topTens);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    leaderBoardRecyclerView.setLayoutManager(mLayoutManager);
                                    leaderBoardRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                    leaderBoardRecyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            //Do nothing
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setDialog(LeaderboardActivity.this, false, null);
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleyProvider.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

