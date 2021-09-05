package com.example.verifyrepresentative.web;

public class ResponseDto<T> {


    private Status status;
    private boolean dataFoundFlag;
    private T data;

    public ResponseDto(){

        this.status=new Status();
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDataFoundFlag() {
        return dataFoundFlag;
    }

    public void setDataFoundFlag(boolean dataFoundFlag) {
        this.dataFoundFlag = dataFoundFlag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
