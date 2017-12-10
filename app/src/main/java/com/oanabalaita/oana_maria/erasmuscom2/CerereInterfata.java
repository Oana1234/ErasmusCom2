package com.oanabalaita.oana_maria.erasmuscom2;

import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerRequest;
import com.oanabalaita.oana_maria.erasmuscom2.serverclient.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Oana-Maria on 23/04/2017.
 */

public interface CerereInterfata {

    @POST("Application/v1/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
