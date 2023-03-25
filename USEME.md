GUI usage:
Menu bar:
Layer:  contains create, delete, current, visible, and invisible.

Operation: contains blur, sharpening, sepia, grey Scale, mosaic and down scaling.

Load and save: load, save, load all and save all.

All above menu items are also appeared as Buttons in order for convinient useage.

Create: use option panel to ask for a name and by default set the newly created layer as current.  Must create a layer first or any other operation beside loadAll will pop up a warning message.

delete, current, visible, and invisible:
All pop up a panel with existing layers name as options and operates on what user have chosen and topmost visible is then displayed in order to restrict user from giving wierd input. 
For detail information of image displaying rule, please reference the Features interface and corresponding method.


All operations, both buttons and menus, operates on current layer displayed on the screen and updates the image after operation to user.

Mossaic: will ask user to input a seeds, will check whther its in integer. 

Down Scaling: will pop up a panel with two field for input width and height and check whether inputs are validate.
Please also reference to Features interface for detail information.


Load and save:
Open an Image(load):Loading use a file chooser that supports "jpg", "jpeg", "ppm", "txt", "png", "gif" by default as an image format.

Save current Picture(save): will pop up a file chooser that user can choose the directory for saving and another pop up chooser to choose for which format the image is saving,"jpg", "jpeg", "ppm", "txt", "png",
The name of image will be the layer name the user entered.

Open Multiple Layers(loadAll): only accept the txt in file chooser and by this assignment, we belive all the image can be found under the same directory.

Save All Pictures(saveAll): will pop up an yes/no panel that warn the user if exporting all means clearing up all existing layers and folding them to the directory as a txt inside res/MultiLayerPackage as before.

Warning: the file path of saving and loading cannot contains any space i.e " " inside. We don't know what's going on but it will throw IOException if it exist. We have went to multiple TA's Office hour including Professor Vido's OH but still can't understand why.
Normal directory with no space will be working and if there is a space, by just specifying part of the path will also work if its in intelliJ's default directory which further confused all of us.  
Please check the name of file path when FileNotFound exception is throwed. 
