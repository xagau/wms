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
package org.primefaces.diamond.service;

import java.sql.Connection;
import org.primefaces.diamond.domain.InventoryStatus;
import org.primefaces.diamond.domain.Action;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpSession;
import org.primefaces.diamond.Globals;
import org.primefaces.diamond.dao.ActionDAO;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.SecurityEventDAO;
import org.primefaces.diamond.domain.SecurityEvent;
import org.primefaces.diamond.util.Utility;


@Named
@ApplicationScoped
public class ActionService {

    List<Action> actionLists;
    String email = "";
    
    @PostConstruct
    public void init() {
            try {
                try {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                email = (String)session.getAttribute("userid");
                System.out.println("Email for Session:" + email);
            } catch(Exception ex) { 

            }
           
            if(Globals.mock){
                actionLists.add(new Action(1000,   "Linux, Toronto", "109.28.213.20", "login", "user" ));
                actionLists.add(new Action(1001,   "Linux, Toronto", "109.28.213.20", "dashboard", "user" ));
                actionLists.add(new Action(1002,   "Linux, Toronto", "109.28.213.20", "profile", "user" ));
                actionLists.add(new Action(1003,   "Linux, Toronto", "109.28.213.20", "edit profile", "user" ));
                actionLists.add(new Action(1004,   "Linux, Toronto", "109.28.213.20", "dashboard", "user" ));
                actionLists.add(new Action(1005,   "Linux, Toronto", "109.28.213.20", "list", "user"));
                actionLists.add(new Action(1006,   "Linux, Toronto", "109.28.213.20", "change", "user" ));
                actionLists.add(new Action(1007,   "Linux, Toronto", "109.28.213.20", "ddd", "user" ));
                actionLists.add(new Action(1008,   "Linux, Toronto", "109.28.213.20", "aaa", "user" ));
                actionLists.add(new Action(1009,   "Linux, Toronto", "109.28.213.20", "aaa", "user" ));
            }
        } catch (Exception ex) {
            Logger.getLogger(ActionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Action> getActionList() {
        
        try {
            actionLists = new ArrayList<>();
            Connection connection = DatabaseManager.getConnection();
            SecurityEventDAO dao = new SecurityEventDAO(connection);
            List<SecurityEvent> securityList = dao.findByOwner(email);
            for(int i =0; i < securityList.size(); i++ ){
                SecurityEvent se = securityList.get(i);
                Action a = new Action();
                a.setDevice(se.getDevice());
                a.setOwner(email);
                a.setActionDate(new Date(se.getTs().getTime()));
                a.setAction(se.getAction());
                a.setUserIpAddress(se.getIp());
                actionLists.add(a);
            }
            Utility.closeQuietly(connection, null, null);

            return actionLists;
        } catch (SQLException ex) {
            Logger.getLogger(ActionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>(actionLists);
    }

    public List<Action> getActionList(int size) {

        if (size > actionLists.size()) {
            Random rand = new Random();

            List<Action> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(actionLists.size());
                randomList.add(actionLists.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(actionLists.subList(0, size));
        }

    }

    public List<Action> getClonedActionList(int size) {
        List<Action> results = new ArrayList<>();
        List<Action> originals = getActionList(size);
        for (Action original : originals) {
            results.add(original.clone());
        }
        return results;
    }
}
