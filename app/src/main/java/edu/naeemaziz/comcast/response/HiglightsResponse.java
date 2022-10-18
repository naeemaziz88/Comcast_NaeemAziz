package edu.naeemaziz.comcast.response;

import com.google.gson.annotations.SerializedName;
import edu.naeemaziz.comcast.model.Higlights;

import java.util.List;

public class HiglightsResponse {

    @SerializedName("highlights")
    private List<Higlights> higlights;

    public List<Higlights> getHiglights() {
        return higlights;
    }

    public void setHiglights(List<Higlights> higlights) {
        this.higlights = higlights;
    }
}
