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
public class ActionExtract extends Action
{
    int from;
    int to;
    
    public ActionExtract()
    {
        this(-1, -1);
    }
    
    public ActionExtract(int from, int to)
    {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public ActionType getActionType()
    {
        return ActionType.Extract;
    }
    
    @Override
    public String apply(Object fileName) throws Exception
    {        
        return fileName.toString().substring(from-1, to);
    }

    public int getFrom()
    {
        return from;
    }

    public void setFrom(int from)
    {
        this.from = from;
    }

    public int getTo()
    {
        return to;
    }

    public void setTo(int to)
    {
        this.to = to;
    }   
}
