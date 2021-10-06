package com.example.covidtracker;






import  retrofit2.Call;
import retrofit2.http.GET;

public interface apiCall {
    @GET("4/query?f=geojson&where=1%3D1&outFields=Åldersgrupp2&outFields=Totalt_antal_fall&outFields=Totalt_antal_avlidna&outFields=Totalt_antal_intensivvårdade")

    Call<apiData> getData();
}
