package org.primefaces.diamond.util;

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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Globals {
    public static String[] args = null;
    public static int major = 1;
    public static int minor = 128;

    public static HashMap<String, String> um = new HashMap<>();
    public static String language = "java";
    public static String server = "jaxrs-spec";

    public static UUID hc = null;
    public static int counter = 0;
    public static String genTokenEnd = "TomEE embedded started";

    public static Font font = new Font("Tahoma", Font.BOLD, 14);

    public final static int SERVER = 0;
    public final static int CLIENT = 1;

    public static int kind = SERVER; // 0 = server 1 = client

    public static int fieldHeight = 40;

    public static int pauseTime = 100;
    public static int padding = Padding.right;
    public static String USER = "xagau";
    public static String ROOT = "/home/";

    public static String project = "project-zero";

    public static String RESOURCE_ICON_PACK = "/src/main/resources/icons";
    public static String DEBUGGER_PACK = "/src/main/resources/icons/iconpack_2018_1/debugger";
    public static String GENERAL_PACK = "/src/main/resources/icons/iconpack_2018_1/general";
    public static String ACTION_PACK = "/src/main/resources/icons/iconpack_2018_1/actions";
    public static String APP_HOME = ROOT + USER + "/Desktop/git/repo/GenPenDocking";
    public static String JAVA_HOME = "/usr/lib/jvm/java-8-openjdk-amd64/bin";

    public static String PROJECT_HOME = ROOT + USER + "/Desktop/projects";
    public static String OPENAPI_HOME = PROJECT_HOME + "/" + project + "/openapi";
    public static String TOMCAT_HOME = ROOT + USER + "/Desktop/apache-tomcat-9.0.65/webapps";
    public static String RESOURCE_HOME = APP_HOME + "/src/main/resources";

    //public static OpenAI oai = new OpenAI();
    public static int numberOfFields = 10;
    public static String modelContext = "None";
    public static String modelToGenerate = "";
    public static String applicationPurpose = "";
    public static int numberOfModels = 7;
    public static String contextToGenerate = "";
    public static boolean done = false;
    public static String applicationContext = "";

    public static int consoleBuffer = 200;

    static Lock lock = new ReentrantLock();

    public static int timeout = 3000000;

}
