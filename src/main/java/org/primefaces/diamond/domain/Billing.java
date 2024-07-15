package org.primefaces.diamond.domain;

import java.util.ArrayList;

public class Billing {
    private int id;

    private ArrayList<Debit> debits = new ArrayList<>();
    private ArrayList<Credit> credits = new ArrayList<>();
    private String account = "";
    private boolean frozen = false;
    public double balance(){
        double c = 0;
        double d = 0;
        for(int i=0; i < credits.size(); i++ ){
            c += credits.get(i).getAmount();
        }
        for(int i=0; i < debits.size(); i++ ){
            d += debits.get(i).getAmount();
        }
        return c - d;
        
    }
    public void deposit(Credit c){
        getCredits().add(c);
    }

    public void debit(Debit d){
        getDebits().add(d);
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public ArrayList<Debit> getDebits() {
        return debits;
    }

    public void setDebits(ArrayList<Debit> debits) {
        this.debits = debits;
    }

    public ArrayList<Credit> getCredits() {
        return credits;
    }

    public void setCredits(ArrayList<Credit> credits) {
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
