package org.primefaces.diamond.shared;
/** Copyright (c) 2022-2023 genpen.io,
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author xagau
 * @email seanbeecroft@gmail.com
 * phone 1.416.878.5282
 */


import org.primefaces.diamond.util.Globals;
import org.primefaces.diamond.util.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class Model extends GenPenObject implements Cloneable {
    private ArrayList<Model> models = new ArrayList<>();
    private ArrayList<Attribute> attributes = new ArrayList<>();
    private FileNode fileNode = null;

    public static boolean namespace(Model t, Attribute a) {
        for (int i = 0; i < t.attributes.size(); i++) {
            Attribute _a = t.attributes.get(i);
            if (_a.getName().equalsIgnoreCase(a.getName())) {
                return false;
            }
        }
        return true;
    }

    public Attribute getAttribute(int i){
        return attributes.get(i);
    }

    public void add(Model model) throws IllegalOperationException {
        if( model.getFileNode() == null ){
            throw new IllegalOperationException("You cannot add a model to a model with a null file node");
        }
        models.add(model);
    }

    public void add(Attribute attribute) throws IllegalOperationException {
        if( attribute.getFileNode() == null ){
            System.out.println("You cannot add an attribute with a null file node");
            return;
        }
        for(int i = 0; i < attributes.size(); i++ ){
            if( attributes.get(i).getName().equalsIgnoreCase(attribute.getName() ) ) {
                System.out.println("Cannot add " + attribute.getName() + " as it is a duplicate");
                return; //throw new IllegalOperationException("You cannot add an attribute with a null file node");
            }
        }
        if(attributes.contains(attribute)){
            System.out.println("You cannot add a duplicate attribute:" + attribute.getName());
            return;
        }
        String al = attribute.getName().toLowerCase(Locale.ROOT);
        if( al.startsWith("gpo") || al.endsWith("_fk")) {
            System.out.println("You cannot use " + al + " as an attribute name as it is reserved.");
            return;
        }
        attributes.add(attribute);

    }

    public void removeAttribute(int i)
    {
        attributes.remove(i);
    }
    public void removeAttribute(Attribute a)
    {
        attributes.remove(a);
    }

    public int getAttributeSize() {
        return attributes.size();
    }

    public Model clone() {
        Model m = new Model();
        m.setName(getName() + " " + Utility.numberToEnglish(Globals.counter++));
        for (int i = 0; i < models.size(); i++) {
            Model c = models.get(i).clone();
            try {
                m.add(c);
            } catch (IllegalOperationException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < attributes.size(); i++) {
            Attribute c = attributes.get(i).clone();
            try {
                m.add(c);
            } catch(IllegalOperationException ex){
                ex.printStackTrace();
            }
        }
        return m;
    }

    @Override
    public String getSchema() {
        StringBuilder schema = new StringBuilder("    " + Utility.convertPresentableToUpperCamelCase(getName()) + ":\n" +
                "      x-swagger-router-model: ai.genpen.models." + Utility.convertPresentableToUpperCamelCase(getName()) + "\n");
        boolean first = false;
        for (int i = 0; i < attributes.size(); i++) {
            if( attributes.get(i).isRequired()) {
                if(!first){
               schema.append("      required:\n");
                    first = true;
                    schema.append("        - id"); //.append(Utility.convertPresentableToLowerCamelCase(attributes.get(i).getName())).append("\n");
                    schema.append("        - name"); //.append(Utility.convertPresentableToLowerCamelCase(attributes.get(i).getName())).append("\n");
                }
               //schema.append("        - ").append(Utility.convertPresentableToLowerCamelCase(attributes.get(i).getName())).append("\n");
            }
        }
        schema.append("      properties:\n");

        for (int i = 0; i < attributes.size(); i++) {
            schema.append(attributes.get(i).getSchema());
        }
        /*
       xml:
        name: user
       type: object
         */
        schema.append("      xml:\n       name: ").append(Utility.convertPresentableToLowerCamelCase(getName())).append("\n").append("      type: object\n");
        return schema.toString();
    }

    @Override
    public String getPathDefinition() {
        String schema = Utility.loadDefaultPathBase(getName());

        return schema;
    }

    @Override
    public void setName(String name) {
        if( name == null || name.equals("") || name.isEmpty() || name.endsWith("models")){
            return;
        }
        this.name = name;
    }

    public FileNode getFileNode() {
        return fileNode;
    }

    public void setFileNode(FileNode fileNode) {
        this.fileNode = fileNode;
    }
}
