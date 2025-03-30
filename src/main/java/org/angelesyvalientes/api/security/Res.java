package org.angelesyvalientes.api.security;

import lombok.Data;

@Data
public class Res {
    private int status;
    private String message;
    private String url;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}