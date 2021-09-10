/*
 *  Project:    FileRenamerFX
 *  Artifact:   TaskRenameFilesPreview.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.task;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.grios.filerenfx.core.parse.Action;
import org.grios.filerenfx.core.parse.ActionCounter;
import org.grios.filerenfx.core.parse.Renamer;
import org.grios.filerenfx.gui.Main;
import org.grios.filerenfx.gui.components.action.PaneAction;
import org.grios.filerenfx.model.FileDescriptor;

/**
 *  A class for concurrent processing intended to
 *  load a directory contents showing a progressbar until
 *  the new file names are computed added to be presented in the
 *  App GUI.
 *  @author LiveGrios
 */
public class TaskRenameFilesPreview extends Task<Void>
{
    
    Main app;
    List<PaneAction> paneActions;
    ObservableList<FileDescriptor> files;
    Action[] actions;
    int[] counters;
    Renamer rm;

    public TaskRenameFilesPreview(Main app, ObservableList<FileDescriptor> files, List<PaneAction> paneActions)
    {
        this.app = app;
        this.files = files;
        this.paneActions = paneActions;
        rm = new Renamer();
    }
    
    public void doBefore()
    {
        app.updateProgressInfo("", 0);
        app.setPanelProgressVisible(true);
    }
    
    @Override
    protected Void call() throws Exception
    {      
        int[] pos = null;
        int[] counters = null;
        double kfile = 0;
        int k = 0;
        String newName = null;
        
        app.updateProgressInfo("Analizying actions to be performed...", -1);
        
        if (paneActions.size() > 0)
        {
            actions = new Action[paneActions.size()];
            for (PaneAction pa : paneActions)
                actions[k++] = pa.getAction();
            pos = locateActionCounterPositions(actions);
            
            if (pos != null)
                counters = new int[pos.length];
         
            app.updateProgressInfo("Starting file renaming actions...", 0);
            for (FileDescriptor fd : files)
            {
                if (counters != null && counters.length > 0)
                {
                    if (kfile == 0)
                    {
                        for (int i = 0; i < pos.length; i++)
                            counters[i] = ((ActionCounter) actions[pos[i]]).getStart();
                    }
                    else
                    {
                        for (int i = 0; i < pos.length; i++)
                            counters[i] += ((ActionCounter) actions[pos[i]]).getStep();
                    }
                }
                
                newName = rm.apply(fd.getName(), fd.getExtension(), actions, counters);
                if (fd.getExtension() != null && !fd.getExtension().isEmpty())
                    fd.setNewName(newName + "." + fd.getExtension());
                else
                    fd.setNewName(newName);
                //System.out.println(newName);
                
                app.updateProgressInfo("Analizying File [" + fd.getName() + "]...", kfile/files.size());
                
                kfile++;
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
            FXUtilities.runAndWait(()->{app.getTableViewFilesOriginal().getColumns().get(0).setVisible(false);
                                        app.getTableViewFilesOriginal().getColumns().get(0).setVisible(true);
                                        app.setPanelProgressVisible(false);});
            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Non-severe exception.");
        }
    }
    
    public static int[] locateActionCounterPositions(Action[] actions)
    {
        int[] p = null;
        int count = 0;
        int k = 0;
        
        for (int i = 0; i < actions.length; i++)
            if (actions[i] instanceof ActionCounter)
                count ++;
        
        if (count > 0)
        {
            p = new int[count];        
            for (int i = 0; i < actions.length; i++)
                if (actions[i] instanceof ActionCounter)
                    p[k++] = i;
        }
        
        return p;
    }
}
