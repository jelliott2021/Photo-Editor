import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IImage;
import model.Pixel;
import model.SimpleImage;

/**
 * A class to handle saving, loading and processing operations on images based on user input.
 */
public class GUIController implements ImageController, ActionListener {
  private final Map<String, IImage> images;
  private final GraphicalImageView view;

  /**
   * Construct and ImageController with no pre-loaded images and the given input source.
   */
  public GUIController() {
    this(new HashMap<String, IImage>());
  }

  /**
   * Construct an ImageController with the given map of images already loaded.
   *
   * @param images the images loaded into the controller.
   */
  public GUIController(Map<String, IImage> images) {
    if (images == null) {
      throw new IllegalArgumentException("Cannot create a SimpleImageController with null images or input");
    }
    this.images = images;
    this.view = new GraphicalImageView();
  }

  String VIEWTEMP = " TempStringAsPlaceHolderForImage";

  public void execute() {
    view.setDefaultLookAndFeelDecorated(false);
    view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    view.setVisible(true);

    view.getFileLoadButton().addActionListener(this);
    view.getFileSaveButton().addActionListener(this);
    view.getBlueButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getBlurButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getBrightenButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getDarkenButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getGreenButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getGreyscaleButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getHorizontalButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getVerticalButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getValueButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getSepiaButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getSharpenButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getRedButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getIntensityButton().addActionListener(e -> modifyImage(e.getActionCommand()));
    view.getLumaButton().addActionListener(e -> modifyImage(e.getActionCommand()));
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Save file":
      case "Open file": {
        int retvalue;
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, PNG, BMP, PPM", "jpeg", "jpg", "bmp", "png", "ppm");
        fchooser.setFileFilter(filter);
        if (e.getActionCommand().equals("Open file")) {
          retvalue = fchooser.showOpenDialog(view.getFileLoadButton());
        } else {
          retvalue = fchooser.showSaveDialog(view.getFileLoadButton());
        }
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();

          if (e.getActionCommand().equals("Open file")) {
            loadPickedFile(f.getAbsolutePath());
          } else {
            savePickedFile(f.getAbsolutePath());
          }
        }
      }
    }
  }

  private void savePickedFile(String path) {
    String type;

    try {
      type = path.split("\\.")[1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalStateException("Path doesn't have extension");
    }
    try {
      switch (type) {
        case "ppm":
          FileWriter writer = new FileWriter(path);
          writer.write(this.images.get(VIEWTEMP).toString());
          writer.close();
          break;
        case "jpeg":
        case "jpg":
        case "png":
        case "bmp":
          ImageIO.write(this.images.get(VIEWTEMP).writeToBuffered(5), type,
                  new File(path));
          break;
        default:
          throw new IllegalStateException("Unrecognized file type");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Error writing to file while saving image.");
    }
  }

  private void loadPickedFile(String path) {
    String type;
    BufferedImage img;
    Pixel[][] pixels;

    try {
      type = path.split("\\.")[1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalStateException("Path doesn't have extension");
    }

    if (type.equals("ppm")) {
      if (path == null) {
        throw new IllegalArgumentException("Path cannot be null");
      }
      Scanner sc;

      try {
        sc = new Scanner(new FileInputStream(path));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + path + " not found!");
      }
      StringBuilder builder = new StringBuilder();
      //read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }

      //now set up the scanner to read from the string we just built
      sc = new Scanner(builder.toString());

      String token;

      token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
      }
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();

      pixels = new Pixel[width][height];
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          pixels[i][j] = new Pixel(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
      }

      this.images.put(VIEWTEMP, new SimpleImage(pixels, maxValue));
    } else {
      try {
        img = ImageIO.read(new FileInputStream(path));
      } catch (FileNotFoundException e) {
        throw new IllegalStateException("File " + path + " not found!");
      } catch (IOException e) {
        throw new IllegalArgumentException("Cannot create Buffered Image");
      }

      pixels = new Pixel[img.getHeight()][img.getWidth()];
      Color rgb;
      for (int i = 0; i < pixels.length; i++) {
        for (int j = 0; j < pixels[0].length; j++) {
          rgb = new Color(img.getRGB(j, i));
          pixels[i][j] = new Pixel(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
        }
      }
      this.images.put(VIEWTEMP, new SimpleImage(pixels, 255));
    }
    view.renderImage(this.images.get(VIEWTEMP).writeToBuffered(5));
  }


  public void modifyImage(String input) {
    double[][] blur = {{0.0625, 0.125, 0.0625},
            {0.125, 0.25, 0.125},
            {0.0625, 0.125, 0.0625}};
    double[][] sharpen = {{-0.125, -0.125, -0.125, -0.125, -0.125,},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1, 0.25, -0.125},
            {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125,}};
    double[][] greyscale = {{0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
    double[][] sepia = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

    switch (input) {
      case "red-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("red"));
        break;
      case "green-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("green"));
        break;
      case "blue-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("blue"));
        break;
      case "luma-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("luma"));
        break;
      case "intensity-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("intensity"));
        break;
      case "value-component":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).greyMap("value"));
        break;
      case "horizontal-flip":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).horizontalFlip());
        break;
      case "vertical-flip":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).verticalFlip());
        break;
      case "brighten":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).brighten(10));
        break;
      case "darken":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).brighten(-10));
        break;
      case "blur":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).filter(blur));
        break;
      case "sharpen":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).filter(sharpen));
        break;
      case "greyscale":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).colorTransform(greyscale));
        break;
      case "sepia":
        this.images.put(VIEWTEMP, this.images.get(VIEWTEMP).colorTransform(sepia));
        break;
      default:
        throw new IllegalStateException("Invalid commands entered via input: " + input);
    }
    view.renderImage(this.images.get(VIEWTEMP).writeToBuffered(5));
  }
}
