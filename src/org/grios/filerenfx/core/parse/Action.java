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
public abstract class Action
{
    enum ActionType {Extract, Counter, Constant}
    
    public abstract ActionType getActionType();
    public abstract String apply(Object o) throws Exception;
}
