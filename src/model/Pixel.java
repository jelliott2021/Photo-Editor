package model;

/**
 * Represents a standard RGB pixel.
 */
public class Pixel implements IPixel {
  final private int r;
  final private int g;
  final private int b;

  /**
   * Constructs a pixel with the given r, g, and b values.
   *
   * @param r the red component.
   * @param g the blue component.
   * @param b the green component.
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("r, g, or b cannot be negative");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Returns the red value of the pixel.
   *
   * @return the r int.
   */
  public int getR() {
    return r;
  }

  /**
   * Returns the green value of the pixel.
   *
   * @return the g int.
   */
  public int getG() {
    return g;
  }

  /**
   * Returns the blue value of the pixel.
   *
   * @return the b int.
   */
  public int getB() {
    return b;
  }
}
