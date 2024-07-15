package org.primefaces.diamond.shared;

import java.io.Console;
import java.util.UUID;

public class Request {
    private String request;
    private static final long UNCATOGORIZED = -1;
    private long category = UNCATOGORIZED;
    private boolean good = false;
    private boolean bad = false;
    private double score = 0;
    private double penalty = 0;
    private String requestor = "";

    private UUID uuid = UUID.randomUUID();
    private UUID responseUUID = UUID.randomUUID();



    public boolean isUncategorized() {
        return category == UNCATOGORIZED;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long cat) {
        category = cat;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }



    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isBad() {
        return bad;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        if( score > 0.999999999999999999 || score < 0.00000000000000001 ) { throw new IllegalArgumentException("Score cannot be higher than or equal to 1 or less than or equal to 0"); }
        this.score = score;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        if( penalty > 0.999999999999999999 || penalty< 0.00000000000000001 ) { throw new IllegalArgumentException("penalty cannot be higher than or equal to 1 or less than or equal to 0"); }
        this.penalty = penalty;
    }

    public UUID getResponseUUID() {
        return responseUUID;
    }

    public void setResponseUUID(UUID responseUUID) {
        this.responseUUID = responseUUID;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
}
