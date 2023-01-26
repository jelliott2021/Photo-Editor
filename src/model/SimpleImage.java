package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a Image.
 */
public class SimpleImage implements IImage {
  private Pixel[][] pixels;
  private int height;
  private int width;
  private int maxValue;

  /**
   * Creates a Simple image with the given parameters.
   *
   * @param pixels   the pixels of the image.
   * @param maxValue the maximum value for the image.
   */
  public SimpleImage(Pixel[][] pixels, int maxValue) {
    if (pixels == null || pixels.length <= 0 || pixels[0].length <= 0 || maxValue < 1) {
      throw new IllegalArgumentException("Invalid inputs. " +
              "Pixels cannot be null and must have cells," +
              " and maxValue must be greater than 0");
    }
    this.pixels = pixels;
    this.height = pixels.length;
    this.width = pixels[0].length;
    this.maxValue = maxValue;
  }

  /**
   * Creates a filtered version of the image using the given kernel (final values are clamped and
   * rounded down to the integer).
   *
   * @param kernel the filter kernel to be applied.
   * @return a filtered version of the image.
   */
  @Override
  public IImage filter(double[][] kernel) {
    if (kernel == null || kernel.length < 1 || kernel.length % 2 == 0) {
      throw new IllegalArgumentException("Invalid kernel, filtering kernels must have positive" +
              " odd dimensions and be non-null.");
    }
    for (double[] row : kernel) {
      if (row.length != kernel.length) {
        throw new IllegalArgumentException("Invalid kernel, filtering kernels must be square.");
      }
    }

    Pixel[][] filtered = new Pixel[this.height][this.width];
    for (int w = 0; w < this.width; w++) {
      for (int h = 0; h < this.height; h++) {
        Pixel px = this.pixels[h][w];
        int r = 0;
        int g = 0;
        int b = 0;
        for (int x = 0; x < kernel.length; x++) {
          for (int y = 0; y < kernel[0].length; y++) {
            try {
              px = this.pixels[h + x - kernel.length / 2][w + y - kernel.length / 2];
              r += kernel[x][y] * px.getR();
              g += kernel[x][y] * px.getG();
              b += kernel[x][y] * px.getB();
            } catch (IndexOutOfBoundsException ignored) {

            }
          }
        }
        filtered[h][w] = new Pixel(Math.min(Math.max(r, 0), 255),
                Math.min(Math.max(g, 0), 255),
                Math.min(Math.max(b, 0), 255));
      }
    }

    return new SimpleImage(filtered, this.maxValue);
  }

  /**
   * Creates a color transformed version of the image using the given matrix (final values are
   * clamped and rounded down to the integer).
   *
   * @param matrix the matrix representing the color transformation to be performed on the rgb
   *               values.
   * @return a post-transformation version of the image.
   */
  @Override
  public IImage colorTransform(double[][] matrix) {
    if (matrix == null || matrix.length < 1) {
      throw new IllegalArgumentException("Invalid matrix, color transformation matrix must have " +
              "positive and be non-null.");
    }
    for (double[] row : matrix) {
      if (row.length != 3) {
        throw new IllegalArgumentException("Invalid matrix, color transformation matrix must be " +
                "3x3.");
      }
    }
    Pixel[][] transformed = new Pixel[this.height][this.width];

    for (int w = 0; w < this.width; w++) {
      for (int h = 0; h < this.height; h++) {
        Pixel px = this.pixels[h][w];
        int r = px.getR();
        int g = px.getG();
        int b = px.getB();
        transformed[h][w] = new Pixel(
                (int) Math.max(0,
                        Math.min(255, r * matrix[0][0] + g * matrix[0][1] + b * matrix[0][2])),
                (int) Math.max(0,
                        Math.min(255, r * matrix[1][0] + g * matrix[1][1] + b * matrix[1][2])),
                (int) Math.max(0,
                        Math.min(255, r * matrix[2][0] + g * matrix[2][1] + b * matrix[2][2])));
      }
    }

    return new SimpleImage(transformed, this.maxValue);
  }

  /**
   * Creates a greyscale image by applying some operation to the pixels of the image (specified by
   * the type).
   *
   * @param type the type of operation to be applied. Can be "red", "green", "blue", "luma",
   *             "intensity", or "value", which set the values in the grayscale image based on that
   *             component of each pixel.
   * @return the greyscale image.
   */
  @Override
  public IImage greyMap(String type) {
    int grey;
    Pixel[][] out = new Pixel[height][width];
    for (int j = 0; j < width; j++) {
      for (int i = 0; i < height; i++) {
        switch (type) {
          case "red":
            grey = pixels[i][j].getR();
            break;
          case "green":
            grey = pixels[i][j].getG();
            break;
          case "blue":
            grey = pixels[i][j].getB();
            break;
          case "luma":
            grey = (int) ((pixels[i][j].getR() * .2126) +
                    (pixels[i][j].getG() * .7152) +
                    (pixels[i][j].getB() * .0722));
            break;
          case "intensity":
            grey = (int) ((pixels[i][j].getR() +
                    pixels[i][j].getG() +
                    pixels[i][j].getB()) / 3);
            break;
          case "value":
            grey = Math.max(Math.max(pixels[i][j].getR(), pixels[i][j].getG()),
                    pixels[i][j].getB());
            break;
          default:
            throw new IllegalArgumentException("Invalid component type requested.");
        }
        out[i][j] = new Pixel(grey, grey, grey);
      }
    }
    return new SimpleImage(out, this.maxValue);
  }

  /**
   * Create a vertically flipped version of this image.
   *
   * @return the flipped image.
   */
  @Override
  public IImage verticalFlip() {
    Pixel[][] out = new Pixel[height][width];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        out[j][i] = pixels[height - j - 1][i];
      }
    }
    return new SimpleImage(out, this.maxValue);
  }

  /**
   * Create a horizontally flipped version of this image.
   *
   * @return the flipped image.
   */
  @Override
  public IImage horizontalFlip() {
    Pixel[][] out = new Pixel[height][width];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        out[j][i] = pixels[j][width - i - 1];
      }
    }
    return new SimpleImage(out, this.maxValue);
  }

  /**
   * Create a brightened or darkened version of the image.
   *
   * @param amount the amount by which to brighten (or darken if negative).
   * @return the brightened or darkened image.
   */
  @Override
  public IImage brighten(int amount) {
    Pixel[][] out = new Pixel[height][width];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        out[j][i] = new Pixel(Math.max(0, Math.min(pixels[j][i].getR() + amount, this.maxValue)),
                Math.max(0, Math.min(pixels[j][i].getG() + amount, this.maxValue)),
                Math.max(0, Math.min(pixels[j][i].getB() + amount, this.maxValue)));
      }
    }
    return new SimpleImage(out, this.maxValue);
  }

  /**
   * Returns a string representing this image based on the PPM file type.
   *
   * @return the string as detailed above.
   */
  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    out.append("P3\n" +
            this.height + " " + this.width + "\n" +
            this.maxValue + "\n");

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        out.append(" " + pixels[i][j].getR() +
                " " + pixels[i][j].getG() +
                " " + pixels[i][j].getB() + " ");
      }
      out.append("\n");
    }

    return out.toString();
  }

  /**
   * Creates a BufferedImage out of this image.
   *
   * @param type the integer representing the file format of the image (see BufferedImage
   *             documentation for details).
   * @return A BufferedImage object of the specified format representing the same image as this
   * IImage.
   */
  @Override
  public BufferedImage writeToBuffered(int type) {
    BufferedImage image = new BufferedImage(this.width, this.height, type);
    for (int w = 0; w < this.width; w++) {
      for (int h = 0; h < this.height; h++) {
        Pixel px = this.pixels[h][w];
        Color rgb = new Color((Math.min(Math.max(px.getR(), 0), 255)),
                (Math.min(Math.max(px.getG(), 0), 255)),
                (Math.min(Math.max(px.getB(), 0), 255)));
        image.setRGB(w, h, rgb.getRGB());
      }
    }
    return image;
  }
}