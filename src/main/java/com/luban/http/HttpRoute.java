package com.luban.http;

public class HttpRoute {
    private String domain;
    private String prePath;

    public HttpRoute(String domain, String prePath) {
        this.domain = domain;
        this.prePath = prePath;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HttpRoute){
            HttpRoute httpRoute = (HttpRoute) obj;
            return httpRoute.prePath.equals(this.prePath);
        }
        return false;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPrePath() {
        return prePath;
    }

    public void setPrePath(String prePath) {
        this.prePath = prePath;
    }
}
