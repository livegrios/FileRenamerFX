# FileRenamerFX
FileRenamerFX is an Utility-Application used to rename multiple files using a set of predefined ruled-actions in order 
to avoid to end-users to deal with the use of regular expressions (aka *regexp*).

It is builded on top of Java - JavaFX Platforms.

The main GUI is clean and easy to use:
![Alt text](readme/images/01_MainWindow.png?raw=true "FileRenamerFX main window")

## Features
This application is easy to use.

The memory footprint is low. However, compared with other console-based applications/scripts, it does not.

The GUI is easy to use with good performance among a wide hardware configurations.

It is builded on top of Java 8 / JavaFX platform in order to have compatibility with almost all operating systems even those working with old JVM versions. However, this feature will be removed in the future since the project will be migrated to recent Java versions such as OpenJDK 17.

The release builds includes the specific Java Runtime (JRE) for each supported platform.

## Getting started
The first step using the application is selecting the directory that contains the files to be renamed. It is important to be mentioned that, at his version, only files renaming is supported. This means that directories contained inside selected directory will be ignored.

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

2. The next step is to define the corresponding actions that allow to rename the files in the desired way. You can add actions by using the *add* button ![Alt text](readme/images/13_01_ButtonAddAction.png?raw=true "FileRenamerFX - Add Action.") from the tool bar or by pressing **[F2]** key. In addition, it is possible to remove an action by selecting the *remove* option in the main action button or by pressing **[Shift + F2]** keys. 

![Alt text](readme/images/14_RemoveAction.png?raw=true "FileRenamerFX - Remove action.")

Furthermore, there is possible to remove all current added actions by pressing the *remove action* button ![Alt text](readme/images/13_02_ButtonRemoveAllActions.png?raw=true "FileRenamerFX - Remove all actions."), or by pressing keys **[Ctrl + Shift + F2]** keys.

Next figure illustrate the actions defined for the described example in this guide.

![Alt text](readme/images/06_ActionsNotChecked_01.png?raw=true "FileRenamerFX - Actions definition.")
     
3. After the renaming actions were defined, they must be validated in order to assure that values are valid and the renaming process will be conducted adequately. This can be done by clicking on *Check all actions* button ![Alt text](readme/images/13_03_ButtonCheckAllActions.png?raw=true "FileRenamerFX - Add Action.") or pressing **[F3]** key. In addition, each action can be validated by pressing their main button. If some action has an error, the application will launch a message window indicating were the problem occurred. It is highly recommended to not continue to next step until remaining error are present in action definitions.

4. When all your actions were validated, next step is to perform a renaming preview in order to know the new name of each file. It is highly recommended perform this step before to proceed with the renaming task considering that, changing a file name is an undoable operation. This step can be performed by clicking in button "Rename preview" ![Alt text](readme/images/13_04_ButtonRenamingPreview.png?raw=true "FileRenamerFX - Add Action.") or by pressing **[F4]** key. If the new name that files will have corresponding to the expected result, proceed to their renaming in nex step. Next figure illustrates the result of preview renaming process:

![Alt text](readme/images/09_renamingPreviewResult.png?raw=true "FileRenamerFX - Add Action.")

5. Las step is proceed to renaming files by pressing the *rename* button ![Alt text](readme/images/13_05_ButtonRenameAll.png?raw=true "FileRenamerFX - Add Action.") or **[F5]**  key. Alternatively, you can rename only selected files by clickin on *Rename* button ![Alt text](readme/images/13_05_ButtonRenameSelection.png?raw=true "FileRenamerFX - Add Action.") or pressing **[F6]** key. Next figure illustrates the files renamed in the file explorer.

![Alt text](readme/images/12_RenamingResult.png?raw=true "FileRenamerFX - Add Action.")

## Known Issues
°    Not all cases related with original file names are covered yet. This is a project in constant development and it is in a very early stage. However you can also support it by adding new functionalities or coreccting bugs and errors.
