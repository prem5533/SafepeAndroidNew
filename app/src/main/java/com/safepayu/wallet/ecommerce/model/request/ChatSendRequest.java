package com.safepayu.wallet.ecommerce.model.request;

public class ChatSendRequest {

    /**
     * title : Hello
     * message : Whats up??
     * document_attached : image
     */

    private String title;
    private String message;
    private String document_attached;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocument_attached() {
        return document_attached;
    }

    public void setDocument_attached(String document_attached) {
        this.document_attached = document_attached;
    }
}
