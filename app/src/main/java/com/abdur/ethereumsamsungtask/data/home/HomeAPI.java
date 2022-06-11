package com.abdur.ethereumsamsungtask.data.home;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeAPI {
    @GET("api?")
    Call<BlockChainResponse> getBalance(@Query("module") String module,
                                        @Query("action") String action,
                                        @Query("address") String address,
                                        @Query("tag") String tag,
                                        @Query("apikey") String apikey
    );

    @GET("api?")
    Call<BlockChainTransactionResponse> getTransaction(@Query("module") String module,
                                                       @Query("action") String action,
                                                       @Query("address") String address,
                                                       @Query("startblock") int startblock,
                                                       @Query("endblock") int endblock,
                                                       @Query("apikey") String apikey
    );
}
