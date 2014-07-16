package com.aerials.network;

import com.octo.android.robospice.SpiceManager;

public class SpiceManagerFactory {
    private static SpiceManager spiceManager;

    public static SpiceManager getInstance(){
        if(spiceManager == null){
            spiceManager = new SpiceManager(JsonSpiceService.class);
        }
        return spiceManager;
    }
}
