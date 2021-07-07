package com.hmmRahulDev.planmyshow.responses;

import com.google.gson.annotations.SerializedName;
import com.hmmRahulDev.planmyshow.models.TVShowDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
