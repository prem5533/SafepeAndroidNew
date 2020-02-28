package com.safepayu.wallet.models.request;

import android.content.Context;

import com.safepayu.wallet.BaseApp;
import com.safepayu.wallet.api.ApiClient;
import com.safepayu.wallet.api.ApiService;
import com.safepayu.wallet.models.response.BaseResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ExceptionLogRequest {

    /**
     * userid : u9811851175
     * title : Nothing
     * description : checkking
     *      * lineno :
     *      * subtitle :
     *      * deviceid :
     */
    private String userid;
    private String title;
    private String description;
    private String lineno;
    private String subtitle;
    private String deviceid;
    private Context context;

    public ExceptionLogRequest(){

    }

    public ExceptionLogRequest(Context context,String userid, String title, String description, String lineno, String subtitle, String deviceid){
        this.context=context;
        this.userid=userid;
        this.title=title;
        this.description=description;
        this.lineno=lineno;
        this.subtitle=subtitle;
        this.deviceid=deviceid;


        addExceptionLog(userid,title,description,lineno,subtitle,deviceid);
    }

    private void addExceptionLog(String userid, String title, String description, String lineno, String subtitle, String deviceid) {
        ExceptionLogRequest request = new ExceptionLogRequest();
        request.setUserid(userid);
        request.setTitle(title);
        request.setDescription(description);
        request.setLineno(lineno);
        request.setSubtitle(subtitle);
        request.setDeviceid(deviceid);
        addExceptionLog(request);
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLineno() {
        return lineno;
    }

    public void setLineno(String lineno) {
        this.lineno = lineno;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    private void addExceptionLog(ExceptionLogRequest exceptionLogRequest){
        ApiService apiService = ApiClient.getClient(context).create(ApiService.class);

        BaseApp.getInstance().getDisposable().add(apiService.addExceptionLog(exceptionLogRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {

                        if (response.getStatus()) {

                        }else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }
}
