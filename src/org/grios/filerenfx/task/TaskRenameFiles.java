/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.task;

import java.io.File;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.grios.filerenfx.core.parse.Action;
import org.grios.filerenfx.core.parse.ActionCounter;
import org.grios.filerenfx.core.parse.Renamer;
import org.grios.filerenfx.gui.Main;
import org.grios.filerenfx.gui.components.action.PaneAction;
import org.grios.filerenfx.model.FileDescriptor;
import static org.grios.filerenfx.task.TaskRenameFilesPreview.locateActionCounterPositions;

/**
 *
 * @author LiveGrios
 */
public class TaskRenameFiles extends Task<Void>
{
    Main app;
    ObservableList<FileDescriptor> files;
    List<PaneAction> paneActions;
            
    Alert alert;


    Renamer rm;
    
    boolean pausedByDialog;
    boolean continueAfterAlert;
    
    public TaskRenameFiles(Main app, ObservableList<FileDescriptor> files, List<PaneAction> paneActions)
    {
        this.app = app;
        this.files = files;
        this.paneActions = paneActions;
        alert = new Alert(Alert.AlertType.NONE);
        alert.initOwner(app.getStage());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        rm = new Renamer();
    }
    
    public void doBefore()
    {
        app.updateProgressInfo("Preparing operations...", 0);
        app.setPanelProgressVisible(true);        
    }
    
    @Override
    protected Void call() throws Exception
    {
        String s = null;
        int k = 0;        
        
        // Check that a valid directory was selected:
        if (files == null || files.isEmpty())
        {
            app.showAlert("No files found.", "Please, select first a directory to load their contents.", Alert.AlertType.WARNING);
            return null;
        }
                
        if (files.get(0).getNewName() == null || files.get(0).getName().trim().isEmpty())
        {
            pausedByDialog = true;
            showConfirmAlert("Action confirm is required.", "Renaming your files whithout a preview could be dangerous. Do you wan to continue with renaming?");
            while(pausedByDialog){}
            if (!continueAfterAlert)
                return null;
                    
            // Check that some actions were defined:
            if (paneActions.size() > 0)
            {
                
                // Check that all actions were previously validated:
                for(PaneAction pa : paneActions)
                {
                    if (!pa.isChecked())
                    {
                        app.showAlert("Renaming operation aborted.", "You must to validate the defined actions before to rename your files.", Alert.AlertType.WARNING);
                        return null;
                    }
                }
                
                app.updateProgressInfo("Starting renaming process...", 0);
                k = 0;
                for (FileDescriptor fd:files)
                {     
                    k++;
                    try
                    {
                        s = computeNewName(fd, k);
                        fd.setNewName(s);
                    } 
                    catch (Exception e)
                    {
                        pausedByDialog = true;
                        showConfirmAlert("Error on File renaming.", "An error occurred while file [" + fd.getFile().getName() + "] was attempt to be renamed. ¿Do you want to continue?");
                        while(pausedByDialog){}
                        if (!continueAfterAlert)
                            return null;
                    }
                    
                    app.updateProgressInfo("Renaming " + fd.getFile().getName()+ "  <<as>>  " + fd.getNewName(), (double)k/files.size());
                    if (!renameFile(fd, k))
                        return null;
                }
            }
            else
            {
                app.showAlert("Renaming process aborted.", "No renaming actions where defined.", Alert.AlertType.WARNING);
                return null;
            }
        }
        else
        {
            k = 0;
            app.updateProgressInfo("Starting renaming process...", 0);
            for (FileDescriptor fd:files)
            {     
                k++;
                app.updateProgressInfo("Renaming " + fd.getFile().getName()+ "  <<as>>  " + fd.getNewName(), (double)k/files.size());
                if (!renameFile(fd, k))
                    return null;                
            }
        }
        return null;
    }
    
    @Override
    public void done()
    {
        super.done();
        app.setPanelProgressVisible(false);
    }
    
    private void showConfirmAlert(String title, String content)
    {
        pausedByDialog = true;
        if (Platform.isFxApplicationThread())
        {
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.alertTypeProperty().set(Alert.AlertType.CONFIRMATION);
            alert.showAndWait();
            continueAfterAlert = alert.getResult() == ButtonType.YES;
            pausedByDialog = false;
        }
        else
        {
            try
            {
                FXUtilities.runAndWait(() ->
                {
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.setTitle(title);
                    alert.setContentText(content);
                    alert.alertTypeProperty().set(Alert.AlertType.CONFIRMATION);
                    alert.showAndWait();
                    continueAfterAlert = alert.getResult() == ButtonType.YES;
                    pausedByDialog = false;
                });
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private String computeNewName(FileDescriptor fd, int fileIndex) throws Exception
    {
        int k = 0;
        int[] pos = null;
        int[] counters = null;
        String newName = null;
        Action[] actions = null;
        
        actions = new Action[paneActions.size()];
        for (PaneAction pa : paneActions)
            actions[k++] = pa.getAction();
        
        pos = locateActionCounterPositions(actions);
        
        if (pos != null)
                counters = new int[pos.length];
        
        if (counters != null && counters.length > 0)
        {
            if (fileIndex == 0)
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
            newName += "." + fd.getExtension();
        
        return newName;
    }


    private boolean renameFile(FileDescriptor fd, int k) 
    {        
        File nf = new File(fd.getFile().getParentFile().getAbsolutePath() + File.separator + fd.getNewName());

        if (nf.exists())
        {
            pausedByDialog = true;
            showConfirmAlert("Duplicate file error.", "A file with name " + nf.getName() + " was found on the selected directory. Do you want to continue renaming the remaining files?");
            while(pausedByDialog){}
            if (!continueAfterAlert)
                return false;
            return true;
        }
        else
        {
            try
            {
                fd.getFile().renameTo(nf);
                return true;
            } 
            catch (Exception e)
            {
                pausedByDialog = true;
                showConfirmAlert("Error on File renaming.", "An error occurred while file [" + fd.getFile().getName() + "] was attempt to be renamed as [" + fd.getNewName() + "]. Do you want to continue renaming the remaining files?");
                while(pausedByDialog){}                
                return continueAfterAlert;
            }

        }
    }
}
