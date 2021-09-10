/*
 *  Project:    FileRenamerFX
 *  Artifact:   Action.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   First code proposal.
 */
package org.grios.filerenfx.core.parse;

/**
 *  This class represents the concept of an Action which defines
 *  an operation to generate new file names or part of the new name.
 *  @author LiveGrios
 */
public abstract class Action
{
    public enum ActionType {Extract, Counter, Constant}
    
    public abstract ActionType getActionType();
    public abstract String apply(Object o) throws Exception;
}
