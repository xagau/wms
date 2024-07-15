/*
   Copyright 2022-2023 AGUA.SAT.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.diamond.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Action implements Serializable {
    
    private int id;

    private Date actionDate = new Date();

    private String device;

    private String userIpAddress;

    private String action;
    
    private String owner;
    
    public Action(){
        
    }

    public Action(int id,   String device, String userIpAddress, String action, String owner) {
        this.id = id;
        this.actionDate = new Date(System.currentTimeMillis());
        this.device = device;
        this.userIpAddress = userIpAddress;
        this.action = action; 
        this.owner = owner;
    }
    
    public Action(int id,   String device, String userIpAddress, String action, String owner, Date actionDate) {
        this.id = id;
        this.actionDate = actionDate;
        this.device = device;
        this.userIpAddress = userIpAddress;
        this.action = action; 
        this.owner = owner;
        
    }

    public Action clone() {
        return new Action(getId(),  getDevice(), getUserIpAddress(), getAction(), getOwner(), getActionDate() );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUserIpAddress() {
        return userIpAddress;
    }

    public void setUserIpAddress(String userIpAddress) {
        this.userIpAddress = userIpAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

  
}