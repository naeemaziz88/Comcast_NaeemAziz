package edu.naeemaziz.comcast.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import edu.naeemaziz.comcast.repository.HighlightsRepostitory;
import edu.naeemaziz.comcast.response.HiglightsResponse;

public class HighlightViewModel extends AndroidViewModel {

    HighlightsRepostitory highlightsRepository;
    LiveData<HiglightsResponse> highlightsResponse;

    public HighlightViewModel(Application application) {
        super(application);
        highlightsRepository = new HighlightsRepostitory();
        highlightsResponse = highlightsRepository.getHighlightsResponse();
    }

    public LiveData<HiglightsResponse> getHighlightsData() {
        return highlightsResponse;
    }
}
