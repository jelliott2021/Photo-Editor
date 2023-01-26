package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A class to hold tests for Pixel.
 */
public class PixelTest {
  IPixel pixel = new Pixel(1, 2, 3);

  /**
   * Checks that the three argument constructor throws exceptions when given a negative r value.
   */
  @Test
  public void testConstructor1() {
    try {
      IPixel testPixel = new Pixel(-1, 3, 5);
      fail("Did not throw an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("r, g, or b cannot be negative", e.getMessage());
    }
  }

  /**
   * Checks that the three argument constructor throws exceptions when given a negative g value.
   */
  @Test
  public void testConstructor2() {
    try {
      IPixel testPixel = new Pixel(10, -6, 5);
      fail("Did not throw an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("r, g, or b cannot be negative", e.getMessage());
    }
  }

  /**
   * Checks that the three argument constructor throws exceptions when given a negative b value.
   */
  @Test
  public void testConstructor3() {
    try {
      IPixel testPixel = new Pixel(3, 5, -10);
      fail("Did not throw an exception");
    } catch (IllegalArgumentException e) {
      assertEquals("r, g, or b cannot be negative", e.getMessage());
    }
  }

  /**
   * Checks that the getR method correctly returns the r value.
   */
  @Test
  public void testGetR() {
    assertEquals(pixel.getR(), 1);
  }

  /**
   * Checks that the getR method correctly returns the g value.
   */
  @Test
  public void testGetB() {
    assertEquals(pixel.getG(), 2);
  }

  /**
   * Checks that the getR method correctly returns the b value.
   */
  @Test
  public void testGetG() {
    assertEquals(pixel.getB(), 3);
  }
}
