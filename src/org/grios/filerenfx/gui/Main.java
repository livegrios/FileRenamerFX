/*
 *  Project:    FileRenamerFX
 *  Artifact:   Main.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the main entry point to the project execution.
 */
package org.grios.filerenfx.gui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
 *  This is the main class of the project.
 *  @author LiveGrios
 */
public class Main extends Application
{
    public static final String COLORHEX_FONT_SUCCESS = "#198754";
    public static final String COLORHEX_FONT_ERROR = "#D32F2F";
    public static final Color COLOR_FONT_SUCCESS = Color.web(COLORHEX_FONT_SUCCESS);
    public static final Color COLOR_FONT_ERROR = Color.web(COLORHEX_FONT_ERROR);
    
    public static final Image ICON_APP = new Image(Main.class.getResource("/icons/app/Icon_v.0.1.png").toExternalForm());
    
    @FXML BorderPane rootPane;
    
    @FXML ScrollPane scpActions;
    @FXML VBox vboxActions;  
    @FXML VBox vboxCenter;
    @FXML VBox panelProgress;
    @FXML VBox vboxLeftRenaming;
    @FXML AnchorPane paneFileInfo;
    @FXML AnchorPane pnlFileSpelling;
    
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
    @FXML SplitMenuButton btnConfig;
    
    @FXML Button btnTogglePaneLeft;
    @FXML Button btnTogglePaneRight;
    @FXML Button btnTogglePaneBottom;
    @FXML Button btnCloseDetails;
    
    @FXML MenuItem mnuiAbout;
    
    @FXML Label lblActionsAdded;
    @FXML Label lblActionsCorrect;
    @FXML Label lblActionsErrorTitle;
    @FXML Label lblActionsError;
    
    @FXML WebView wvSpelling;
    @FXML WebView wvFileInfo;
    
    @FXML ProgressBar defaultProgressBar;
    @FXML Label lblProgress;
    
    @FXML Label lblFilesLoaded;
    @FXML Label lblFilesSelected;
    @FXML Label lblJavaRuntime;
    
    
    Stage window;    
    Scene scene;    
    FXMLLoader fxmll;
    
    WindowAbout windowAbout;
    
    FileChooser fc;
    DirectoryChooser dc;
    Alert alert;
    
    
    List<PaneAction> actions;
    
    WebEngine weSpelling;
    WebEngine weFileInfo;
    
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
    
    private void initComponents() throws Exception
    {
        windowAbout = new WindowAbout(this);
        windowAbout.init();
        
        actions = new ArrayList<>();
        
        dc = new DirectoryChooser();
        dc.setTitle("Choose a directory...");
        
        //TableAdapterFile.adapt(tvFilesOriginal, null, false);
        //TableAdapterFile.adapt(tvFilesRenamed, null, false);
        
        alert = new Alert(Alert.AlertType.NONE);
        alert.initOwner(window);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(ICON_APP);
        
        btnLoadDirectory.setOnAction(evt -> { showDirectoryDialog(); });
        
        btnAddAction.setOnAction(evt -> {addAction();});
        btnRemoveAllActions.setOnAction(evt -> {removeAllActions();});
        btnCheckAllActions.setOnAction(evt -> {checkAllActions();});
        
        btnPerformRenaming.setOnAction(evt->{performRenamingSecure(false);});
        btnPerformRenamingSelection.setOnAction(evt->{performRenamingSecure(true);});
        btnPerformPreview.setOnAction(evt->{performRenamingPreview();});
        
        btnConfig.setOnAction(evt->{});
        mnuiAbout.setOnAction(evt->{showWindowAbout();});
        
        btnTogglePaneLeft.setOnAction(evt->{togglePanelActionsVisible();});
        btnTogglePaneRight.setOnAction(evt->{togglePanelFileInfoVisible();});
        btnTogglePaneBottom.setOnAction(evt->{togglePanelSpellVisible();});
        
        btnCloseDetails.setOnAction(evt->{setPanelFileInfoVisible(false);});
        
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
        
        tvFilesOriginal.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvFilesOriginal.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends FileDescriptor> observable, FileDescriptor oldValue, FileDescriptor newValue) ->
        {
            spellFileName(newValue);
            showFileInfo(newValue);
            setFilesSelected(tvFilesOriginal.getSelectionModel().getSelectedItems().size());
        });
        
        tvFilesOriginal.setOnMouseClicked(evt->{
            if (evt.getClickCount() == 2 && !isPanelFileInfoVisible())
            {
                showFileInfo(tvFilesOriginal.getSelectionModel().getSelectedItem());
                setPanelFileInfoVisible(true);
            }
        });
        
        scene.setOnKeyReleased(evt->{
            switch(evt.getCode())
            {
                case F1:
                    showWindowAbout();
                    break;
                case F2: 
                    if (evt.isControlDown() && evt.isShiftDown())
                        removeAllActions();
                    else if (evt.isShiftDown())
                        removeLastAction();
                    else
                        addAction();
                    break;
                case F3:
                    checkAllActions();
                    break;
                case F4:
                    performRenamingPreview();
                    break;
                case F5:
                    performRenamingSecure(false);
                    break;
                case F6:
                    performRenamingSecure(true);
                    break;
            }
        });
        
        initWebViews();
        
        setPanelFileInfoVisible(false);
        setPanelProgressVisible(false);
        
        lblJavaRuntime.setText(System.getProperty("java.version"));
    }
    
    private void initWebViews() throws Exception
    {
        weSpelling = wvSpelling.getEngine();
        weSpelling.load(new File("html/spelling.html").toURI().toURL().toString());
        
        weFileInfo = wvFileInfo.getEngine();
        weFileInfo.load(new File("html/file_info.html").toURI().toURL().toString());
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
        window.initStyle(StageStyle.DECORATED);
        window.getIcons().add(ICON_APP);
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
    
    public void showWindowAbout()
    {
        windowAbout.show();
    }
    
    public void setTotalFilesLoaded(int value)
    {
        if (value < 1)
            lblFilesLoaded.setText("-");
        else
            lblFilesLoaded.setText("" + value);
            
    }
    
    public void setFilesSelected(int value)
    {
        if (value < 1)
            lblFilesSelected.setText("0");
        else
            lblFilesSelected.setText("" + value);
            
    }
    
    public void setPanelProgressVisible(boolean value)
    {
        if (Platform.isFxApplicationThread())
        {
            if (value)
            {
                //rootPane.setBottom(panelProgress);
                vboxCenter.getChildren().add(panelProgress);
            }
            else
            {
                //rootPane.setBottom(null);
                vboxCenter.getChildren().remove(panelProgress);
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
    
    public void setPanelActionsVisible(boolean value)
    {
        if (value)
            rootPane.setLeft(vboxLeftRenaming);
        else
            rootPane.setLeft(null);
    }
    
    public boolean isPanelActionsVisible()
    {
        return rootPane.getLeft() != null;
    }
    
    public void togglePanelActionsVisible()
    {
        setPanelActionsVisible(!isPanelActionsVisible());
    }
    
    public void setPanelFileInfoVisible(boolean value)
    {
        if (value)
        {
            if (rootPane.getRight() == null)
                rootPane.setRight(paneFileInfo);
        }
        else
            rootPane.setRight(null);
    }
    
    public boolean isPanelFileInfoVisible()
    {
        return rootPane.getRight() != null;
    }
    
    public void togglePanelFileInfoVisible()
    {
        setPanelFileInfoVisible(!isPanelFileInfoVisible());
    }
    
    public void setPanelSpellVisible(boolean value)
    {
        if (value)
            vboxCenter.getChildren().add(1, pnlFileSpelling);
        else
            vboxCenter.getChildren().remove(pnlFileSpelling);
    }
    
    public boolean isPanelSpellVisible()
    {
        return vboxCenter.getChildren().contains(pnlFileSpelling);
    }
    
    public void togglePanelSpellVisible()
    {
        setPanelSpellVisible(!isPanelSpellVisible());
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
    
    public void removeLastAction()
    {
        PaneAction pa = null;
        if (actions.size() > 0)
        {
            pa = actions.get(actions.size() - 1);
            actions.remove(pa);
            vboxActions.getChildren().remove(pa.getRoot());
            updateActionsInventory();
        }
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
    
    public void performRenamingSecure(boolean onlySelected)
    {
        TaskRenameFiles trfs = new TaskRenameFiles(this, 
                                                    onlySelected ? tvFilesOriginal.getSelectionModel().getSelectedItems() : tvFilesOriginal.getItems(), 
                                                    actions);
        Thread t = new Thread(trfs);
        trfs.doBefore();
        t.start();
    }
    
    public void spellFileName(FileDescriptor fd)
    {
        if (fd == null)
            weSpelling.executeScript("spell('')");
        else
            weSpelling.executeScript("spell('" + fd.getName() + "')");
    }
    
    public void showFileInfo(FileDescriptor fd)
    {
        
        if (fd == null)
            weFileInfo.executeScript("displayFileInfo(null, null, null, null, null)");
        else //displayFileInfo(fileName, filePath, fullFilePath, fileSize)
        {
            BasicFileAttributes attr;
            
            try
            {
                attr = Files.readAttributes(Paths.get(fd.getFile().toURI()), BasicFileAttributes.class);
                String script = "displayFileInfo('" + fd.getName() + "', " +
                                                     "'" + fd.getFile().getParent().replaceAll("\\\\", "/") + "', " +
                                                     "'" + fd.getFile().getPath().replaceAll("\\\\", "/") + "', " +
                                                     "'" + fd.getExtension() + "', " +
                                                     attr.size() + ")";
                weFileInfo.executeScript(script);
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
