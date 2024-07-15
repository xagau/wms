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
import java.sql.SQLException;
import org.primefaces.diamond.domain.Project;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.diamond.Globals;
import org.primefaces.diamond.dao.DatabaseManager;
import org.primefaces.diamond.dao.ProjectDAO;
import org.primefaces.diamond.util.Utility;


@Named
@ApplicationScoped
public class ProjectService {

    List<Project> projectLists;

    @PostConstruct
    public void init() {
        try {
            projectLists = new ArrayList<>();
            Connection connection = DatabaseManager.getConnection();
            ProjectDAO dao = new ProjectDAO(connection);

            if( Globals.mock ){
                projectLists.add(new Project(1000,  "MERN", "default", 34.6, "Lorem ipsum dol...", "endpoint"));
                projectLists.add(new Project(1001,  "JSF", "default", 25, "Lorem ipsum dolor ..", "endpoint"));
                projectLists.add(new Project(1002,   "sarus","default", 50, "incididunt ut labore et d..", "endpoint"));
                projectLists.add(new Project(1003,   "AGUA.SAT","default", 70, "Lorem ipsum dolor sit amet, consectetur adipi", "endpoint"));
                projectLists.add(new Project(1004,   "dev","default", 80, "Lorem ipsum dolor sit amet, consectetur adip...", "endpoint"));
                projectLists.add(new Project(1005,   "frontend","default", 10, "incididunt ut labore et dolore magna a...", "endpoint"));
                return;
            }
            
            projectLists = dao.findAll();

            Utility.closeQuietly(connection, null, null);


        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Project> getProjectLists() {
        
        return new ArrayList<>(projectLists);
    }

    public List<Project> getProjectLists(int size) {

        if (size > projectLists.size()) {
            Random rand = new Random();

            List<Project> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(projectLists.size());
                randomList.add(projectLists.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(projectLists.subList(0, size));
        }

    }

    public List<Project> getClonedProjectLists(int size) {
        List<Project> results = new ArrayList<>();
        List<Project> originals = getProjectLists(size);
        for (Project original : originals) {
            results.add(original.clone());
        }
        return results;
    }
}
