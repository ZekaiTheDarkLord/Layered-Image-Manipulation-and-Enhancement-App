Change In Previous:
Moved the read and write file from model to controller, given by the points off in self-eval about not handling IO in model.

Rename and restructured the ImageModel interface so that it is less confusing. It's not wrong, but the naming is bad.

Make the PPM Import and Export class part of controller's delegate and reconstruct the PPMModel under the guide of a TA

New features:
imagecontroller interface: support either reading commands from a txt or reading a string of command directly from client.
Image: Interface that support for read and write image. The implementation decide their own about what format to support or not.

SimpleImageController: implements this interface with an model that holds all the layers, an ImageIOImage that has the ability for handling image in different format and a view that handle interaction with client.

PPMImage: a make up controller that corrects previous mistake in self-eval. In charge of .ppm and .txt form of image read and write.
PPMImageController: also a make up controller that seperate IO part from model to controller.

ImageIOImage: support any format that ImageIO library supports for read and write, i.e [JPG, jpg, tiff, bmp, BMP, gif, GIF, WBMP, png, PNG, JPEG, tif, TIF, TIFF, wbmp, jpeg].


Model:
Layers: interface that extends previous imageModel interface as an extension in functionality. Specially designed getter and setter for multi-layer model.

MultiLayerImageModel: implements this layer and itself extends abstract model, making it a model too. Contains a LinkedHashMap for recording sequence of layer and a string that holds topmost visible layer.

ImageLayer: the interface that represent as a layer in image. Contains methods for get and set what a layer should do in current state.
SimpleImageLayer: an representation of Image layer. It is an private inner class inside MultiLayerImageModel and is only used by it.

View:
PictureProcessingView: interface that contains methods in which a view should do. renderImage()  method is specially leave for GUI if asked.
SimpleView: a text based implementaion of View, did operations on an appendable that provide feedbacks for user with inputs from controller that presents whether commands is finished or not.

Higher Level Consideration:
The private inner class prevent possible mutation of model.

The view interface leave an extensiblility for further GUI, although we do need to reimplement somehow.

The recieving of file commands and user typed command are two seperate methods, this leave space for different variation.

Image read and write support infinite delegation that can add up supporting types.

Model are restructured to more reasonable place with better name and All image operations and filters remained extensibility if needed.

Controller have multiple private methods that in charge of different method call, this leave some space for delegation.

