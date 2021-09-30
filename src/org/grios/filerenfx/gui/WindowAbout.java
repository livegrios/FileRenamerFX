/*
 *  Project:    FileRenamerFX
 *  Artifact:   WindowAbout.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.gui;

import java.io.File;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *  This class is the JavaFX Stage that will display the
 *  about information.
 *  @author LiveGrios
 */
public class WindowAbout extends Stage
{    
    @FXML WebView webView;
    
    FXMLLoader fxmll;
    
    Scene scene;
    
    Main app;
    
    public WindowAbout(Main app)
    {
        super();
        setTitle("About FileRenamerFX...");        
        initStyle(StageStyle.UTILITY);
        initOwner(app.getStage());
        setResizable(false);
        getIcons().add(Main.ICON_APP);
        this.app = app;
    }
    
    public void init() throws Exception
    {
        fxmll = new FXMLLoader(getClass().getResource("/org/grios/filerenfx/gui/fxml/about.fxml"));
        fxmll.setController(this);
        fxmll.load();
        scene = new Scene(fxmll.getRoot());
        webView.getEngine().load(new File("html/about.html").toURI().toURL().toString());
        setScene(scene);
        
        scene.setOnKeyReleased(evt->{
            if (evt.getCode() == KeyCode.ESCAPE)
                hide();
        });
        
        focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
        {
            if (newValue != null && !newValue)
                hide();
        });
    }
    
}
