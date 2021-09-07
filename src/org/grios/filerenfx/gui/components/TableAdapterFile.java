/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grios.filerenfx.gui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.grios.filerenfx.model.FileDescriptor;

/**
 *
 * @author LiveGrios
 */
public class TableAdapterFile
{   
    public static void adapt(TableView<FileDescriptor> tv, ObservableList<FileDescriptor> files)
    {
        TableColumn<FileDescriptor, Void> tcFileIcon = new TableColumn<>("");
        TableColumn<FileDescriptor, String> tcFileName = new TableColumn<>("File Name");
        TableColumn<FileDescriptor, String> tcFileExt = new TableColumn<>("Ext.");
        TableColumn<FileDescriptor, String> tcFileNewName = new TableColumn<>("New File Name");
        
        tcFileIcon.setCellFactory(new Callback<TableColumn<FileDescriptor, Void>, TableCell<FileDescriptor, Void>>()
        {
            @Override
            public TableCell<FileDescriptor, Void> call(TableColumn<FileDescriptor, Void> param)
            {
                TableCell<FileDescriptor, Void> cell = new TableCell<FileDescriptor, Void>()
                {
                    @Override
                    public void updateItem(Void v, boolean value)
                    {
                        super.updateItem(v, value);
                        if (getTableRow() != null && getTableRow().getItem() != null)
                        {
                            ImageView imgv = new ImageView(System.class.getResource("/icons/64/newsfile.png").toExternalForm());
                            imgv.setFitHeight(24.0);
                            imgv.setFitWidth(24.0);
                            setGraphic(imgv);
                        }
                        else
                        {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
                
                return cell;
            }
        });
        
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
                        if (getTableRow() != null && getTableRow().getItem() != null)
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
                        if (getTableRow() != null && getTableRow().getItem() != null)
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
        
        tcFileNewName.setCellFactory(new Callback<TableColumn<FileDescriptor, String>, TableCell<FileDescriptor, String>>()
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
                        if (getTableRow() != null && getTableRow().getItem() != null)
                        {
                            FileDescriptor fd = (FileDescriptor) getTableRow().getItem();
                            setText(fd.getNewName());
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
        
        tcFileIcon.setMinWidth(32);
        tcFileIcon.setMaxWidth(32);
        //tcFileName.setPrefWidth(0);
        //tcFileExt.setPrefWidth(64.0);
        //tcFileExt.setMinWidth(56.0);
        //tcFileExt.setMaxWidth(128.0);
        
        tv.setItems(files);
        tv.getColumns().clear();
        tv.getColumns().addAll(tcFileIcon, tcFileName, tcFileExt, tcFileNewName);
    }
}
