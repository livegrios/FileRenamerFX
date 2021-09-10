/*
 *  Project:    FileRenamerFX
 *  Artifact:   Renamer.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   First code proposal.
 */
package org.grios.filerenfx.core.parse;

import java.util.List;

/**
 *  This class contains the method to process actions and generate new
 *  file names.
 *  @author LiveGrios
 */
public class Renamer
{
    /**
     * This method processes a set of actions in order to compute
     * the new file name based on them.
     * @param fname     The Filename whithout extension.
     * @param ext       The File Extension.
     * @param actions   The array of renaming actions to be aplied.
     * @param counters  The array of current counters that will be attached to the new name.
     * @return          The new file name.
     * @throws Exception 
     */
    public String apply(String fname, String ext, Action[] actions, int[] counters) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        int kc = 0;
        for (int i = 0; i < actions.length; i++)
        {
            if (actions[i] instanceof ActionExtract)
                sb.append(((ActionExtract) actions[i]).apply(fname));
            else if (actions[i] instanceof ActionConstant)
                sb.append(((ActionConstant) actions[i]).apply(null));
            else if (actions[i] instanceof ActionCounter)
                sb.append(((ActionCounter) actions[i]).apply(counters[kc++]));
        }
        
        return sb.toString();
    }
    
    
}
