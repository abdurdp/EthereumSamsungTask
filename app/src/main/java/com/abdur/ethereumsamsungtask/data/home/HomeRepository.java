package com.abdur.ethereumsamsungtask.data.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.abdur.ethereumsamsungtask.BuildConfig;
import com.abdur.ethereumsamsungtask.data.source.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomeRepository {
    private static HomeRepository repository;
    private static HomeAPI homeAPI;

    public HomeRepository() {
        homeAPI = APIClient.createService(HomeAPI.class);
    }

    public static HomeRepository getInstance() {
        if (repository == null) {
            repository = new HomeRepository();
        }

        return repository;
    }

    public MutableLiveData<BlockChainResponse> getBalance(String address) {
        MutableLiveData<BlockChainResponse> data = new MutableLiveData<>();
        homeAPI.getBalance("account", "balance", address, "latest", BuildConfig.API_KEY).enqueue(new Callback<BlockChainResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlockChainResponse> call,
                                   @NonNull Response<BlockChainResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    BlockChainResponse apiResponse = new BlockChainResponse();
                    apiResponse.setError(true);
                    if (response.body() != null) {
                        apiResponse.setMessage(response.body().getMessage());
                    }
                    data.setValue(apiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BlockChainResponse> call, @NonNull Throwable t) {
                Timber.e(t.getCause());
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<BlockChainTransactionResponse> getTransaction(String address) {
        MutableLiveData<BlockChainTransactionResponse> data = new MutableLiveData<>();
        homeAPI.getTransaction("account", "txlist", address, 0, 40, BuildConfig.API_KEY).enqueue(new Callback<BlockChainTransactionResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlockChainTransactionResponse> call,
                                   @NonNull Response<BlockChainTransactionResponse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    BlockChainTransactionResponse apiResponse = new BlockChainTransactionResponse();
                    apiResponse.setError(true);
                    if (response.body() != null) {
                        apiResponse.setMessage(response.body().getMessage());
                    }
                    data.setValue(apiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BlockChainTransactionResponse> call, @NonNull Throwable t) {
                Timber.e(t.getCause());
                data.setValue(null);
            }
        });
        return data;
    }
}
