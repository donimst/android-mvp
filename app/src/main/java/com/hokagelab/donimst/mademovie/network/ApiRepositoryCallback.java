package com.hokagelab.donimst.mademovie.network;

public interface ApiRepositoryCallback<T> {

    void onGetResponse(T response);

    void onGetError();

}
