package com.ezz.moviesapp.base.view;

import android.app.Activity;

import com.ezz.moviesapp.baseListners.Request_fail_dialog_action_listener;


/**
 * Created by prins on 5/20/2017.
 */
public interface BaseView {
    void showLoading(int requestCode);
    void hideLoading(int requestCode);
    void showError(String error);
    void showError(int errorMsg);
    void handleConnectionError(Request_fail_dialog_action_listener listener);
    Activity getRunningActivity();
}
