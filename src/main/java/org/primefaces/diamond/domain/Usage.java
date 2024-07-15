package org.primefaces.diamond.domain;

import java.io.Serializable;

public class Usage implements Serializable {

    private int id = 0;
    private String account = "";
    private int cpu = 0;
    private int ram = 0;
    private int storage = 0;
    private double price = 0;
    private int minutes = 0;
    private int computeCredits = 0;

    public Usage() {
    }

   
    public Usage(String account, int cc, int minutes, int cpu, int ram, int storage, double price) {
        this.setAccount(account);
        this.setComputeCredits(cc);
        this.setMinutes(minutes);
        this.setCpu(cpu);
        this.setRam(ram);
        this.setStorage(storage);
        this.setPrice(price);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int[] getRam(long s, long e)
    {
        return new int[]{0,0,0,0,0,0,0};
    }

    public int[] getStorage(long s, long e)
    {
        return new int[]{0,0,0,0,0,0,0};
    }

    public int[] getComputeCredits(long s, long e)
    {
        return new int[]{0,0,0,0,0,0,0};
    }
    
    public int[] getCpu(long s, long e)
    {
        return new int[]{0,0,0,0,0,0,0};
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * @return the computeCredits
     */
    public int getComputeCredits() {
        return computeCredits;
    }

    /**
     * @param computeCredits the computeCredits to set
     */
    public void setComputeCredits(int computeCredits) {
        this.computeCredits = computeCredits;
    }
}