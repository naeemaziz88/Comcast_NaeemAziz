package edu.naeemaziz.comcast.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import edu.naeemaziz.comcast.R;
import edu.naeemaziz.comcast.model.Higlights;


import java.util.ArrayList;

public class HighLightsItemAdapter extends RecyclerView.Adapter<HighLightsItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Higlights> higlightsList;
    private OnItemClick listener;

    public HighLightsItemAdapter(Context ctx, ArrayList<Higlights> list, OnItemClick listener) {
        context = ctx;
        higlightsList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.highlights_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Higlights highlight = higlightsList.get(position);
        holder.leagueName.setText(highlight.getTitle());
        holder.teamAName.setText(highlight.getTeamA());
        holder.teamBName.setText(highlight.getTeamB());
        holder.matchDate.setText(highlight.getData());
        holder.matchVenue.setText("Venue: " + highlight.getLocation());

        Glide.with(context).load(highlight.getTeamAImageUrl()).into(holder.teamOneIcon);
        Glide.with(context).load(highlight.getTeamBImageUrl()).into(holder.teamTwoIcon);

        holder.watchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickHighLight(highlight);
            }
        });
    }

    @Override
    public int getItemCount() {
        return higlightsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView leagueName;
        AppCompatTextView teamAName;
        AppCompatTextView teamBName;
        AppCompatTextView matchDate;
        AppCompatTextView matchVenue;
        ImageView leagueIcon;
        ImageView teamOneIcon;
        ImageView teamTwoIcon;
        AppCompatButton watchBtn;

        public ViewHolder(@NonNull View view) {
            super(view);

            leagueName = view.findViewById(R.id.league_name);
            teamAName = view.findViewById(R.id.team_one_name);
            teamBName = view.findViewById(R.id.team_two_name);
            matchDate = view.findViewById(R.id.date);
            matchVenue = view.findViewById(R.id.venue1);
            leagueIcon = view.findViewById(R.id.league_icon);
            teamOneIcon = view.findViewById(R.id.team_one_ic);
            teamTwoIcon = view.findViewById(R.id.team_two_ic);
            watchBtn = view.findViewById(R.id.watch);
        }
    }

    public interface OnItemClick {
        void OnClickHighLight(Higlights higlight);
    }
}
