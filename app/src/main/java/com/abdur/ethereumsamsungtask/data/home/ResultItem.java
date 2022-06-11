package com.abdur.ethereumsamsungtask.data.home;

import com.google.gson.annotations.SerializedName;

public class ResultItem {

    @SerializedName("blockHash")
    private String blockHash;

    @SerializedName("contractAddress")
    private String contractAddress;

    @SerializedName("transactionIndex")
    private String transactionIndex;

    @SerializedName("confirmations")
    private String confirmations;

    @SerializedName("nonce")
    private String nonce;

    @SerializedName("timeStamp")
    private String timeStamp;

    @SerializedName("input")
    private String input;

    @SerializedName("gasUsed")
    private String gasUsed;

    @SerializedName("isError")
    private String isError;

    @SerializedName("txreceipt_status")
    private String txreceiptStatus;

    @SerializedName("blockNumber")
    private String blockNumber;

    @SerializedName("gas")
    private String gas;

    @SerializedName("cumulativeGasUsed")
    private String cumulativeGasUsed;

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("value")
    private String value;

    @SerializedName("hash")
    private String hash;

    @SerializedName("gasPrice")
    private String gasPrice;

    public String getBlockHash() {
        return blockHash;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public String getConfirmations() {
        return confirmations;
    }

    public String getNonce() {
        return nonce;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getInput() {
        return input;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public String getIsError() {
        return isError;
    }

    public String getTxreceiptStatus() {
        return txreceiptStatus;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public String getGas() {
        return gas;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public String getHash() {
        return hash;
    }

    public String getGasPrice() {
        return gasPrice;
    }
}