package model;

import java.awt.image.BufferedImage;

/**
 * An interface to represent images and the operations that can be performed on them.
 */
public interface IImage {
  /**
   * Creates a filtered version of the image using the given kernel.
   *
   * @param kernel the filter kernel to be applied.
   * @return a filtered version of the image.
   */
  IImage filter(double[][] kernel);

  /**
   * Creates a color transformed version of the image using the given matrix.
   *
   * @param matrix the matrix representing the color transformation to be performed on the rgb
   *               values.
   * @return a post-transformation version of the image.
   */
  IImage colorTransform(double[][] matrix);

  /**
   * Creates a greyscale image by applying some operation to the pixels of the image (specified by
   * the type). Operation types will be specified in implementations.
   *
   * @param type the type of operation to be applied.
   * @return the greyscale image.
   */
  IImage greyMap(String type);

  /**
   * Create a horizontally flipped version of this image.
   *
   * @return the flipped image.
   */
  IImage horizontalFlip();

  /**
   * Create a vertically flipped version of this image.
   *
   * @return the flipped image.
   */
  IImage verticalFlip();

  /**
   * Create a brightened or darkened version of the image.
   *
   * @param amount the amount by which to brighten (or darken if negative).
   * @return the brightened or darkened image.
   */
  IImage brighten(int amount);

  /**
   * Creates a BufferedImage out of this image.
   *
   * @param type the integer representing the file format of the image (see BufferedImage
   *             documentation for details).
   * @return A BufferedImage object of the specified format representing the same image as this
   * IImage.
   */
  public BufferedImage writeToBuffered(int type);
}
