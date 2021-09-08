/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
