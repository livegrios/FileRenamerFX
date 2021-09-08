/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author LiveGrios
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
        webView.getEngine().load(new File("help/about.html").toURI().toURL().toString());
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
