package babasmatatu.hackerthon.com.babasmatatu.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import babasmatatu.hackerthon.com.babasmatatu.R;
import babasmatatu.hackerthon.com.babasmatatu.api_models.leaderboards.TopTen;

/**
 * Created by munene on 4/8/2018.
 */
public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder>  {
    private List<TopTen> leaderboards;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, points;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            points = (TextView) view.findViewById(R.id.points);
        }
    }


    public LeaderboardAdapter(List<TopTen> leaderboards) {
        this.leaderboards = leaderboards;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TopTen leaderboard = leaderboards.get(position);
        holder.name.setText(leaderboard.getName());
        holder.points.setText(leaderboard.getScore());
    }

    @Override
    public int getItemCount() {
        return leaderboards.size();
    }
}
