/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.core.parse;

import java.text.DecimalFormat;

/**
 *
 * @author LiveGrios
 */
public class ActionCounter extends Action
{
    String pattern;
    DecimalFormat df;
    int start;
    int step;
    
    public ActionCounter()
    {
        pattern = "#";
        df = new DecimalFormat(pattern);
    }
    
    @Override
    public ActionType getActionType()
    {
        return ActionType.Counter;
    }
    
    @Override
    public String apply(Object count) throws Exception
    {        
        return df.format(count);
    }
    
    public String getPattern()
    {
        return pattern;
        
    }

    public void setPattern(String pattern)
    {
        this.pattern = pattern;
        df = new DecimalFormat(pattern);
    }
    
    public DecimalFormat getFormat()
    {
        return df;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getStep()
    {
        return step;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    
    
}
