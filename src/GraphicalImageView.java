import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GraphicalImageView extends JFrame implements ActionListener, ItemListener,
        ListSelectionListener, PropertyChangeListener {
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  private BufferedImage currentImage;
  private JPanel imagePanel;

  private JPanel histogramPanel;

  private JButton fileOpenButton;
  private JButton fileSaveButton;

  private JButton brightenButton;
  private JButton redButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton lumaButton;
  private JButton intensityButton;
  private JButton valueButton;
  private JButton horizontalButton;
  private JButton verticalButton;
  private JButton darkenButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;

  public GraphicalImageView() {
    super();
    this.currentImage = new BufferedImage(1, 1, 5);
    setTitle("Image Processor");
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    histogramPanel = new JPanel();
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histograms"));
    histogramPanel.setLayout(new GridLayout(1, 4, 10, 10));
    mainPanel.add(histogramPanel);

    //show an image JLabel
    JLabel image = new JLabel(new ImageIcon(this.currentImage));
    imagePanel.add(image);

    //File boxes
    JPanel filePanel = new JPanel();
    filePanel.setBorder(BorderFactory.createTitledBorder("File Operations"));
    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.LINE_AXIS));
    filePanel.setMaximumSize(new Dimension(800, 100));
    mainPanel.add(filePanel);

    //Command boxes
    JPanel commandPanel = new JPanel();
    commandPanel.setBorder(BorderFactory.createTitledBorder("File Operations"));
    commandPanel.setLayout(new GridLayout(2, 7));
    commandPanel.setMaximumSize(new Dimension(800, 100));
    mainPanel.add(commandPanel);

    //file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    filePanel.add(fileOpenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenPanel.add(fileOpenButton);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    filePanel.add(filesavePanel);
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    filesavePanel.add(fileSaveButton);

    //Image brighten
    JPanel brightenPanel = new JPanel();
    brightenPanel.setLayout(new FlowLayout());
    commandPanel.add(brightenPanel);
    brightenButton = new JButton("Brighten");
    brightenButton.setActionCommand("brighten");
    brightenPanel.add(brightenButton);

    //Image darken
    JPanel darkenPanel = new JPanel();
    darkenPanel.setLayout(new FlowLayout());
    commandPanel.add(darkenPanel);
    darkenButton = new JButton("Darken");
    darkenButton.setActionCommand("darken");
    darkenPanel.add(darkenButton);

    //Image red
    JPanel redPanel = new JPanel();
    redPanel.setLayout(new FlowLayout());
    commandPanel.add(redPanel);
    redButton = new JButton("Red");
    redButton.setActionCommand("red-component");
    redPanel.add(redButton);

    //Image green
    JPanel greenPanel = new JPanel();
    greenPanel.setLayout(new FlowLayout());
    commandPanel.add(greenPanel);
    greenButton = new JButton("Green");
    greenButton.setActionCommand("green-component");
    greenPanel.add(greenButton);

    //Image blue
    JPanel bluePanel = new JPanel();
    bluePanel.setLayout(new FlowLayout());
    commandPanel.add(bluePanel);
    blueButton = new JButton("Blue");
    blueButton.setActionCommand("blue-component");
    bluePanel.add(blueButton);

    //Image luma
    JPanel lumaPanel = new JPanel();
    lumaPanel.setLayout(new FlowLayout());
    commandPanel.add(lumaPanel);
    lumaButton = new JButton("Luma");
    lumaButton.setActionCommand("luma-component");
    lumaPanel.add(lumaButton);

    //Image intensity
    JPanel intensityPanel = new JPanel();
    intensityPanel.setLayout(new FlowLayout());
    commandPanel.add(intensityPanel);
    intensityButton = new JButton("Intensity");
    intensityButton.setActionCommand("intensity-component");
    intensityPanel.add(intensityButton);

    //Image value
    JPanel valuePanel = new JPanel();
    valuePanel.setLayout(new FlowLayout());
    commandPanel.add(valuePanel);
    valueButton = new JButton("Value");
    valueButton.setActionCommand("value-component");
    valuePanel.add(valueButton);

    //Image blur
    JPanel blurPanel = new JPanel();
    blurPanel.setLayout(new FlowLayout());
    commandPanel.add(blurPanel);
    blurButton = new JButton("Blur");
    blurButton.setActionCommand("blur");
    blurPanel.add(blurButton);

    //Image sharpen
    JPanel sharpenPanel = new JPanel();
    sharpenPanel.setLayout(new FlowLayout());
    commandPanel.add(sharpenPanel);
    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("sharpen");
    sharpenPanel.add(sharpenButton);

    //Image horizontal flip
    JPanel horizontalPanel = new JPanel();
    horizontalPanel.setLayout(new FlowLayout());
    commandPanel.add(horizontalPanel);
    horizontalButton = new JButton("Horizontal");
    horizontalButton.setActionCommand("horizontal-flip");
    horizontalPanel.add(horizontalButton);

    //Image vertical flip
    JPanel verticalPanel = new JPanel();
    verticalPanel.setLayout(new FlowLayout());
    commandPanel.add(verticalPanel);
    verticalButton = new JButton("Vertical");
    verticalButton.setActionCommand("vertical-flip");
    verticalPanel.add(verticalButton);

    //Image greyscale
    JPanel greyscalePanel = new JPanel();
    greyscalePanel.setLayout(new FlowLayout());
    commandPanel.add(greyscalePanel);
    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("greyscale");
    greyscalePanel.add(greyscaleButton);

    //Image sepia
    JPanel sepiaPanel = new JPanel();
    sepiaPanel.setLayout(new FlowLayout());
    commandPanel.add(sepiaPanel);
    sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("sepia");
    sepiaPanel.add(sepiaButton);
  }

  /**
   * Displays the given image to the image viewing window of the GUI.
   *
   * @param buffImage the image to be shown.
   */
  public void renderImage(BufferedImage buffImage) {
    this.currentImage = buffImage;
    imagePanel.removeAll();
    imagePanel.add(new JLabel(new ImageIcon(this.currentImage)));
    imagePanel.repaint();
    imagePanel.revalidate();

    Histogram hist = new Histogram(buffImage);

    histogramPanel.removeAll();
    histogramPanel.add(new JLabel(new ImageIcon(hist.showRed())));
    histogramPanel.add(new JLabel(new ImageIcon(hist.showGreen())));
    histogramPanel.add(new JLabel(new ImageIcon(hist.showBlue())));
    histogramPanel.add(new JLabel(new ImageIcon(hist.showIntensity())));
    histogramPanel.repaint();
    histogramPanel.revalidate();
  }

  public JButton getFileLoadButton() {
    return fileOpenButton;
  }

  public JButton getFileSaveButton() {
    return fileSaveButton;
  }

  public JButton getBrightenButton() {
    return brightenButton;
  }

  public JButton getRedButton() {
    return redButton;
  }

  public JButton getGreenButton() {
    return greenButton;
  }

  public JButton getBlueButton() {
    return blueButton;
  }

  public JButton getLumaButton() {
    return lumaButton;
  }

  public JButton getIntensityButton() {
    return intensityButton;
  }

  public JButton getValueButton() {
    return valueButton;
  }

  public JButton getHorizontalButton() {
    return horizontalButton;
  }

  public JButton getVerticalButton() {
    return verticalButton;
  }

  public JButton getDarkenButton() {
    return darkenButton;
  }

  public JButton getBlurButton() {
    return blurButton;
  }

  public JButton getSharpenButton() {
    return sharpenButton;
  }

  public JButton getGreyscaleButton() {
    return greyscaleButton;
  }

  public JButton getSepiaButton() {
    return sepiaButton;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    }
  }

  /**
   * Invoked when an item has been selected or deselected by the user.
   * The code written for this method performs the operations
   * that need to occur when an item is selected (or deselected).
   *
   * @param e the event to be processed
   */
  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  /**
   * Called whenever the value of the selection changes.
   *
   * @param e the event that characterizes the change.
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  /**
   * This method gets called when a bound property is changed.
   *
   * @param evt A PropertyChangeEvent object describing the event source
   *            and the property that has changed.
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {

  }
}
