package org.primefaces.diamond.shared;

public class BaseAttribute {
    private String name;
    private Class type;

    public String getName() {
        return name;
    }

    public void setName(String attribute) {
        name = attribute;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String toString() {
        return "[" + name + " " + type + "]";
    }
}
