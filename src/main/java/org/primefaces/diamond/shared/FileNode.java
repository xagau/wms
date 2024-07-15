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

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.util.UUID;
import java.util.Vector;

public class FileNode extends GenPenObject {

    protected File m_file;
    private int order = Globals.counter++;
    private String type = null;

    public FileNode(File file, int counter)
    {
        super();
        m_file = file;
        this.order = counter;
    }

    public File getFile()
    {
        return m_file;
    }

    public String toString()
    {
        return m_file.getAbsolutePath().length() > 0 ? m_file.getAbsolutePath() :
                "no file " + UUID.randomUUID().toString(); //getPath();
    }

    public boolean hasSubDirs()
    {
        File[] files = listFiles();
        if (files == null)
            return false;
        for (int k=0; k<files.length; k++)
        {
            if (files[k].isDirectory())
                return true;
        }
        return false;
    }

    public int compareTo(FileNode toCompare)
    {
        return  m_file.getAbsolutePath().compareToIgnoreCase(
                toCompare.m_file.getAbsolutePath() );
    }

    protected File[] listFiles()
    {
        if (!m_file.isDirectory()) {
            return null;
        }
        try
        {
            return m_file.listFiles();
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null,
                    "Error reading directory "+m_file.getAbsolutePath(),
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}




