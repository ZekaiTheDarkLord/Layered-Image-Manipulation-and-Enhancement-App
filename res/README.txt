Pixels interface: This class give functionalities that is expected as what a pixel can perform inside our implementation.  For every methods and parameter inside our entire assignment, we use pixels as generics or specifiers so that Every Implementation of this pixels class
can be fit inside our model. Morover, for every place of accessing a value, we use a getter method that is inside this interface. This means if we are asked to have different kinds of representation of pixels, i.e not in r,g,b but in binary, we would simply implements this method without changing the exisiting code inside our model about manipulating image
as well as filter and color transformation as they only use methods in interface. 

Pixel: This is a pixel implementation for this current assignment. If in future hw, we are asked to implement more function in our pixel or different constraints(i.e more than 256) we are able to either use an Object adapter or class adapter. I personally would prefer the previous one
as in the case we support more colors, the past constraints are no longer true thus delegate would cause conflict.


imageModelGetState: the observe interface for a model that return a copy of values inside our model.

ImageModel: We created this interface in order to contain a general image implementation with filter and transformation as well as  a checkerBoard method
AbstractImageModel: lifted the forseenable redundant code such as checkerBoard and filters inside so that we no longer need to copy and paste them in future.  Inside, the maxValue and pixels[][], respresenting image, is stored as this is a common instance for every model.

Image: This image interface is desinged to support any kind of image type. The class that implements this interface should determine their own support image type inside read and write methods.  This opened for extension.

PPMImageModel: This class implements PPMImage and extending abstract model means it is an image model that support dealing with ppm images and applying filters.


filter: This interface specified an commutative method that apply a filter to a given image.
AbstractFilter: In this abstract class, not only we lifted the redundant code for each filter implementation but also uses a factory method that determine the exact filter to be used.  This means, we are able to create new filter as a private static final instance along with our Blur and Sharpening class.
In the future, if we are expecting more operations, we would able to simply extends the AbstractFilter and define the new matrix inside the new class. No old code needs to be changed for adding new matrix.

The same logic applys for ColorTransformation and AbstractColorTransformation. 


How to use: If there is a GUI with JButtons and we would expect a set of commands, one for import path， one for operate/operations, and one for export path.  The sequence would be calling read(String path), do some operation and write(String path).
If user needs, we have different observe methods that support showing the properties of image by View.

Future design: If more filter or transformation, new filter will extends the corresponding abstract class that holds the apply method and new filter matrix is contained as private inside there own class.
If we support more format, we can have a decorator implementing the image class (with or without model depending on need) so that not only new format will be supported, but also include old support format.
If different kinds of checkBoard is needed, we will also use decorator in our new implementation.


Citation:
Color Picker: Alan(2014-01-27):
https://alanhou.org/find-out-color-name/
WindowXp Dex: Charles O'Rear(1996)
https://en.wikipedia.org/wiki/Bliss_(image)


									