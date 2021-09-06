/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui.components.action;

import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.grios.filerenfx.core.parse.Action;
import org.grios.filerenfx.core.parse.ActionConstant;
import org.grios.filerenfx.core.parse.ActionCounter;
import org.grios.filerenfx.core.parse.ActionExtract;

/**
 *
 * @author LiveGrios
 */
public class PaneAction
{
    public static final String COLOR_EXTRACT_HEX = "#BBDEFB";
    public static final String COLOR_COUNT_HEX = "#CCFF90";
    public static final String COLOR_CONSTANT_HEX = "#FFCC80";
    
    public static final Color COLOR_EXTRACT = Color.web(COLOR_EXTRACT_HEX);
    public static final Color COLOR_COUNT = Color.web(COLOR_COUNT_HEX);
    public static final Color COLOR_CONSTANT = Color.web(COLOR_CONSTANT_HEX);
    
    @FXML VBox vboxAction;
    @FXML SplitMenuButton btnActionType;
    @FXML HBox hboxExtract;
    @FXML HBox hboxCounter;
    @FXML HBox hboxConstant;
    
    @FXML MenuItem mnuiExtract;
    @FXML MenuItem mnuiCounter;
    @FXML MenuItem mnuiConstant;
    
    FXMLLoader fxmll;
    
    HBox hboxActions;
    
    Action action;
    
    List<PaneAction> paneActions;
    
    public PaneAction(HBox hboxActions, List<PaneAction> paneActions)
    {
        fxmll = new FXMLLoader(PaneAction.class.getResource("pane_action.fxml"));
        fxmll.setController(this);
        this.hboxActions = hboxActions;
        this.paneActions = paneActions;
        paneActions.add(this);
    }
    
    public void initComponents() throws Exception
    {
        fxmll.load();
        
        btnActionType.setOnAction(evt->{removeThisAction();});
        mnuiConstant.setOnAction(evt->{setConstantType();});
        mnuiCounter.setOnAction(evt->{setCounterType();});
        mnuiExtract.setOnAction(evt->{setExtractType();});
        
        setExtractType();
    }
    
    public VBox getRoot()
    {
        return vboxAction;
    }
    
    public Action getAction()
    {
        return action;
    }
    
    public void setExtractType()
    {
        action = new ActionExtract();
       
        vboxAction.getChildren().remove(hboxCounter);
        vboxAction.getChildren().remove(hboxConstant);
        if (!vboxAction.getChildren().contains(hboxExtract))
            vboxAction.getChildren().add(hboxExtract);
        
        btnActionType.setStyle("-fx-base: " + COLOR_EXTRACT_HEX);
        vboxAction.setStyle("-fx-background-color: " + COLOR_EXTRACT_HEX);
        btnActionType.setText("Extract Filename");
    }
    
    public void setCounterType()
    {
        action = new ActionCounter();
        
        vboxAction.getChildren().remove(hboxExtract);
        vboxAction.getChildren().remove(hboxConstant);
        if (!vboxAction.getChildren().contains(hboxCounter))
            vboxAction.getChildren().add(hboxCounter);
        btnActionType.setStyle("-fx-base: " + COLOR_COUNT_HEX);
        vboxAction.setStyle("-fx-background-color: " + COLOR_COUNT_HEX);
        btnActionType.setText("Counter");
    }
    
    public void setConstantType()
    {
        action = new ActionConstant();
        
        vboxAction.getChildren().remove(hboxExtract);
        vboxAction.getChildren().remove(hboxCounter);
        if (!vboxAction.getChildren().contains(hboxConstant))
            vboxAction.getChildren().add(hboxConstant);
        btnActionType.setStyle("-fx-base: " + COLOR_CONSTANT_HEX);
        vboxAction.setStyle("-fx-background-color: " + COLOR_CONSTANT_HEX);
        btnActionType.setText("Constant");
    }
    
    public void removeThisAction()
    {
        if (hboxActions != null)
            hboxActions.getChildren().remove(vboxAction);
        if (paneActions != null)
            paneActions.remove(this);
    }
}
