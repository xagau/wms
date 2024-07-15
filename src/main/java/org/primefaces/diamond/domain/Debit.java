package org.primefaces.diamond.domain;

import java.sql.Timestamp;

public class Debit {
    private int id = 0;
    private String owner = "";
    private Double amount = 0.0;
    private Timestamp ts = new Timestamp(0);
    private String memo = "";

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) { // throws IllegalAmountException
	if( amount >=0 || Double.isNaN(amount) ) { 
            System.err.println("Dedit cannot be > or equal to 0 or NaN");
            //throw new IllegalAmountException("Debit cannot be > or equal to 0 or NaN");
	}
        this.amount = amount;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
