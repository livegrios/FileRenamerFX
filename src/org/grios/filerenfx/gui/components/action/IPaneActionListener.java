/*
 *  Project:    FileRenamerFX
 *  Artifact:   IPaneActionListener.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.gui.components.action;

/**
 *  A custom Java Interface to define events that occurs during
 *  action checking and related tasks as defined in the abstract
 *  methods name.
 *  @author LiveGrios
 */
public interface IPaneActionListener
{
    public void setOnCheckError(String errorDescription);
    public void setOnActionRemoved(PaneAction pa);
    public void setOnCheckProcessFinished();
}
