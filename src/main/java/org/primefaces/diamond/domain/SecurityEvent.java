package org.primefaces.diamond.domain;


import java.sql.Timestamp;
import java.util.Date;

public class SecurityEvent {
    private String owner;
    private String ip;
    private String countryCode;
    private String action;
    private Timestamp ts = new Timestamp(System.currentTimeMillis());
    private String device;

    public SecurityEvent(String owner, String ip, String countryCode, String action, Timestamp ts, String device){
        this.owner = owner;
        this.ip = ip;
        this.countryCode = countryCode;
        this.action = action;
        this.ts = ts;
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
