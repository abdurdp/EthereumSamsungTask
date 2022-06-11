package com.abdur.ethereumsamsungtask.data.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockChainTransactionResponse {

    @SerializedName("result")
    private List<ResultItem> result;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;
    private boolean error;

    public List<ResultItem> getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}