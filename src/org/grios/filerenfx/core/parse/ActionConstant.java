/*
 *  Project:    FileRenamerFX
 *  Artifact:   ActionConstant.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   First code proposal.
 */
package org.grios.filerenfx.core.parse;

/**
 *  This class implements the functionality to put a constant string in the 
 *  new file names.
 *  @author LiveGrios
 */
public class ActionConstant extends Action
{
    
    String text;
    
    public ActionConstant() {this ("");}
    
    public ActionConstant(String text)
    {
        this.text = text;
    }

    @Override
    public ActionType getActionType()
    {
        return ActionType.Constant;
    }        

    @Override
    public String apply(Object o) throws Exception
    {
        return text;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    
    
    
}
