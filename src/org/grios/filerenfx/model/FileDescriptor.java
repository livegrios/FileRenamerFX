/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.model;

import java.io.File;

/**
 *
 * @author LiveGrios
 */
public class FileDescriptor
{
    File file;
    String name;
    String extension;
    String newName;
    
    public FileDescriptor(File f)
    {
        int pos = -1;
        this.file = f;
        
        if (f != null)
        {
            if (f.isDirectory())
            {
                extension = "";
                name = f.getName();
            }
            else
            {
                pos = f.getName().lastIndexOf(".");
                if (pos > -1)
                {
                    name = f.getName().substring(0, pos);
                    extension = f.getName().substring(pos + 1, f.getName().length());
                }
                else
                {
                    name = f.getName();
                    extension = "";
                }
            }
        }
        
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getExtension()
    {
        return extension;
    }        

    public String getNewName()
    {
        return newName;
    }

    public void setNewName(String newName)
    {
        this.newName = newName;
    }
    
    
}
