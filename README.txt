SCRIPT:
Enter the following script to the console when the program runs:
load Test/TestImage.ppm test1   brighten 4 test1 test2   save Test/Test2.ppm test2   horizontal-flip test2 flipped   vertical-flip flipped flipped   blue-component flipped blue   save Test/TestBlue.ppm blue   load Test/TestBlue.ppm blue2   quit



OVERVIEW:

ImageProcessor is a class to hold the main method and actually run the program with user input from the console

IImage is an interface to represent images, and supports various image processing methods

SimpleImage implements IImage and represents images specifically, allowing construction of objects from file 
paths or from 2D Pixel arrays directly

IPixel is an interface for representing any Pixel, with ability to find the R, G, and B values

Pixel implements IPixel and represents standard RGB pixels with no additional functionality

ImageController is an interface to abstract any type of controller for dealing with images; i.e. a program that
is run using a single "execute()" method.

SimpleImageController is a class representing a program which allows loading of various images into the controller, 
performing various image processing methods on them, and saving of of said various images, all controlled by user input

SimpleImageTest is a class to hold tests for SimpleImages

PixelTest is a class to hold tests for Pixels

SimpleImageControllerTest is a class to hold tests for SimpleImageControllers

Histogram is a class that represents a histogram of value and frequency of red, green, blue, and intensity in Buffered image.

GraphicalImageView is a class that represents the GUI of the program. Creating the window, buttons, rendering the image, and all view aspects of the program

GUIController is a class representing a program which allows loading of various images into the controller, 
performing various image processing methods on them, and saving of of said various images, all controlled by action events from the view

ImageProcessor is a class to hold the main method and actually run the program by setting up the view and being controlled through action events.


Design Changes:

One design change we made was to switch to a MVC design as this would allow for the JFrame view to interact with the controller.
We did this by creating a new View (GraphicalImageView), new controller (GUIController), and new main method (ImageProcessor).
This design decision allowed us to create a GUI which allowed for the user to load, modify, and save images.


IMAGES:

TestImage.ppm is an original image authorized for use in this project and used for tests.

notPPM.ppm is also an original file authorized for use in this project and used for tests.

Koala.jpg converted image of koala.png given in assignment 4.

CHANGES:

Swapped width and height in image representation to better align with the BufferedImage class.
Fixed issues with blur and sharpen
