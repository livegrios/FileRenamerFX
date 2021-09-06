/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui.components;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.grios.filerenfx.gui.Main;
import org.grios.filerenfx.model.FileDescriptor;
import org.grios.filerenfx.task.TaskLoadDirectoryContent;

/**
 *
 * @author LiveGrios
 */
public class TableAdapterFile
{   
    public static void adapt(TableView<FileDescriptor> tv, ObservableList<FileDescriptor> files)
    {
        TableColumn<FileDescriptor, String> tcFileName = new TableColumn<>("File Name");
        TableColumn<FileDescriptor, String> tcFileExt = new TableColumn<>("Ext.");
        
        tcFileName.setCellFactory(new Callback<TableColumn<FileDescriptor, String>, TableCell<FileDescriptor, String>>()
        {
            @Override
            public TableCell<FileDescriptor, String> call(TableColumn<FileDescriptor, String> param)
            {
                TableCell<FileDescriptor, String> cell = new TableCell<FileDescriptor, String>()
                {
                    @Override
                    public void updateItem(String t, boolean value)
                    {
                        super.updateItem(t, value);
                        if (getTableRow().getItem() != null)
                        {
                            FileDescriptor fd = (FileDescriptor) getTableRow().getItem();
                            setText(fd.getName());
                        }
                        else
                        {
                            setText(null);
                        }
                    }
                };
                
                return cell;
            }
        });
        
        tcFileExt.setCellFactory(new Callback<TableColumn<FileDescriptor, String>, TableCell<FileDescriptor, String>>()
        {
            @Override
            public TableCell<FileDescriptor, String> call(TableColumn<FileDescriptor, String> param)
            {
                TableCell<FileDescriptor, String> cell = new TableCell<FileDescriptor, String>()
                {
                    @Override
                    public void updateItem(String t, boolean value)
                    {
                        super.updateItem(t, value);
                        if (getTableRow().getItem() != null)
                        {
                            FileDescriptor fd = (FileDescriptor) getTableRow().getItem();
                            setText(fd.getExtension());
                        }
                        else
                        {
                            setText(null);
                        }
                    }
                };
                
                return cell;
            }
        });
        
        
        //tcFileName.setPrefWidth(0);
        //tcFileExt.setPrefWidth(64.0);
        //tcFileExt.setMaxWidth(72.0);
        
        tv.setItems(files);
        tv.getColumns().clear();
        tv.getColumns().addAll(tcFileName, tcFileExt);
    }
}
