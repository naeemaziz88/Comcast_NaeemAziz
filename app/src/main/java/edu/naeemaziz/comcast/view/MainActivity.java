package edu.naeemaziz.comcast.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;


import edu.naeemaziz.comcast.R;
import edu.naeemaziz.comcast.adpater.HighLightsItemAdapter;
import edu.naeemaziz.comcast.model.Higlights;
import edu.naeemaziz.comcast.utilities.Utility;
import edu.naeemaziz.comcast.viewmodel.HighlightViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<Higlights> highlightsList = new ArrayList<>();
    HighlightViewModel highlightsViewModel;
    HighLightsItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if(Utility.isNetwork(this)) {
            highlightsViewModel = new HighlightViewModel(getApplication());
            highlightsViewModel.getHighlightsData().observe(this, highlightsResponse -> {
                if (highlightsResponse != null && highlightsResponse.getHiglights() != null && !highlightsResponse.getHiglights().isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    List<Higlights> list = highlightsResponse.getHiglights();
                    highlightsList.addAll(list);
                    adapter.notifyDataSetChanged();

                }
            });
        }else{
            Utility.showNetworkErrorDialogue(this);
        }
    }

    private void initView() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyler_view);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutmanager);
        adapter = new HighLightsItemAdapter(this, highlightsList, new HighLightsItemAdapter.OnItemClick() {
            @Override
            public void OnClickHighLight(Higlights higlight) {
                Intent intent;
                intent = new Intent(MainActivity.this, ExoPlayerActivity.class);
                intent.putExtra("URL", higlight.getHighlightsUrl());
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Utility.showExitDialogue(this);
    }
}