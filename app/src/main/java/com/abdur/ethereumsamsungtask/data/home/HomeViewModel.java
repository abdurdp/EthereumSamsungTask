package com.abdur.ethereumsamsungtask.data.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<BlockChainResponse> apiResponseMutableLiveData;
    private MutableLiveData<BlockChainTransactionResponse> apiTransactionResponseMutableLiveData;
    private final HomeRepository repository;

    private final String ACTION_TRANSACTION = "ACTION_TRANSACTION";
    private final String ACTION_BALANCE = "ACTION_BALANCE";

    public HomeViewModel() {
        repository = HomeRepository.getInstance();
    }

    public void initViewModel(String address, String action) {
        if (action.equals(ACTION_BALANCE)) {
            apiResponseMutableLiveData = repository.getBalance(address);
        } else {
            apiTransactionResponseMutableLiveData = repository.getTransaction(address);
        }
    }

    public LiveData<BlockChainResponse> getBalanceLiveData() {
        return apiResponseMutableLiveData;
    }

    public LiveData<BlockChainTransactionResponse> getTransactionLiveData() {
        return apiTransactionResponseMutableLiveData;
    }
}