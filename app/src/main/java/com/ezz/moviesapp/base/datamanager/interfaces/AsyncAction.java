package com.ezz.moviesapp.base.datamanager.interfaces;


import com.ezz.moviesapp.base.exception.UiException;

/**
 * Created by EzzWalid on 9/21/2017.
 */


public interface AsyncAction <T> {
    Object run() throws UiException;
}//end AsyncAction
