/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.core.parse;

import java.util.List;

/**
 *
 * @author LiveGrios
 */
public class Renamer
{
    /**
     * 
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
