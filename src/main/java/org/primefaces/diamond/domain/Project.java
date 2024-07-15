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

public class Project implements Serializable {
    private int id;
    private String name;
    private String owner;
    private Date creationDate = new Date();
    private double size;
    private String description;
    private String endPoint;

    private Date lastStagingDeployment = new Date();
    public Project() {}
    public Project(int id, String name, String owner, double size, String description, String endpoint) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.creationDate = new Date(System.currentTimeMillis());
        this.size = size;
        this.description = description;
        this.endPoint = endpoint;
    }

    public Project clone() {
        return new Project(getId(), getName(), getOwner(), getSize(), getDescription(), getEndPoint() );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date createDate) {
        this.creationDate = createDate;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastStagingDeployment() {
        return lastStagingDeployment;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setLastStagingDeployment(Date lastStagingDeployment) {
        this.lastStagingDeployment = lastStagingDeployment;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}