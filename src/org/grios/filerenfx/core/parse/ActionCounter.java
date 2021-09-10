/*
 *  Project:    FileRenamerFX
 *  Artifact:   ActionCounter.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   First code proposal.
 */
package org.grios.filerenfx.core.parse;

import java.text.DecimalFormat;

/**
 *  This class implements the functionality to perform a counter task
 *  that will be reflected in the new file names.
 *  @author LiveGrios
 */
public class ActionCounter extends Action
{
    String pattern;
    DecimalFormat df;
    int start;
    int step;
    
    public ActionCounter()
    {
        pattern = "0";
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
