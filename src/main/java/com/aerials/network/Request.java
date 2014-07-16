package com.aerials.network;

import com.aerials.domain.Wave;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Request<T> extends SpringAndroidSpiceRequest<T> {
    private RequestResponseBean requestResponseBean;
    private AerialsRequestListener requestListener;
    private final Class<T> clazz;

    public Request(Class<T> clazz, RequestResponseBean requestResponseBean, AerialsRequestListener requestListener) {
        super(clazz);
        this.requestResponseBean = requestResponseBean;
        this.requestListener = requestListener;
        this.clazz = clazz;
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
//        return getRestTemplate().postForObject(requestResponseBean.getUrl(), requestResponseBean.getEntity(), clazz);
        return getRestTemplate().getForObject(requestResponseBean.getUrl(), clazz);
    }

    public String createCacheKey() {
        return createCacheKey(requestResponseBean);
    }

    public static String createCacheKey(RequestResponseBean rb) {
        return rb.getUrl() + new String(Hex.encodeHex(DigestUtils.md5(rb.toString())));
    }

    public void doRequest(long durationInMillis) {
        if (!SpiceManagerFactory.getInstance().isStarted()) {
            SpiceManagerFactory.getInstance().start(requestListener.getContext());
        }
        SpiceManagerFactory.getInstance().execute(this, createCacheKey(), durationInMillis, requestListener);
    }

    public static void getRSS(Wave requestResponseBean, AerialsRequestListener<Wave> listener) {
        new Request<Wave>(Wave.class, requestResponseBean, listener).doRequest(DurationInMillis.ONE_HOUR);
    }

}
