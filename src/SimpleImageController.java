import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Collection;
import model.IImage;
import model.Pixel;
import model.SimpleImage;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * A class to handle saving, loading and processing operations on images based on user input.
 */
public class SimpleImageController implements ImageController {
  private final Map<String, IImage> images;
  private final Readable input;

  /**
   * Construct and ImageController with no pre-loaded images and the given input source.
   *
   * @param input the source of input to be read.
   */
  public SimpleImageController(Readable input) {
    this(new HashMap<String, IImage>(), input);
  }

  /**
   * Construct an ImageController with the given map of images already loaded.
   *
   * @param images the images loaded into the controller.
   * @param input  the source of input to be read.
   */
  public SimpleImageController(Map<String, IImage> images, Readable input) {
    if (images == null || input == null) {
      throw new IllegalArgumentException("Cannot create a SimpleImageController with null images or input");
    }
    this.images = images;
    this.input = input;
  }

  /**
   * Runs the controller, taking in commands from the input. Supported commands are:
   * load image-path dest-image-name
   * save image-path image-name
   * red-component image-name dest-image-name
   * green-component image-name dest-image-name
   * blue-component image-name dest-image-name
   * luma-component image-name dest-image-name
   * intensity-component image-name dest-image-name
   * value-component image-name dest-image-name
   * horizontal-flip image-name dest-image-name
   * vertical-flip image-name dest-image-name
   * brighten increment image-name dest-image-name
   * blur image-name dest-image-name
   * sharpen image-name dest-image-name
   * greyscale image-name dest-image-name
   * sepia image-name dest-image-name
   * Where image-name represents the source in loaded images dest-image-name represents the name to
   * keep the result under, image-path represents a file path for an image, and increment is an
   * int representing the amount to brighten or darken the image by.
   */
  public void execute() {
    Collection images = new Collection();
    Scanner in = new Scanner(input);
    String path;
    String name;
    String type;
    String input;
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

    while (true) {
      input = in.next();
      switch (input) {
        case "load":
          path = in.next();
          name = in.next();
          Pixel[][] pixels;
          try {
            type = path.split("\\.")[1];
          } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Path doesn't have extension");
          }
          switch (type) {
            case "ppm":
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

              this.images.put(name, new SimpleImage(pixels, maxValue));
              break;
            default:
              BufferedImage img;

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
              this.images.put(name, new SimpleImage(pixels, 255));
              break;
          }
          break;
        case "save":
          path = in.next();
          name = in.next();
          try {
            type = path.split("\\.")[1];
          } catch (NoSuchElementException e) {
            throw new IllegalStateException("Path doesn't have extension");
          }
          try {
            switch (type) {
              case "ppm":
                FileWriter writer = new FileWriter(path);
                writer.write(this.images.get(name).toString());
                writer.close();
                break;
              case "jpeg":
              case "jpg":
              case "png":
              case "bmp":
                ImageIO.write(this.images.get(name).writeToBuffered(5), type,
                        new File(path));
                break;
              default:
                throw new IllegalStateException("Unrecognized file type");
            }
          } catch (IOException e) {
            throw new IllegalStateException("Error writing to file while saving image.");
          }
          break;
        case "red-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("red"));
          break;
        case "green-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("green"));
          break;
        case "blue-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("blue"));
          break;
        case "luma-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("luma"));
          break;
        case "intensity-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("intensity"));
          break;
        case "value-component":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).greyMap("value"));
          break;
        case "horizontal-flip":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).horizontalFlip());
          break;
        case "vertical-flip":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).verticalFlip());
          break;
        case "brighten":
          int amount = in.nextInt();
          name = in.next();
          this.images.put(in.next(), this.images.get(name).brighten(amount));
          break;
        case "blur":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).filter(blur));
          break;
        case "sharpen":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).filter(sharpen));
          break;
        case "greyscale":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).colorTransform(greyscale));
          break;
        case "sepia":
          name = in.next();
          this.images.put(in.next(), this.images.get(name).colorTransform(sepia));
          break;
        case "quit":
          return;
        default:
          throw new IllegalStateException("Invalid commands entered via input: " + input);
      }
    }
  }
}