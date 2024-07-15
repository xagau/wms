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
package org.primefaces.diamond.view;

import org.primefaces.PrimeFaces;
import org.primefaces.diamond.domain.*;


import org.primefaces.diamond.service.ProductService;
import org.primefaces.diamond.service.ActionService;
import org.primefaces.diamond.service.ProjectService;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SAAJResult;
import org.primefaces.diamond.dao.ActionDAO;
import org.primefaces.diamond.dao.BuildDAO;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.DeploymentDAO;
import org.primefaces.diamond.dao.NotificationDAO;
import org.primefaces.diamond.dao.ProjectDAO;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.dao.UsageDAO;
import org.primefaces.diamond.service.TimeService;

@Named
@ViewScoped
public class DashboardView implements Serializable {

    /**
     * @return the notifications
     */
    public List<Notification> getNotifications() {
        connection = DatabaseManager.getConnection();
        NotificationDAO notificationDAO = new NotificationDAO(connection);
        try {
            this.notifications = notificationDAO.findByEmail(email);
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;
    }

    /**
     * @param notifications the notifications to set
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    Connection connection = null;
    private List<Product> productsThisWeek;

    private List<Product> productsLastWeek;

    private List<Product> products;

    private List<Action> actionLists;

    private Usage usage = new Usage();
    private List<Build> builds = new ArrayList<>();
    private List<Deployment> deployments = new ArrayList<>();
    private List<Project> projects  = new ArrayList<>();
    private List<Billing> billings  = new ArrayList<>();
    private List<Notification> notifications  = new ArrayList<>();

    private List<Project> selectedProjects;

    private int productWeek = 0;
    private Project selectedProject;
    @Inject
    private ProductService service;
    @Inject
    private ActionService actionService;
    @Inject
    private ProjectService projectService;

    String email = "<none>";
    @PostConstruct
    public void init() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            email = (String)session.getAttribute("userid");
            System.out.println("Email for Session:" + email);
        } catch(Exception ex) { 
            ex.printStackTrace();
        }

        connection = DatabaseManager.getConnection();
        this.productsThisWeek = this.service.getProducts();
        this.productsLastWeek = this.service.getClonedProducts(25);

        this.actionLists = this.actionService.getActionList();
        this.products = this.productsThisWeek;
        this.projects = this.projectService.getProjectLists();
        this.notifications = this.getNotifications();

    }

    private TimeService timeService = new TimeService();

    public List<Product> getProducts() {
        return products;
    }

    public List<Project> getProjects() {
        try {
            connection= DatabaseManager.getConnection();
            ProjectDAO dao = new ProjectDAO(connection);
            System.out.println("Projects:" + email);
            projects = dao.findByOwner(email);
            return projects;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projects;
    }

    public List<Action> getActionLists() {
        
        try {
            connection= DatabaseManager.getConnection();
            SecurityEventDAO dao = new SecurityEventDAO(connection);
            System.out.println("ActionLists:" + email);
            List<SecurityEvent> sec = dao.findByOwner(email);
            for(int i = sec.size() - 1; i >= 0; i--){
                Action a = new Action();
                a.setAction(((SecurityEvent)sec.get(i)).getAction());
                a.setActionDate(new Date(((SecurityEvent)sec.get(i)).getTs().getTime()));
                a.setDevice(((SecurityEvent)sec.get(i)).getDevice());
                a.setOwner(((SecurityEvent)sec.get(i)).getOwner());
                a.setUserIpAddress(((SecurityEvent)sec.get(i)).getIp());
                actionLists.add(a);
            }
            return actionLists;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actionLists;
    }

    public void loadProducts() {
        this.products = this.productWeek == 1 ? this.productsLastWeek : this.productsThisWeek;
    }

    public int getProductWeek() {
        return productWeek;
    }

    public void setProductWeek(int productWeek) {
        this.productWeek = productWeek;
    }


    // project table(CRUD)
    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public List<Project> getSelectedProjects() {
        return selectedProjects;
    }

    public void setSelectedProjects(List<Project> selectedProjects) {
        this.selectedProjects = selectedProjects;
    }

    public void openNew() {
        this.selectedProject = new Project();
    }

    public void saveProject() {
        if (this.selectedProject.getId() == 0) {
            this.selectedProject.setId(this.selectedProjects.size() + 1);
            this.projects.add(this.selectedProject);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Project Added"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Project Updated"));
        }

        PrimeFaces.current().executeScript("PF('manageProjectDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-projects");
    }

    public void deleteProject() {
        this.projects.remove(this.selectedProject);
        this.selectedProject = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Project Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-projects");
    }
    public String getDeleteButtonMessage() {
        if (hasSelectedProjects()) {
            int size = this.selectedProjects.size();
            return size > 1 ? size + " projects selected" : "1 project selected";
        }
        return "Delete";
    }

    public boolean hasSelectedProjects() {
        return this.selectedProjects != null && !this.selectedProjects.isEmpty();
    }

    public void deleteSelectedProjects() {
        this.projects.removeAll(this.selectedProjects);
        this.selectedProjects = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Projects Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-projects");
    }


    public List<Deployment> getDeployments() {
        try {
            connection= DatabaseManager.getConnection();
            DeploymentDAO dao = new DeploymentDAO(connection);
            System.out.println("Deployments:" + email);
            deployments = dao.findByOwner(email);
            return deployments;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deployments;
    }

    public void setDeployments(List<Deployment> deployments) {
        this.deployments = deployments;
    }

    public Usage getUsage() {
        try {
            connection = DatabaseManager.getConnection();
            System.out.println("Usage:" + email);
            UsageDAO dao = new UsageDAO(connection);
            usage = dao.findByAccount(email);
            return usage;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Usage(email,0,0,0,0,0,0);
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public List<Build> getBuilds() {
        try {
            connection= DatabaseManager.getConnection();
            BuildDAO dao = new BuildDAO(connection);
            System.out.println("Builds:" + email);
            builds = dao.findByOwner(email);
            return builds;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    public List<Billing> getBillings() {
        return billings;
    }

    public void setBillings(List<Billing> billings) {
        this.billings = billings;
    }

    /**
     * @return the timeService
     */
    public TimeService getTimeService() {
        return timeService;
    }

    /**
     * @param timeService the timeService to set
     */
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
