/*
 *  Project:    FileRenamerFX
 *  Artifact:   PaneAction.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.gui.components.action;

import java.util.List;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.grios.filerenfx.core.parse.Action;
import org.grios.filerenfx.core.parse.ActionConstant;
import org.grios.filerenfx.core.parse.ActionCounter;
import org.grios.filerenfx.core.parse.ActionExtract;

/**
 *  This class contains the JavaFX visual controls that defines the
 *  visual aspect of Action Components which the end user will interact.
 *  @author LiveGrios
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
    
    public static final Image ICON_EXTRACT = new Image(System.class.getResource("/icons/64/columnraw2.png").toExternalForm());
    public static final Image ICON_COUNTER = new Image(System.class.getResource("/icons/64/Count-tool-icon.png").toExternalForm());
    public static final Image ICON_TEXT = new Image(System.class.getResource("/icons/64/text.png").toExternalForm());
    
    public static final char CHAR_NUMBER_PATTERN = '0';
    
    @FXML VBox vboxAction;    
    @FXML HBox hboxExtract;
    @FXML HBox hboxCounter;
    @FXML HBox hboxConstant;
    
    @FXML SplitMenuButton btnActionType;    
    @FXML MenuItem mnuiExtract;
    @FXML MenuItem mnuiCounter;
    @FXML MenuItem mnuiConstant;
    @FXML MenuItem mnuiRemove;
    @FXML ImageView imgvActionIcon;
    
    @FXML Button btnMoveUp;
    @FXML Button btnMoveDown;
    
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
        
        vboxAction.setOnKeyReleased((evt)->{
            
            if (evt.getCode() == KeyCode.UP && evt.isControlDown())
                moveUp();
            else if (evt.getCode() == KeyCode.DOWN && evt.isControlDown())
                moveDown();
        });
        
        btnActionType.setOnAction(evt->{checkAction();});
        mnuiConstant.setOnAction(evt->{setConstantType();});
        mnuiCounter.setOnAction(evt->{setCounterType();});
        mnuiExtract.setOnAction(evt->{setExtractType();});
        mnuiRemove.setOnAction(valuet->{removeThisAction();});
        
        btnMoveDown.setOnAction(evt->{moveDown();});
        btnMoveUp.setOnAction(evt->{moveUp();});
        
        EventHandler<KeyEvent> evt = (KeyEvent event) ->
        {
            if (event.getCode() == KeyCode.ENTER)
                checkAction();
        };
        txtConstantText.setOnKeyReleased(evt);
        txtCounterFormat.setOnKeyReleased(evt);
        txtCounterStart.setOnKeyReleased(evt);
        txtCounterStep.setOnKeyReleased(evt);
        txtExtractStart.setOnKeyReleased(evt);
        txtExtractTo.setOnKeyReleased(evt);
        
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
        
        imgvActionIcon.setImage(ICON_EXTRACT);
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
        
        imgvActionIcon.setImage(ICON_COUNTER);
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
        
        imgvActionIcon.setImage(ICON_TEXT);
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
    
    public void moveUp()
    {
        int pos = vboxActions.getChildren().indexOf(vboxAction);
        int newPos = pos -1;
        if (pos > 0)
            move(newPos);
    }
    
    public void moveDown()
    {
        int pos = vboxActions.getChildren().indexOf(vboxAction);
        int newPos = pos + 1;
        if (newPos < vboxActions.getChildren().size())
            move(newPos);
    }
    
    private void move(int newPos)
    {
        paneActions.remove(this);
        vboxActions.getChildren().remove(vboxAction);

        paneActions.add(newPos, this);
        vboxActions.getChildren().add(newPos, vboxAction);
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
                
//                if (end < start)
//                {
//                    errDesc = "\"To\" value must be greater or equal than \"From\" value.";
//                    break;
//                }
                
                
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
