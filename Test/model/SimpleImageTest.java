package model;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SimpleImageTest {
  Pixel[][] exPixels = {{new Pixel(3, 5, 1), new Pixel(2, 1, 0),
          new Pixel(0, 7, 6)},
          {new Pixel(0, 4, 5), new Pixel(9, 3, 8), new Pixel(2, 5, 1)},
          {new Pixel(3, 8, 1), new Pixel(0, 1, 2), new Pixel(3, 1, 2)}};
  IImage exImage = new SimpleImage(exPixels, 9);
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

  /**
   * Checks that the constructor throws exceptions when given an image with no width.
   */
  @Test
  public void testConstructorZeroWidth() {
    try {
      IImage testImage = new SimpleImage(new Pixel[1][0], 2);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid inputs. " +
              "Pixels cannot be null and must have cells," +
              " and maxValue must be greater than 0", e.getMessage());
    }
  }

  /**
   * Checks that the constructor throws an exception when given zero for the max value.
   */
  @Test
  public void testConstructorZeroMax() {
    try {
      IImage testImage = new SimpleImage(new Pixel[1][1], 0);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid inputs. " +
              "Pixels cannot be null and must have cells," +
              " and maxValue must be greater than 0", e.getMessage());
    }
  }

  /**
   * Checks that the constructor throws an exception when given zero for the max value.
   */
  @Test
  public void testConstructorZeroHeight() {
    try {
      IImage testImage = new SimpleImage(new Pixel[0][1], 5);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid inputs. " +
              "Pixels cannot be null and must have cells," +
              " and maxValue must be greater than 0", e.getMessage());
    }
  }

  /**
   * Checks that the constructor throws an exception when given zero for the max value.
   */
  @Test
  public void testConstructorNullPixels() {
    try {
      IImage testImage = new SimpleImage(null, 1);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid inputs. " +
              "Pixels cannot be null and must have cells," +
              " and maxValue must be greater than 0", e.getMessage());
    }
  }

  /**
   * Checks that the toString method correctly produces a PPM-formatted string.
   */
  @Test
  public void testToString() {
    assertEquals(exImage.toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 3 5 1  2 1 0  0 7 6 \n" +
                    " 0 4 5  9 3 8  2 5 1 \n" +
                    " 3 8 1  0 1 2  3 1 2 \n");
  }

  /**
   * Tests that the horizontal and vertical flip methods work correctly.
   */
  @Test
  public void testFlip() {
    assertEquals(exImage.horizontalFlip().toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 7 6  2 1 0  3 5 1 \n" +
                    " 2 5 1  9 3 8  0 4 5 \n" +
                    " 3 1 2  0 1 2  3 8 1 \n");
    assertEquals(exImage.verticalFlip().toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 3 8 1  0 1 2  3 1 2 \n" +
                    " 0 4 5  9 3 8  2 5 1 \n" +
                    " 3 5 1  2 1 0  0 7 6 \n");
  }

  /**
   * Tests that the greyMap method works correctly for all supported component types.
   */
  @Test
  public void testGreyMap() {
    assertEquals(exImage.greyMap("red").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 3 3 3  2 2 2  0 0 0 \n" +
                    " 0 0 0  9 9 9  2 2 2 \n" +
                    " 3 3 3  0 0 0  3 3 3 \n");

    assertEquals(exImage.greyMap("green").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 5 5 5  1 1 1  7 7 7 \n" +
                    " 4 4 4  3 3 3  5 5 5 \n" +
                    " 8 8 8  1 1 1  1 1 1 \n");

    assertEquals(exImage.greyMap("blue").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 1 1 1  0 0 0  6 6 6 \n" +
                    " 5 5 5  8 8 8  1 1 1 \n" +
                    " 1 1 1  2 2 2  2 2 2 \n");

    assertEquals(exImage.greyMap("luma").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 4 4 4  1 1 1  5 5 5 \n" +
                    " 3 3 3  4 4 4  4 4 4 \n" +
                    " 6 6 6  0 0 0  1 1 1 \n");

    assertEquals(exImage.greyMap("intensity").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 3 3 3  1 1 1  4 4 4 \n" +
                    " 3 3 3  6 6 6  2 2 2 \n" +
                    " 4 4 4  1 1 1  2 2 2 \n");

    assertEquals(exImage.greyMap("value").toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 5 5 5  2 2 2  7 7 7 \n" +
                    " 5 5 5  9 9 9  5 5 5 \n" +
                    " 8 8 8  2 2 2  3 3 3 \n");
  }

  /**
   * Checks that the greyMap method throws an exception when given an unsupported component type.
   */
  @Test
  public void testGreyException() {
    try {
      exImage.greyMap("kdhks");
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid component type requested.", e.getMessage());
    }
  }

  /**
   * Tests that the brighten method correctly produces results for brighter, darker, and the same
   * image.
   */
  @Test
  public void testBrighten() {
    assertEquals(exImage.brighten(0).toString(),
            exImage.toString());

    assertEquals(exImage.brighten(3).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 6 8 4  5 4 3  3 9 9 \n" +
                    " 3 7 8  9 6 9  5 8 4 \n" +
                    " 6 9 4  3 4 5  6 4 5 \n");

    assertEquals(exImage.brighten(-3).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 2 0  0 0 0  0 4 3 \n" +
                    " 0 1 2  6 0 5  0 2 0 \n" +
                    " 0 5 0  0 0 0  0 0 0 \n");

    assertEquals(exImage.brighten(9).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 9 9 9  9 9 9  9 9 9 \n" +
                    " 9 9 9  9 9 9  9 9 9 \n" +
                    " 9 9 9  9 9 9  9 9 9 \n");

    assertEquals(exImage.brighten(-9).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n");
  }

  /**
   * Checks that the colorTransform method throws an exception when given a null value.
   */
  @Test
  public void testColorTException1() {
    try {
      exImage.colorTransform(null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid matrix, color transformation matrix must have positive and " +
              "be non-null.", e.getMessage());
    }
  }

  /**
   * Checks that the colorTransform method throws an exception when given a non 3x3 array.
   */
  @Test
  public void testColorTException2() {
    try {
      exImage.colorTransform(new double[3][2]);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid matrix, color transformation matrix must be 3x3.", e.getMessage());
    }
  }

  /**
   * Checks that the colorTransform method throws an exception when given a less than 1x1 array.
   */
  @Test
  public void testColorTException3() {
    try {
      exImage.colorTransform(new double[0][0]);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid matrix, color transformation matrix must have positive and " +
              "be non-null.", e.getMessage());
    }
  }

  /**
   * Tests that the colorTransform method correctly produces results for the given 2D array
   */
  @Test
  public void testColorTransformation() {
    assertEquals(exImage.colorTransform(new double[3][3]).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n");
    assertEquals(exImage.colorTransform(greyscale).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 4 4 4  1 1 1  5 5 5 \n" +
                    " 3 3 3  4 4 4  4 4 4 \n" +
                    " 6 6 6  0 0 0  1 1 1 \n");
    assertEquals(exImage.colorTransform(sepia).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 5 4 3  1 1 1  6 5 4 \n" +
                    " 4 3 2  7 6 5  4 4 3 \n" +
                    " 7 6 5  1 1 0  2 2 1 \n");
  }

  /**
   * Checks that the filter method throws an exception when given a null.
   */
  @Test
  public void testFilterException1() {
    try {
      exImage.filter(null);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid kernel, filtering kernels must have positive odd dimensions " +
              "and be non-null.", e.getMessage());
    }
  }

  /**
   * Checks that the filter method throws an exception when given a non square array.
   */
  @Test
  public void testFilterException2() {
    try {
      exImage.filter(new double[3][2]);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid kernel, filtering kernels must be square.", e.getMessage());
    }
  }

  /**
   * Checks that the filter method throws an exception when given a array less than 1.
   */
  @Test
  public void testFilterException3() {
    try {
      exImage.filter(new double[0][0]);
      fail("Did not throw exception");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid kernel, filtering kernels must have positive odd dimensions " +
              "and be non-null.", e.getMessage());
    }
  }

  /**
   * Tests that the filter method correctly produces results for the given 2D array.
   */
  @Test
  public void testFilter() {
    assertEquals(exImage.filter(new double[3][3]).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n" +
                    " 0 0 0  0 0 0  0 0 0 \n");
    assertEquals(exImage.filter(blur).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 0 1 0  1 0 1  0 1 1 \n" +
                    " 1 2 2  2 0 2  1 1 1 \n" +
                    " 0 2 0  1 1 1  0 0 0 \n");
    assertEquals(exImage.filter(sharpen).toString(),
            "P3\n" +
                    "3 3\n" +
                    "9\n" +
                    " 2 1 0  2 2 1  0 4 4 \n" +
                    " 0 4 5  9 9 10  3 4 2 \n" +
                    " 3 7 2  2 5 5  4 1 3 \n");
  }

  /**
   * Tests that the writeToBuffered method correctly produces a bufferedimage.
   */
  @Test
  public void testWriteToBuffered() {
    BufferedImage exBuff = exImage.writeToBuffered(5);
    assertEquals(exBuff.getRGB(0, 0), new Color(3, 5, 1).getRGB());
  }
}
