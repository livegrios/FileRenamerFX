/*
 *  Project:    FileRenamerFX
 *  Artifact:   FileDescriptor.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.model;

import java.io.File;

/**
 *  This is a model class who describes the properties of a file such as
 *  its new name, extension and the proper java.io.File object who contains 
 *  a reference to the physical file.
 *  @author LiveGrios
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

    public File getFile()
    {
        return file;
    }
    
    
}
