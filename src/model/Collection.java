package model;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

public class Collection {
  private final Map<String, IImage> images;


  public Collection(Map<String, IImage> images) {
    this.images = images;
  }

  public Collection() {
    this.images = new HashMap<>();
  }

  public void add(String name, IImage image) {
    this.images.put(name, image);
  }
/*
  public void save(String path, String name) {
    String type;
    try {
      type = path.split(".")[1];
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Path doesn't have extension");
    }
    switch(type) {
      case "ppm":
        FileWriter writer = new FileWriter(path);
        writer.write(this.images.get(name).toString());
        writer.close();
        break;
      case "jpeg":
        ImageIO.write(this.images.get(name), "jpeg", 1);
        break;
      default:
        break;
    }
  }
 */
}
