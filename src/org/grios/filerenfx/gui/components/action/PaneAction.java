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
import javafx.scene.control.TextField;
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
    public static final String COLOR_UNCHECKED = "#ECEFF1";
    
    public static final Color COLOR_EXTRACT = Color.web(COLOR_EXTRACT_HEX);
    public static final Color COLOR_COUNT = Color.web(COLOR_COUNT_HEX);
    public static final Color COLOR_CONSTANT = Color.web(COLOR_CONSTANT_HEX);
    
    public static final char CHAR_NUMBER_PATTERN = '0';
    
    @FXML VBox vboxAction;
    @FXML SplitMenuButton btnActionType;
    @FXML HBox hboxExtract;
    @FXML HBox hboxCounter;
    @FXML HBox hboxConstant;
    
    @FXML MenuItem mnuiExtract;
    @FXML MenuItem mnuiCounter;
    @FXML MenuItem mnuiConstant;
    @FXML MenuItem mnuiRemove;
    
    @FXML TextField txtExtractStart;
    @FXML TextField txtExtractTo;
    @FXML TextField txtCounterFormat;
    @FXML TextField txtCounterStart;
    @FXML TextField txtCounterStep;
    @FXML TextField txtConstantText;
    
    FXMLLoader fxmll;
    
    VBox vboxActions;
    
    Action action;
    
    List<PaneAction> paneActions;
    
    IPaneActionListener actionListener;
    
    boolean checked;
    
    public PaneAction(VBox vboxActions, List<PaneAction> paneActions)
    {
        fxmll = new FXMLLoader(PaneAction.class.getResource("pane_action.fxml"));
        fxmll.setController(this);
        this.vboxActions = vboxActions;
        this.paneActions = paneActions;
        paneActions.add(this);
        checked = false;
    }
    
    public void initComponents() throws Exception
    {
        fxmll.load();
        
        btnActionType.setOnAction(evt->{checkAction();});
        mnuiConstant.setOnAction(evt->{setConstantType();});
        mnuiCounter.setOnAction(evt->{setCounterType();});
        mnuiExtract.setOnAction(evt->{setExtractType();});
        mnuiRemove.setOnAction(valuet->{removeThisAction();});
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
        vboxAction.setStyle("-fx-background-color: " + COLOR_UNCHECKED);
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
        vboxAction.setStyle("-fx-background-color: " + COLOR_UNCHECKED);
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
        vboxAction.setStyle("-fx-background-color: " + COLOR_UNCHECKED);
        btnActionType.setText("Constant");
    }
    
    public void setActionListener(IPaneActionListener al)
    {
        this.actionListener = al;
    }
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public void removeThisAction()
    {
        if (vboxActions != null)
            vboxActions.getChildren().remove(vboxAction);
        if (paneActions != null)
            paneActions.remove(this);
        if (actionListener != null)
            actionListener.setOnActionRemoved(this);
    }
    
    public void checkAction()
    {
        String errDesc = null;
        
        switch (action.getActionType())
        {
            case Extract :
                Integer start = parseInt(txtExtractStart);
                Integer end = parseInt(txtExtractTo);
                if (start == null || start < 1)
                {
                    errDesc = "Minimum value for \"From\" must be 1. Type a valid Integer value";
                    break;
                }
                if (end == null)
                {
                    errDesc = "Minimum value for \"To\" must be 1. Type a valid Integer value";
                    break;
                }
                if (end < start)
                {
                    errDesc = "\"To\" value must be greater or equal than \"From\" value.";
                    break;
                }
                
                if (errDesc == null)
                {
                    ((ActionExtract) action).setFrom(start);
                    ((ActionExtract) action).setTo(end);
                    vboxAction.setStyle("-fx-background-color: " + COLOR_EXTRACT_HEX);
                    checked = true;
                    if (actionListener != null) actionListener.setOnCheckProcessFinished();
                }
                break;
            case Counter :
                Integer startCounter = parseInt(txtCounterStart);
                Integer step = parseInt(txtCounterStep);
                if (txtCounterFormat.getText().isEmpty())
                {
                    errDesc = "Counter pattern must not be empty.";
                    break;
                }
                errDesc = checkCounterPattern();
                if (errDesc != null)
                    break;
                
                if (startCounter == null)
                {
                    errDesc = "Type a valid Integer value";
                    break;
                }
                if (step == null || step < 1)
                {
                    errDesc = "Minimum value for \"Step\" must be 1. Type a valid integer value";
                    break;
                }
                if (errDesc == null)
                {
                    ((ActionCounter) action).setStart(startCounter);
                    ((ActionCounter) action).setStep(step);
                    ((ActionCounter) action).setPattern(txtCounterFormat.getText());
                    vboxAction.setStyle("-fx-background-color: " + COLOR_COUNT_HEX);
                    checked = true;
                    if (actionListener != null) actionListener.setOnCheckProcessFinished();
                }
                break;
                
            case Constant :
                if (txtConstantText.getText().isEmpty())
                {
                    errDesc = "Constant String must not be empty.";
                    break;
                }
                else
                {
                    ((ActionConstant) action).setText(txtConstantText.getText());
                    vboxAction.setStyle("-fx-background-color: " + COLOR_CONSTANT_HEX);
                    checked = true;
                    if (actionListener != null) actionListener.setOnCheckProcessFinished();
                }
        }
        
        
        if (errDesc != null)
        {
            checked = false;
            vboxAction.setStyle("-fx-background-color: " + COLOR_UNCHECKED);
            if (actionListener != null)
            {                
                actionListener.setOnCheckError(errDesc);
                actionListener.setOnCheckProcessFinished();
            }
            
        }
    }
    
    public Integer parseInt(TextField txtf)
    {
        Integer i = null;
        try
        {
            i = Integer.valueOf(txtf.getText().trim());
        } 
        catch (NumberFormatException nfe)
        {
            
        }
        return i;
    }

    public String checkCounterPattern()
    {
        for (int i = 0; i < txtCounterFormat.getText().length(); i++)
            if (txtCounterFormat.getText().charAt(i) != CHAR_NUMBER_PATTERN)
                return "Counter Pattern must contain only character \"#\".";
        return null;
    }
    
}
