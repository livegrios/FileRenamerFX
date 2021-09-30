/*
 *  Project:    FileRenamerFX
 *  Artifact:   TableAdapterFile.java
 *  Version:    0.1
 *  Date:       2021-09-09 20:03:00
 *  Author:     Miguel Angel Gil Rios (LiveGrios)
 *  Email:      angel.grios@gmail.com
 *  Comments:   This is the first proposal code.
 */
package org.grios.filerenfx.gui.components;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import org.grios.filerenfx.model.FileDescriptor;

/**
 *  This class contains the methods to create columns and their corresponding
 *  renderers in order to display information of the loaded files.
 *  @author LiveGrios
 */
public class TableAdapterFile
{   
    /**
     * This method creates the TableColumns and their respective renderers 
     * that will contain the files information.
     * @param tv
     * @param files 
     */
    public static void adapt(TableView<FileDescriptor> tv, ObservableList<FileDescriptor> files)
    {
        TableColumn<FileDescriptor, Void> tcRowNumber = new TableColumn<>("");
        TableColumn<FileDescriptor, Void> tcFileIcon = new TableColumn<>("");
        TableColumn<FileDescriptor, String> tcFileName = new TableColumn<>("Current File Name");
        TableColumn<FileDescriptor, String> tcFileExt = new TableColumn<>("Ext.");
        TableColumn<FileDescriptor, String> tcFileNewName = new TableColumn<>("New File Name");
        
        tcRowNumber.setCellFactory(new Callback<TableColumn<FileDescriptor, Void>, TableCell<FileDescriptor, Void>>()
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
                        setStyle("-fx-background-color: #ECEFF1;");
                        if (getTableRow() != null && getTableRow().getItem() != null)
                        {
                            setAlignment(Pos.CENTER_RIGHT);
                            setText("" + (getTableRow().getIndex() + 1));
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
                            ImageView imgv = new ImageView(System.class.getResource("/icons/48/document-48.png").toExternalForm());
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
                            setAlignment(Pos.CENTER_LEFT);
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
                            setAlignment(Pos.CENTER_LEFT);
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
                        setStyle("-fx-text-fill:#2962FF;");
                        if (getTableRow() != null && getTableRow().getItem() != null)
                        {
                            setAlignment(Pos.CENTER_LEFT);
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
        
        tcRowNumber.setMinWidth(72);
        tcFileIcon.setMinWidth(32);
        tcFileIcon.setMaxWidth(32);
        tcFileName.setPrefWidth(156);
        tcFileExt.setPrefWidth(72.0);
        tcFileNewName.setPrefWidth(192);
        
        tv.setItems(files);
        tv.getColumns().clear();
        tv.getColumns().addAll(tcRowNumber, tcFileIcon, tcFileName, tcFileExt, tcFileNewName);
    }
}
