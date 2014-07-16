package com.aerials.network;

import android.content.Context;
import com.aerials.app.R;
import com.aerials.util.MessageToUser;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class AerialsRequestListener<T extends RequestResponseBean> implements RequestListener<T> {

    private Context context;
    private RequestStateListener requestStateListener;

    protected AerialsRequestListener(Context context) {
        this.context = context;
    }

    protected AerialsRequestListener(Context context, RequestStateListener requestStateListener) {
        this.context = context;
        this.requestStateListener = requestStateListener;
    }

    public RequestStateListener getRequestStateListener() {
        return requestStateListener;
    }

    public void setRequestStateListener(RequestStateListener requestStateListener) {
        this.requestStateListener = requestStateListener;
    }

    @Override
    public void onRequestSuccess(T responseBean) {
        if (responseBean != null) {
            gotResponse(responseBean);
            if (requestStateListener != null) {
                requestStateListener.doneRequestWithSuccess();
            }
            if (requestStateListener != null) {
                requestStateListener.doneRequest();
            }
        } else {
            MessageToUser.showMessage(R.string.error_try_again, context);
        }
    }

    public abstract void gotResponse(T responseBean);

    @Override
    public void onRequestFailure(com.octo.android.robospice.persistence.exception.SpiceException e) {
        MessageToUser.showMessage(R.string.error_try_again, context);
        if (requestStateListener != null) {
            requestStateListener.doneRequest();
        }
    }

    public Context getContext(){
        return context;
    }
}
