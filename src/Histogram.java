import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class to represent and calculate Histograms from RGB images.
 */
public class Histogram {
  BufferedImage image;
  int[] red;
  int[] blue;
  int[] green;
  int[] intensity;

  /**
   * Construct a histogram representing the various components of a given RGB image (R, G, B, and
   * intensity).
   *
   * @param image the image to be considered.
   */
  public Histogram(BufferedImage image) {
    this.image = image;
    this.red = new int[256];
    this.green = new int[256];
    this.blue = new int[256];
    this.intensity = new int[256];

    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        Color rgb = new Color(this.image.getRGB(x, y));
        int r = rgb.getRed();
        int g = rgb.getGreen();
        int b = rgb.getBlue();
        int i = (r + g + b) / 3;

        this.red[r] = this.red[r] + 1;
        this.green[g] = this.green[g] + 1;
        this.blue[b] = this.blue[b] + 1;
        this.intensity[i] = this.intensity[i] + 1;
      }
    }
  }

  /**
   * Generates the red component histogram as an image.
   *
   * @return a BufferedImage showing this histogram with frequency bars in red and values increasing
   * along the X axis from 0 to 255 left to right.
   */
  public BufferedImage showRed() {
    return this.renderChart(this.red, Color.RED.getRGB());
  }

  /**
   * Generates the green component histogram as an image.
   *
   * @return a BufferedImage showing this histogram with frequency bars in green and values
   * increasing along the X axis from 0 to 255 left to right.
   */
  public BufferedImage showGreen() {
    return this.renderChart(this.green, Color.GREEN.getRGB());
  }

  /**
   * Generates the blue component histogram as an image.
   *
   * @return a BufferedImage showing this histogram with frequency bars in blue and values
   * increasing along the X axis from 0 to 255 left to right.
   */
  public BufferedImage showBlue() {
    return this.renderChart(this.blue, Color.BLUE.getRGB());
  }

  /**
   * Generates the intensity component histogram as an image.
   *
   * @return a BufferedImage showing this histogram with frequency bars in grey and values
   * increasing along the X axis from 0 to 255 left to right.
   */
  public BufferedImage showIntensity() {
    return this.renderChart(this.intensity, Color.GRAY.getRGB());
  }

  /**
   * Renders a histogram chart of a given array of frequency values, scaling the final image to
   * only be 100 pixels tall but maintain frequency bar proportions, and coloring the frequency bars
   * according to the given RGB color integer (a black background is always used.
   *
   * @param frequencies the data to be charted.
   * @param color       the color to be used for the bars.
   * @return a BufferedImage displaying the final histogram chart.
   */
  private BufferedImage renderChart(int[] frequencies, int color) {
    int height = 0;
    for (int f : frequencies) {
      if (height < f) {
        height = f;
      }
    }

    double scale = 100.0 / height;
    height *= scale;
    for (int v = 0; v < 256; v++) {
      frequencies[v] = (int) (frequencies[v] * scale);
    }

    BufferedImage chart = new BufferedImage(256, height, 5);

    for (int v = 0; v < 256; v++) {
      for (int h = height - 1; h > height - frequencies[v]; h--) {
        chart.setRGB(v, h, color);
      }
    }

    return chart;
  }
}