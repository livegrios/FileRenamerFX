# FileRenamerFX
FileRenamerFX is an Utility-Application used to rename multiple files using a set of predefined ruled-actions in order 
to avoid to end-users to deal with the use of regular expressions (aka *regexp*).

The main GUI is clean and easy to use:
![Alt text](readme/images/01_MainWindow.png?raw=true "FileRenamerFX main window")

## Features
This application is easy to use.

The memory footprint is low. However, compared with other console-based applications/scripts, it does not.

The GUI is easy to use with good performance among a wide hardware configurations.

It is builded on top of Java 8 / JavaFX platform in order to have compatibility with almost all operating systems even those working with old JVM versions. However, this feature will be removed in the future since the project will be migrated to recent Java versions such as OpenJDK 17.

The release builds includes the specific Java Runtime (JRE) for each supported platform.

## Getting started
The first step using the application is selecting the directory that contains the files to be renamed. It is important to be mentioned that, at his version, only files renaming is supported. This means that directories contained in the previous parent directory selected will be ignored.

![Alt text](readme/images/04_LoadingDirectoryContents.png?raw=true "FileRenamerFX - Selecting a directory and loading their contents.")

After the process loading is finished, the names of contained files and some corresponding basic information are displayed.

![Alt text](readme/images/05_DirectoryContentsLoaded.png?raw=true "FileRenamerFX - Displaying directory files content.")

### Actions
The application is based in the *blocks* concept, which allows to define and configure a set of specific actions in an easy way. 
This actions represents the operations that will be performed when the renaming process is called after. 
Also, is possible to remove actions or change their order of precedence from a top-bottom point of view where the top action
will be the first in be applied in the renaming process.

![Alt text](readme/images/03_ActionTypes.png?raw=true "FileRenamerFX - Action types")
### Action types
At this very earlier version, only three basic action types are defined: Extraction, Counter and Constant.
#### Extract
![Alt text](readme/images/02_ActionExtract_NotChecked.png?raw=true "FileRenamerFX - Extract action.")

Extract action is useful when it is need to extract characters from the original file name. 
For example, if the directory contains a set of files named as: PSN__0001.png, NSN_45.png, ..., PPN_AX3.png,
and for the renaming operation is necessary to extract the initial four characters, the **Extract** action is useful.
This actions only requires two values: the **start** position begining in **1** as the minimum valid value and the number of characters to be extracted which minimum valid value is **1**.

### Counter
![Alt text](readme/images/02_ActionCounter_NotChecked.png?raw=true "FileRenamerFX - Counter action.")

Counter action is useful to attach an incremental value to the file name. Also, a numbering pattern is possible to be defined 
in order to keep the new file names according to naming patterns. The numbering pattern is responsible for example, to fill with left-leading zeros 
the new file name. Using this approach the new file names can be expressed for example, as follows: MyFile0001.txt, MyFile0002.txt, ... MyFile9999.txt.

### Constant
![Alt text](readme/images/02_ActionConstant_NotChecked.png?raw=true "FileRenamerFX - Constant action.")

Constant action allows to define static text for the new file name. This text will be include in the new name of all files that will be renamed.

## Basic Example
In this basic example, the renaming of a set of 5000 png files will be performed by using all types of available actions.
In the next image, a screenshot of the files contained in some directory is illustrated:

![Alt text](readme/images/10_OriginalFiles.png?raw=true "FileRenamerFX - Original files.")

As it can be observed, the initial file names follows some naming convention (or may not). For this example, all those files are need to
be renamed following the next pattern: **XXX_Testing_YYYY.png**. Where **XXX** are the first three characters of the file name. The **_Testing_** word is a constant text for all file names and the **YYYY** term means a numbering pattern with 4 digits where is the need to put leading lef-zeros when counter numbering is less than 1000.

1. The first step after the application is running is to select a directory and load their contents.

2. The next step

After the directory contents, 

## Known Issues
