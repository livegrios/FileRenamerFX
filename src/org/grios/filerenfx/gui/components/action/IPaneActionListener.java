/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui.components.action;

/**
 *
 * @author LiveGrios
 */
public interface IPaneActionListener
{
    public void setOnCheckError(String errorDescription);
    public void setOnActionRemoved(PaneAction pa);
    public void setOnCheckProcessFinished();
}
