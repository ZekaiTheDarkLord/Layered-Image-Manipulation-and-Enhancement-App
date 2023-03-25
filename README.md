Change of previous:

Add access modifier to previous missing spot, MultiLayerController and MultiLayerModel, pointed out by grader of Assignment 6.

Fixed a small bug in MultiLayerModel, add a line of code in Line 71, which has previously documented as a functionality of deleteLayer() in Line 26 of Layers interface but failed to perform due to my poor memory.

New Features:

ImageGuiView: an interface that extends previous PictureProfessingView interface in order for reusing previous code and support new GUI features.

GUIView: Construct a JFrame that supports all functionality needed and override previous View's methods in order  to reuse previous code without changeing implementation of previous controller. 
i.e the message rendered in controller don't need to be rewrited and are displayed in our new GUI.

GUIMenu: private static inner class inside GUIView that constructs the menu bar needed.

Features: the features interface that contains all possible button calls/user interaction that would need to react from GUI and extends the previous controller so that we can reuse previous code without changing our previous controller.

GUIController: controller that supports the GUI by implementing the feature interface and react to different button call, not implementing ActionListener in order to seperate view and controller. 
Has an delegate to previous controller and reuse most of its code without modifying.

Design thinking: Under current structure, if I need to reimplement my GUI, I just need to reimplement the GUI interface and leave my old controller the same without changing. 
If I need more features, then I am able to extends previous feature interface and have another controller extends or delegate the previous controller that add up by not changing view at all, or possibly more buttons.
Then this design satisfy changing one part with no/minimal changes to the others as professor said.

-----------------------------------------------------------------------------------Extra-------------------------------------------------------------------------------

DownScaling: We added the interface Downscaling. For the downscaling, as the size of the image has changed when running the program, the return type of the apply method is Pixels[][] which represents the pxiels in the image. Also, becuase of that, we create this interface with no interfaces or classes extended. 

Mosaicing: We added theinterfaces Mosaicing. For the Mosaicing interface, it contains a apply method which is the same as the return type of the filter and the ColorTranformation. Therefore, the mosaicing interface extends the old filter. In addition, as there a number of 
seeds is required before generating the picture, therefore, I overload the apply method. So there are two apply method in downscaling interface: a default apply method that takes nothing and gives out a picture that is mosaiced with 1000 seed and a overload apply method that takes the number of seeds to generate the corresponding image. 

Then, in order to add these functionalities into our program, we create a new controller GUIController to implements the old controller that will respond to commands like downsizing or mosaicing but also delgate to have functionalities of the old controller. When the commands are received, new classes(including downsizing and mosaicing) will be generated and use its apply method to get the expected
image. At the last, we add buttons in the GUI to make the user can access to these functionalites. 

Citation:
Color Picker: Alan(2014-01-27):
https://alanhou.org/find-out-color-name/
WindowXp Dex: Charles O'Rear(1996)
https://en.wikipedia.org/wiki/Bliss_(image)


