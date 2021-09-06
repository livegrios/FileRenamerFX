/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.core.parse;

/**
 *
 * @author LiveGrios
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
