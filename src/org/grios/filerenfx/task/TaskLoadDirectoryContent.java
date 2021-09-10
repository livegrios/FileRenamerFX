/*
 *  Project:    FileRenamerFX
 *  Artifact:   TaskLoadDirectoryContent.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.task;

import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import org.grios.filerenfx.gui.Main;
import org.grios.filerenfx.gui.components.TableAdapterFile;
import org.grios.filerenfx.model.FileDescriptor;

/**
 *  A class for concurrent processing intended to
 *  load a directory contents showing a progressbar until
 *  the files are readed and added to be presented in the
 *  App GUI.
 *  @author LiveGrios
 */
public class TaskLoadDirectoryContent extends Task<Void>
{
    Main app;
    File[] files;
    boolean onlyFiles;
    TableView<FileDescriptor> tv;
    ObservableList<FileDescriptor> listFiles;
    
    public TaskLoadDirectoryContent(Main app, TableView<FileDescriptor> tv, File[] files, boolean onlyFiles)
    {
        this.app = app;
        this.files = files;
        this.tv = tv;
    }
    
    public void doBefore()
    {
        app.updateProgressInfo("", 0);
        app.setPanelProgressVisible(true);
    }

    @Override
    protected Void call() throws Exception
    {        
        int counter = -1;
        double n = (double) files.length;
        listFiles = FXCollections.observableArrayList();
        if (files != null)
        {
            for (File f : files)
            {
                if ((f.isFile() && onlyFiles) || !onlyFiles)
                    listFiles.add(new FileDescriptor(f));
                app.updateProgressInfo("Adding file " + f.getName(), (++counter)/n);
            }
        }
        
        return null;
    }
    
    @Override
    public void done()
    {
        super.done();
        try
        {            
            FXUtilities.runAndWait(() ->
            {
                app.setPanelProgressVisible(false);
                app.getTableViewFilesOriginal().getItems().clear();
                TableAdapterFile.adapt(tv, listFiles);
            });
        } 
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }
    
}
