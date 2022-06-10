package com.abdur.ethereumsamsungtask.data.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<BlockChainResponse> apiResponseMutableLiveData;
    private final HomeRepository repository;

    public HomeViewModel() {
        repository = HomeRepository.getInstance();
    }

    public void initViewModel(String address) {
        apiResponseMutableLiveData = repository.getBalance(address);
    }

    public LiveData<BlockChainResponse> getBalanceLiveData() {
        return apiResponseMutableLiveData;
    }
}