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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.primefaces.diamond.util.Globals;
import org.primefaces.diamond.util.Utility;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class Attribute extends GenPenObject implements Cloneable {
    private FileNode fileNode = null;
    private DataType type = DataType.NULL;
    private String value = ""; //Constraint.initialize(type);
    private String example = "" + (int) (Math.random() * 120);
    private boolean required = false;
    private String format = "";
    private String description = "";
    private String minimum = "" + Integer.MIN_VALUE;
    private String maximum = "" + Integer.MAX_VALUE;
    private String pattern = "";


    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws InvalidAttributeValueException {
        //if (Constraint.isValid(type, value)) {
        this.value = value;
        //} else {
        //    throw new InvalidAttributeValueException("Type: " + type.toString() + " has a value of " + value + " which is invalid");
        //}
    }

    public String toString() {
        return getName();
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) throws InvalidAttributeValueException {

            boolean throwEnabled = false;
            if (throwEnabled) {
                throw new InvalidAttributeValueException("Type: " + type.toString() + " has a example of " + value + " which is invalid");
            }

        this.example = example;
    }

    public String toJson() {
        String json = "";
        if( isNumber()) {
            json = "{\n" +
                    " \"id\":\"" + this.getId() + "\",\n" +
                    " \"name\":\"" + this.getName() + "\",\n" +
                    " \"example\":\"" + this.getExample() + "\",\n" +
                    " \"required\":\"" + this.isRequired() + "\",\n" +
                    " \"description\":\"" + this.getDescription() + "\",\n" +
                    " \"minimum\":\"" + this.getMinimum() + "\",\n" +
                    " \"maximum\":\"" + this.getMaximum() + "\",\n" +
                    " \"pattern\":\"" + this.getPattern() + "\",\n" +
                    " \"value\":\"" + this.getValue() + "\",\n" +
                    " \"type\":\"" + Utility.getSwaggerType(this.getType()) + "\",\n" +
                    " \"format\":\"" + Utility.getSwaggerFormatForType(this.getType()) + "\"\n" +
                    "}\n";
        } else {
            json = "{\n" +
                    " \"id\":\"" + this.getId() + "\",\n" +
                    " \"name\":\"" + this.getName() + "\",\n" +
                    " \"example\":\"" + this.getExample() + "\",\n" +
                    " \"required\":\"" + this.isRequired() + "\",\n" +
                    " \"description\":\"" + this.getDescription() + "\",\n" +
                    " \"pattern\":\"" + this.getPattern() + "\",\n" +
                    " \"value\":\"" + this.getValue() + "\",\n" +
                    " \"type\":\"" + Utility.getSwaggerType(this.getType()) + "\",\n" +
                    " \"format\":\"" + Utility.getSwaggerFormatForType(this.getType()) + "\"\n" +
                    "}\n";

        }
        return json;
    }

    public void fromJson() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileNode.getFile())));
            System.out.println(fileNode.getFile());
            String buf = "";
            String contents = "";
            while ((buf = reader.readLine()) != null) {
                contents += buf + "\n";
            }
            Gson gson = new Gson();
            String json = contents;

            try {
                String jsonString = contents;
                JsonParser jsonParser = new JsonParser();

                JsonElement jsonElement = jsonParser.parse(jsonString);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String name = jsonObject.get("name").getAsString();
                String format = "";
                try {
                    format = jsonObject.get("format").getAsString();
                } catch (Exception ex) {
                }
                boolean required = false;
                try {
                    required = jsonObject.get("required").getAsBoolean();
                } catch (Exception ex) {
                }
                String example = "";
                try {
                    example = jsonObject.get("example").getAsString();
                } catch (Exception ex) {
                }
                String pattern = "";
                try {
                    pattern = jsonObject.get("pattern").getAsString();
                } catch (Exception ex) {
                }
                String value = "";
                try {
                    value = jsonObject.get("value").getAsString();
                } catch (Exception ex) {
                }
                String max = "";
                try {
                    max = jsonObject.get("maximum").getAsString();
                } catch (Exception ex) {
                }
                String min = "";
                try {
                    min = jsonObject.get("minimum").getAsString();
                } catch (Exception ex) {
                }
                String type = "";
                try {
                    type = jsonObject.get("type").getAsString();
                } catch (Exception ex) {
                }
                int id = 0;
                try {
                    id = jsonObject.get("id").getAsInt();
                } catch (Exception ex) {
                }
                String description = "";
                try {
                    description = jsonObject.get("description").getAsString();
                } catch (Exception ex) {
                }

                setName(name);
                setFileNode(fileNode);
                setExample(example);
                setRequired(required);
                setValue(value);
                setMaximum(max);
                setMinimum(min);
                setDescription(description);
                setType(Utility.getSwaggerType(type));
                setId(id);
                setParent("Model");
                setFormat(format);
                setPattern(pattern);
                setUuid(UUID.randomUUID());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("cannot load attribute:" + ex.getMessage());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    public static Attribute fromBase(FileNode node, BaseAttribute base) {
        Attribute attribute = new Attribute();
        attribute.setId(Math.abs(attribute.getId()));
        attribute.setName(Utility.createCompatibleAttributeName(base.getName()));
        attribute.setPattern("");
        attribute.setRequired(false);
        attribute.setDescription("This attribute is a variable named:" + attribute.getName());
        DataType type = Utility.getSwaggerType(base.getType().getSimpleName());
        attribute.setType(type);
        attribute.setFileNode(node);
        try {
            attribute.setValue(Utility.getDefaultSwaggerValueForPrimitive(base.getType().getSimpleName()));
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
        try {
            attribute.setExample(Utility.getDefaultSwaggerValueForPrimitive(base.getType().getSimpleName()));
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
        return attribute;
    }

    public Attribute clone() {
        Attribute a = new Attribute();
        a.setName(Utility.createCompatibleAttributeName(getName()) + " clone " + Utility.numberToEnglish( Globals.counter++));
        //a.setConstraint(getConstraint());
        try {
            a.setExample(getExample());
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
        try {
            a.setValue(getValue());
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
        }
        a.setDescription(getDescription());
        a.setFileNode(getFileNode());
        a.setPattern(getPattern());
        if (getType().equals(DataType.STRING)) {
            a.setMinimum("");
            a.setMaximum("");
        } else if (getType().equals(DataType.SHORT)) {
            a.setMinimum(getMinimum());
            a.setMaximum(getMaximum());
        } else if (getType().equals(DataType.LONG)) {
            a.setMinimum(getMinimum());
            a.setMaximum(getMaximum());
        } else if (getType().equals(DataType.BYTE)) {
            a.setMinimum(getMinimum());
            a.setMaximum(getMaximum());
        }
        a.setRequired(isRequired());
        a.setFormat(getFormat());
        a.setType(getType());
        a.setId(getId()); // TODO Not sure
        return a;
    }

    /*
        User:
      x-swagger-router-model: io.swagger.uec.model.User
      properties:
        id:
          type: integer
          format: int64
          example: 10
        username:
          type: string
          example: theUser
        firstName:
          type: string
          example: John
        lastName:
          type: string
          example: James
        email:
          type: string
          example: john@email.com
        password:
          type: string
          example: 123456789abC
        phone:
          type: string
          example: 14168785282
        userStatus:
          type: integer
          format: int32
          example: 1
          description: User Status
      xml:
        name: user
      type: object
     */
    public boolean isNumber(){
        return type == DataType.BIG_DECIMAL || type ==DataType.BYTE || type == DataType.LONG || type ==DataType.INTEGER || type == DataType.DOUBLE || type == DataType.FLOAT;
    }

    public boolean isDate() {
        return type == DataType.DATE;
    }

    public boolean isString() {
        return type == DataType.STRING;
    }

    public boolean isBoolean() {
        return type == DataType.BOOLEAN;
    }

    public String getSchema() {
        String schema = "";
        schema =        "        " + Utility.convertPresentableToLowerCamelCase(getName()) + ":\n" +
                        "          format: " + Utility.getSwaggerFormatForType(getType()) + "\n" +
                        "          type: " + Utility.getSwaggerType(getType()) + "\n" +
                        "          example: " + getExample() + "\n" +
                        "          description: " + getDescription() + "\n";
        if( isNumber() && (getMinimum() != null && !getMinimum().isEmpty())  ){
            schema +=   "          minimum: " + getMinimum() + "\n";
        }
        boolean tmax = false;
        boolean tmin = false;
        Long l1 = null;
        Long l2 = null;

        try {
            l1 = Long.parseLong(getMaximum());
        } catch(Exception ex) { }
        try {
            l2 = Long.parseLong(getMinimum());
        } catch(Exception ex) { }
        if( l1 != null ){
            tmax = true;
        }
        if( l2 != null ){
            tmin = true;
        }
        if( tmin && tmax ){
            if( l2 < l1 ){

            } else {
                tmin = false;
                tmax = false;
            }
        }
        if( isNumber() && (getMaximum() != null && !getMaximum().isEmpty() ) && tmax ){
            schema +=   "          maximum: " + getMaximum() + "\n";
        }
        if( (getValue() != null )  ){
            //schema +=   "          default: " + getValue() + "\n";
        }

        return schema;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        //this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public FileNode getFileNode() {
        return fileNode;
    }

    public void setFileNode(FileNode fileNode) {
        this.fileNode = fileNode;
    }

    public String getFormat() {
        if( format == null || format.isEmpty() ){
            format = Utility.getSwaggerFormatForType(type);
        }
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
