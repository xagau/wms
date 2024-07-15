package org.primefaces.diamond.shared;

import java.util.UUID;

public class Response {
    private String response;
    private String recipient;
    private static final long UNCATOGORIZED = -1;
    private long category = UNCATOGORIZED;
    private boolean good = false;
    private boolean bad = false;
    private double score = 0;
    private double penalty = 0;

    private UUID uuid = UUID.randomUUID();
    private UUID requestUUID = UUID.randomUUID();
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


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
        this.score = score;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public UUID getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(UUID requestUUID) {
        this.requestUUID = requestUUID;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
