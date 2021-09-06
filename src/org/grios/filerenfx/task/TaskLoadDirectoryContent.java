/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.task;

import java.io.File;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import org.grios.filerenfx.gui.Main;
import org.grios.filerenfx.gui.components.TableAdapterFile;
import org.grios.filerenfx.model.FileDescriptor;

/**
 *
 * @author LiveGrios
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
