/*
 *  Project:    FileRenamerFX
 *  Artifact:   ActionsPersist.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This class is intended to persist the actions that
 *              users add to their workspace.
 */
package org.grios.filerenfx.core;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.grios.filerenfx.core.parse.Action;
import org.grios.filerenfx.gui.components.action.PaneAction;

/**
 *
 * @author LiveGrios
 */
public class ActionsPersist
{
    public static void save(List<PaneAction> paneActions) throws Exception
    {
        List<Action> actions = new ArrayList<Action>();
        for (PaneAction pa : paneActions)
            actions.add(pa.getAction());
        
        System.out.println(new Gson().toJson(actions));
    }
}
