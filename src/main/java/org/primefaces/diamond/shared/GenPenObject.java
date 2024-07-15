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


import org.primefaces.diamond.util.UUIDLookup;
import org.primefaces.diamond.util.Utility;

import javax.swing.*;
import java.util.UUID;

public class GenPenObject {
    private int order = 0;
    private String parent = "";
    protected String name = "";
    private String schema = "";
    private String schemaPathDefinition = "";
    private UUID uuid = UUID.randomUUID();
    private int id = (int)uuid.getLeastSignificantBits();
    private ImageIcon icon = null;


    public GenPenObject() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UUIDLookup.put(uuid, this);
            }
        });
    }
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID puuid) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if( UUIDLookup.lookup(puuid) != null ){
                    String msg = "This object " + this + " has a UUID " + uuid.toString() + " which is already stored. Duplicates are not permitted";
                    //System.out.println(msg);
                    System.out.println(msg);
                    //Globals.editor.showMessageDialog(msg);
                } else {
                    UUIDLookup.put(puuid, this);
                    uuid = UUID.fromString(puuid.toString());
                    System.out.println("UUID " + uuid.toString() + " " + getName() + " has been registered successfully to " + this);
                }
            }
        });
    }

    public String getName() {
        if( name == null || name.equalsIgnoreCase("") || name.isEmpty() ){
            name = "GPO" + Utility.shortId();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public String getPathDefinition() {
        return schemaPathDefinition;
    }

    public String toString() {
        return getName();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
