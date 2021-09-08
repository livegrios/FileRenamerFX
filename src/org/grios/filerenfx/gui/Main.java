/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grios.filerenfx.gui.components.action.IPaneActionListener;
import org.grios.filerenfx.gui.components.action.PaneAction;
import org.grios.filerenfx.model.FileDescriptor;
import org.grios.filerenfx.task.FXUtilities;
import org.grios.filerenfx.task.TaskLoadDirectoryContent;
import org.grios.filerenfx.task.TaskRenameFilesPreview;
import org.grios.filerenfx.task.TaskRenameFiles;

/**
 *
 * @author LiveGrios
 */
public class Main extends Application
{
    public static final String COLORHEX_FONT_SUCCESS = "#2e7d32";
    public static final String COLORHEX_FONT_ERROR = "#D32F2F";
    public static final Color COLOR_FONT_SUCCESS = Color.web(COLORHEX_FONT_SUCCESS);
    public static final Color COLOR_FONT_ERROR = Color.web(COLORHEX_FONT_ERROR);
    
    @FXML BorderPane rootPane;
    
    @FXML ScrollPane scpActions;
    @FXML VBox vboxActions;    
    @FXML VBox panelProgress;
    
    @FXML TableView<FileDescriptor> tvFilesOriginal;
    //@FXML TableView<FileDescriptor> tvFilesRenamed;
    
    @FXML TextField txtSourceDirectory;
    @FXML Button btnLoadDirectory;
    @FXML Button btnAddAction;
    @FXML Button btnRemoveAllActions;
    @FXML Button btnCheckAllActions;    
    @FXML Button btnPerformRenaming;
    @FXML Button btnPerformRenamingSelection;
    @FXML Button btnPerformPreview;    
    @FXML Button btnConfig;
    
    @FXML Label lblActionsAdded;
    @FXML Label lblActionsCorrect;
    @FXML Label lblActionsErrorTitle;
    @FXML Label lblActionsError;
    
    @FXML ProgressBar defaultProgressBar;
    @FXML Label lblProgress;
    
    
    
    Stage window;    
    Scene scene;    
    FXMLLoader fxmll;
    
    
    
    FileChooser fc;
    DirectoryChooser dc;
    Alert alert;
    
    
    List<PaneAction> actions;
    
    public Main()
    {
        super();
        
        fxmll = new FXMLLoader(Main.class.getResource("fxml/main.fxml"));
        fxmll.setController(this);
    }
    
    public Stage getStage()
    {
        return window;
    }
    
    public TableView<FileDescriptor> getTableViewFilesOriginal()
    {
        return tvFilesOriginal;
    }
    
    private void initComponents()
    {
        actions = new ArrayList<>();
        
        dc = new DirectoryChooser();
        dc.setTitle("Choose a directory...");
        
        //TableAdapterFile.adapt(tvFilesOriginal, null, false);
        //TableAdapterFile.adapt(tvFilesRenamed, null, false);
        
        alert = new Alert(Alert.AlertType.NONE);
        alert.initOwner(window);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(System.class.getResource("/icons/app/Icon_v.0.1.png").toExternalForm()));
        
        btnLoadDirectory.setOnAction(evt -> { showDirectoryDialog(); });
        
        btnAddAction.setOnAction(evt -> {addAction();});
        btnRemoveAllActions.setOnAction(evt -> {removeAllActions();});
        btnCheckAllActions.setOnAction(evt -> {checkAllActions();});
        
        btnPerformRenaming.setOnAction(evt->{performRenamingSecure();});
        btnPerformPreview.setOnAction(evt->{performRenamingPreview();});
        
        txtSourceDirectory.setOnKeyReleased(evt -> {
            if (evt.getCode() == KeyCode.ENTER)
            {
                if (txtSourceDirectory.getText().trim().isEmpty())
                {
                    showDirectoryDialog();
                }
                else
                    loadDirectoryContents(txtSourceDirectory.getText());
            }
        });
        
        setPanelProgressVisible(false);
    }
    
    private void initStyle()
    {
        try
        {
            File f = new File("style/filerenamerfx_sty01.css");
            scene.getStylesheets().add(f.toURI().toURL().toExternalForm());
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Non-severe exception treated by JGreepFX.");
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        fxmll.load();
        
        scene = new Scene(fxmll.getRoot());
        scene.setFill(Color.rgb(0, 0, 0, 0.2));
        
        initComponents();
        initStyle();
                
        window = primaryStage;
        window.initStyle(StageStyle.UNIFIED);
        window.getIcons().add(new Image(System.class.getResource("/icons/app/Icon_v.0.1.png").toExternalForm()));
        window.setScene(scene);
        window.setTitle("FileRenamerFX");
        window.show();
    }    
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    private void showDirectoryDialog()
    {
        File f = dc.showDialog(window);
        if (f != null)
        {
            txtSourceDirectory.setText(f.getAbsolutePath());
            loadDirectoryContents(f.getAbsolutePath());
        }
    }    
    
    public void showAlert(String title, String content, Alert.AlertType t)
    {
        if (Platform.isFxApplicationThread())
        {
            alert.setTitle(title);
            alert.setContentText(content);
            alert.alertTypeProperty().set(t);
            alert.showAndWait();
        }
        else
        {
            try
            {
                FXUtilities.runAndWait(() ->
                {
                    alert.setTitle(title);
                    alert.setContentText(content);
                    alert.alertTypeProperty().set(t);
                    alert.showAndWait();
                });
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    public void setPanelProgressVisible(boolean value)
    {
        if (Platform.isFxApplicationThread())
        {
            if (value)
            {
                rootPane.setBottom(panelProgress);
            }
            else
            {
                rootPane.setBottom(null);
            }
        }
        else
        {
            try
            {
                FXUtilities.runAndWait(() ->
                {
                    if (value)
                    {
                        rootPane.setBottom(panelProgress);
                    }
                    else
                    {
                        rootPane.setBottom(null);
                    }
                });
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
    
    public void updateProgressInfo(String text, double value)
    {
        if (Platform.isFxApplicationThread())
        {
            lblProgress.setText(text);
            defaultProgressBar.setProgress(value);
        }
        else
        {
            try
            {
                FXUtilities.runAndWait(() ->
                {
                    lblProgress.setText(text);
                    defaultProgressBar.setProgress(value);
                });
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void updateActionsInventory()
    {
        int cc = 0;
        lblActionsAdded.setText("" + vboxActions.getChildren().size());
        for (PaneAction pa : actions)
            if (pa.isChecked())
                cc++;
        lblActionsCorrect.setText("" + cc);
        lblActionsError.setText("" + (vboxActions.getChildren().size() - cc));
        
        if (vboxActions.getChildren().size() - cc == 0)
        {
            lblActionsError.setTextFill(COLOR_FONT_SUCCESS);
            lblActionsErrorTitle.setTextFill(COLOR_FONT_SUCCESS);
        }
        else
        {
            lblActionsError.setTextFill(COLOR_FONT_ERROR);
            lblActionsErrorTitle.setTextFill(COLOR_FONT_ERROR);
        }
    }
    
    private void loadDirectoryContents(String dirPath)
    {
        TaskLoadDirectoryContent tldc = null;
        Thread t = null;
        File f = new File(dirPath);        
        if (f.exists() && f.isDirectory())
        {
            //tvFilesRenamed.getItems().clear();
            //TableAdapterFile.adapt(this, tvFilesOriginal, f.listFiles(), true);
            tldc = new TaskLoadDirectoryContent(this, tvFilesOriginal, f.listFiles(), true);
            t = new Thread(tldc);
            tldc.doBefore();
            t.start();
        }
        else
        {
            showAlert("No valid directory was selected.", "Specified path is not a directory or it not exists in your system file.", Alert.AlertType.WARNING);
        }
    }
    
    private void addAction()
    {
        PaneAction pa = new PaneAction(vboxActions, actions);
        IPaneActionListener ipal = null;
        try
        {
            ipal = new IPaneActionListener()
            {
                @Override
                public void setOnCheckError(String ed)
                {
                    showAlert("Action Check Error", ed, Alert.AlertType.WARNING);
                }

                @Override
                public void setOnActionRemoved(PaneAction pa)
                {
                    updateActionsInventory();
                }

                @Override
                public void setOnCheckProcessFinished()
                {
                    updateActionsInventory();
                }
            };
            pa.initComponents();       
            pa.setActionListener(ipal);
            vboxActions.getChildren().add(pa.getRoot());            
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        updateActionsInventory();
    }
    
    private void performRenamingPreview()
    {
        TaskRenameFilesPreview trfp = new TaskRenameFilesPreview(this, tvFilesOriginal.getItems(), actions);
        Thread t = new Thread(trfp);
             
        if (tvFilesOriginal.getItems() == null || tvFilesOriginal.getItems().size() < 1)
        {
            showAlert("No Files were selected to be renamed.", "No Files were selected to be renamed. Select a different directory.", Alert.AlertType.WARNING);
            return;
        }
        
        if (actions.size() < 1)
        {
            showAlert("No Actions were defined.", "No Actions were defined to be performed.", Alert.AlertType.WARNING);
            return;
        }
        
        checkAllActions();
        for (PaneAction pa : actions)
        {
            if (!pa.isChecked())
            {
                //showAlert("Actions with error.", "Some actions have an error. You must to correct it before to run the preview process.", Alert.AlertType.WARNING);
                return;
            }
        }
        
        trfp.doBefore();
        t.start();
    }
    
    public void performRenamingSecure()
    {
        TaskRenameFiles trfs = new TaskRenameFiles(this, tvFilesOriginal.getItems(), actions);
        Thread t = new Thread(trfs);
        trfs.doBefore();
        t.start();
    }
    
    private void checkAllActions()
    {
        for (PaneAction pa : actions)
            pa.checkAction();
    }
    
    private void removeAllActions()
    {
        vboxActions.getChildren().clear();
        actions.clear();
        updateActionsInventory();
    }
}
