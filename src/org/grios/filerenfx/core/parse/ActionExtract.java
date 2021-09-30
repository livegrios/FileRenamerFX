/*
 *  Project:    FileRenamerFX
 *  Artifact:   ActionExtract.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   First code proposal.
 */
package org.grios.filerenfx.core.parse;

/**
 *  This class implements the functionality to define an extract operation
 *  that will act over the original file name in order to substract specific 
 *  character sequences from it.
 *  @author LiveGrios
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
        if ((from - 1) + to <= fileName.toString().length())
            return fileName.toString().substring(from-1, from + to - 1);
        else
        {
            StringBuilder sb = new StringBuilder();
            sb.append(fileName.toString().substring(from-1, fileName.toString().length()));
            sb.append(new String(new char[(from + to - 1) - fileName.toString().length()]).replace("\0", "-"));
            return sb.toString();
        }
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
