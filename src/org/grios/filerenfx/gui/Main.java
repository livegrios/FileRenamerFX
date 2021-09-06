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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grios.filerenfx.gui.components.TableAdapterFile;
import org.grios.filerenfx.gui.components.action.PaneAction;
import org.grios.filerenfx.model.FileDescriptor;
import org.grios.filerenfx.task.FXUtilities;
import org.grios.filerenfx.task.TaskRenameFilesPreview;

/**
 *
 * @author LiveGrios
 */
public class Main extends Application
{
    @FXML BorderPane rootPane;
    @FXML HBox hboxActions;    
    @FXML VBox panelProgress;
    
    @FXML TableView<FileDescriptor> tvFilesOriginal;
    @FXML TableView<FileDescriptor> tvFilesRenamed;
    
    @FXML TextField txtSourceDirectory;
    @FXML Button btnLoadDirectory;
    @FXML Button btnAddAction;
    @FXML Button btnPerformPreview;
    
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
    
    public TableView<FileDescriptor> getTableViewFilesOriginal()
    {
        return tvFilesOriginal;
    }
    
    private void initComponents()
    {
        actions = new ArrayList<>();
        
        dc = new DirectoryChooser();
        dc.setTitle("Choose a directory...");
        
        TableAdapterFile.adapt(tvFilesOriginal, null, false);
        TableAdapterFile.adapt(tvFilesRenamed, null, false);
        
        alert = new Alert(Alert.AlertType.NONE);
        alert.initOwner(window);
        
        btnLoadDirectory.setOnAction(evt -> { showDirectoryDialog(); });
        
        btnAddAction.setOnAction(evt -> {addAction();});
        
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
        alert.setTitle(title);
        alert.setContentText(content);
        alert.alertTypeProperty().set(t);
        alert.showAndWait();
    }
    
    public void setPanelProgressVisible(boolean value)
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
    
    private void loadDirectoryContents(String dirPath)
    {
        File f = new File(dirPath);        
        if (f.exists() && f.isDirectory())
        {
            tvFilesRenamed.getItems().clear();
            TableAdapterFile.adapt(this, tvFilesOriginal, f.listFiles(), true);
        }
        else
        {
            showAlert("No valid directory was selected.", "Specified path is not a directory or it not exists in your system file.", Alert.AlertType.WARNING);
        }
    }
    
    private void addAction()
    {
        PaneAction pa = new PaneAction(hboxActions, actions);
        try
        {
            pa.initComponents();
            hboxActions.getChildren().add(pa.getRoot());
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        
    }
    
    private void performRenamingPreview()
    {
        TaskRenameFilesPreview trfp = new TaskRenameFilesPreview(this, tvFilesOriginal.getItems(), actions);
        Thread t = new Thread(trfp);
        
        trfp.doBefore();
        t.start();
    }
}
