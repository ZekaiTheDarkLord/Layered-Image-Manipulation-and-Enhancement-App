package view;

import imagelayer.ImageLayer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * An GUI version of view that contains an inner class which set up JMenu Bar for this GUI and use
 * JSwing as library.
 */
public class GUIView extends JFrame implements ImageGuiView {

  private JTextArea inputTextArea;
  private PictureProcessingView delegate;
  private JButton blur;
  private JButton sharpening;
  private JButton greyScale;
  private JButton sepia;
  private JButton mosaic;
  private JButton downScaling;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton LayerLoadButton;
  private JButton LayerSaveButton;
  private JButton createLayer;
  private JButton deleteLayer;
  private JButton setCurrent;
  private JButton visible;
  private JButton invisible;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel displayLayers;//in charge of displaying layers
  private JLabel displayMessage;
  private JLabel imageLabel;
  private GUIMenu GUIMenu;

  /**
   * Construct a frame that contains all functionality documented in Feature interface with a border
   * layout.
   */
  public GUIView() {
    super();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    delegate = new SimpleView();

    setTitle("Image Operation");
    setSize(500, 400);
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(10, 10));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //Text Area
    inputTextArea = new JTextArea(10, 20);//text area row column?
    inputTextArea.setLineWrap(true); // /n as a sniffer
    JScrollPane textAreaScroll = new JScrollPane(inputTextArea);
    textAreaScroll.setBorder(BorderFactory.createTitledBorder("Pleas Type Input"));
    mainPanel.add(inputTextArea, BorderLayout.SOUTH);
    //Error or Success message
    displayMessage = new JLabel("Message shows up here");
    displayMessage.setFont(new java.awt.Font("Dialog", 1, 20));
    mainPanel.add(displayMessage, BorderLayout.SOUTH);

    //North
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());
    mainPanel.add(topPanel, BorderLayout.NORTH);

    createLayer = new JButton("Create Layer");
    createLayer.setActionCommand("create");
    topPanel.add(createLayer);

    deleteLayer = new JButton("Delete Layer");
    deleteLayer.setActionCommand("delete");
    topPanel.add(deleteLayer);

    setCurrent = new JButton("Choose Layer");
    setCurrent.setActionCommand("current");
    topPanel.add(setCurrent);

    visible = new JButton("Visible Layer");
    visible.setActionCommand("visible");
    topPanel.add(visible);

    invisible = new JButton("Invisible Layer");
    invisible.setActionCommand("invisible");
    topPanel.add(invisible);

    //Center
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Current Layer"));
    imagePanel.setLayout(new FlowLayout());
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imagePanel.add(imageScrollPane);

    displayLayers = new JLabel("Layers will be displayed here");
    mainPanel.add(displayLayers, BorderLayout.EAST);

    setEast();
    GUIMenu = new GUIMenu();
    this.setJMenuBar(GUIMenu);
    pack();
  }

  //A copy from professor's swing demo, did nothing but for testing layout.
  public static void main(String[] args) {
    GUIView.setDefaultLookAndFeelDecorated(false);
    GUIView frame = new GUIView();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
      // handle exception
    }

  }

  private void setEast() {
    //West border

    JPanel leftCommands = new JPanel();
    leftCommands.setBorder(BorderFactory.createTitledBorder("Options"));
    leftCommands.setLayout(new BoxLayout(leftCommands, BoxLayout.Y_AXIS));
    mainPanel.add(leftCommands, BorderLayout.WEST);

    //Operations
    JPanel operation = new JPanel();
    operation.setBorder(BorderFactory.createTitledBorder("Operations"));
    operation.setLayout(new BoxLayout(operation, BoxLayout.Y_AXIS));//left most?

    blur = new JButton("Blur");
    blur.setActionCommand("blur");

    sharpening = new JButton("Sharpening");
    sharpening.setActionCommand("sharpening");

    greyScale = new JButton("Grey Scale");
    greyScale.setActionCommand("greyScale");

    sepia = new JButton("Sepia");
    sepia.setActionCommand("sepia");

    mosaic = new JButton("Mosaic");
    mosaic.setActionCommand("mosaic");

    downScaling = new JButton("Down Scaling");
    downScaling.setActionCommand("downScaling");
    operation.add(blur);
    operation.add(sharpening);
    operation.add(sepia);
    operation.add(greyScale);
    operation.add(mosaic);
    operation.add(downScaling);
    leftCommands.add(operation);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setBorder(BorderFactory.createTitledBorder("In and Out"));
    fileopenPanel.setLayout(new BoxLayout(fileopenPanel, BoxLayout.Y_AXIS));
    leftCommands.add(fileopenPanel);

    fileOpenButton = new JButton("Open an image");
    fileOpenButton.setActionCommand("load");

    LayerLoadButton = new JButton("Open multiple layers");
    LayerLoadButton.setActionCommand("loadAll");

    fileSaveButton = new JButton("Save current Picture");
    fileSaveButton.setActionCommand("save");

    LayerSaveButton = new JButton("Save All Pictures");
    LayerSaveButton.setActionCommand("saveAll");

    fileopenPanel.add(fileOpenButton);
    fileopenPanel.add(LayerLoadButton);
    fileopenPanel.add(fileSaveButton);
    fileopenPanel.add(LayerSaveButton);


  }

  @Override
  public void renderImage(BufferedImage image) {
    if (image != null) {
      ImageIcon icon = new ImageIcon(image);

      imageLabel.invalidate();

      imageLabel.setIcon(icon);

      imageLabel.repaint();
      imageLabel.revalidate();
    } else {
      imageLabel.setIcon(null);
    }
  }

  @Override
  public void addFeatures(Features features) {
    if (features == null) {
      throw new IllegalArgumentException("empty feature");
    }
    blur.addActionListener(evt -> features.blur());
    sharpening.addActionListener(evt -> features.sharpening());
    sepia.addActionListener(evt -> features.sepia());
    greyScale.addActionListener(evt -> features.greyScale());
    mosaic.addActionListener(evt -> features
        .mosaic(JOptionPane.showInputDialog("Please indicate the seeds for mosaic.")));
    downScaling.addActionListener(
        evt -> {
          String[] a = JOptionPaneMultiInput();
          features.downScaling(a[0], a[1]);
        });

    fileSaveButton.addActionListener(evt -> features.save(fileSave(), chooseFormat()));
    fileOpenButton.addActionListener(evt -> features
        .load(fileChooser(new String[]{"jpg", "jpeg", "ppm", "txt", "png", "gif"})));
    LayerSaveButton.addActionListener(evt -> saveAll(features));
    LayerLoadButton.addActionListener(evt -> features
        .loadAll(fileChooser(new String[]{"txt"})));

    createLayer.addActionListener(
        evt -> features.create(JOptionPane.showInputDialog("Please indicate a new Layer's name.")));
    deleteLayer
        .addActionListener(evt -> features.delete(optionChooser(features.getLayers(), "delete")));
    setCurrent.addActionListener(
        evt -> features.current(optionChooser(features.getLayers(), "set as current")));
    visible
        .addActionListener(
            evt -> features.visible(optionChooser(features.getLayers(), "be visible")));
    invisible.addActionListener(
        evt -> features.invisible(optionChooser(features.getLayers(), "be invisible")));
    this.GUIMenu.addFeatures(features);
  }

  private String fileChooser(String[] acceptable) {
    String path = "";
    String description = "";
    final JFileChooser fchooser = new JFileChooser(".");
    for (String str : acceptable) {

      description += str + " .";
    }
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        description, acceptable);
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
    }
    return path;
  }

  private String chooseFormat() {
    String[] options = {".jpg", ".ppm", ".png", ".jpeg", ".txt"};
    int retvalue = JOptionPane
        .showOptionDialog(GUIView.this, "Please choose a format for image.", "Options",
            JOptionPane.YES_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    if (retvalue == JFileChooser.ERROR_OPTION) {
      return "";

    }
    return options[retvalue];
  }

  private String fileSave() {

    JFileChooser fchooser = new JFileChooser(".");
    String path = "";
    fchooser.setDialogTitle("Please indicate place to save current image");
    fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int retvalue = fchooser.showSaveDialog(GUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      path = f.getAbsolutePath();
    }
    return path;
  }

  private String optionChooser(String[] options, String show) {
    if (options.length == 0) {
      return "";
    }
    int retvalue = JOptionPane
        .showOptionDialog(GUIView.this, "Please choose a layer to " + show, "Options",
            JOptionPane.YES_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    if (retvalue == JFileChooser.ERROR_OPTION) {
      return "";

    }
    return options[retvalue];
  }

  private boolean yesOrno() {
    String[] options = {"yes", "no"};
    String show = "By saving all, all the layers will be removed and reset";
    int retvalue = JOptionPane
        .showOptionDialog(GUIView.this, show, "Warning",
            JOptionPane.YES_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    return retvalue == 0;
  }

  private void saveAll(Features features) {
    if (yesOrno()) {
      features.saveAll();
    }
  }

  //There is a possibility that user give empty input
  private String[] JOptionPaneMultiInput() {
    JTextField xField = new JTextField(5);
    JTextField yField = new JTextField(5);

    String[] answer = new String[2];
    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("width:"));
    myPanel.add(xField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("height:"));
    myPanel.add(yField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Please Enter new width and height values", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      answer[0] = xField.getText();
      answer[1] = yField.getText();
    }
    return answer;
  }

  @Override
  public void renderLayer(List<String> layers, Map<String, ImageLayer> data) {
    delegate = new SimpleView();
    delegate.renderLayer(layers, data);
    String str = delegate.peekOutput().toString();
    String result = str.replaceAll("\n", "<br>");
    displayLayers.setText("<html>" + result + "<html>");
  }

  @Override
  public void renderMessage(String message) {
    Objects.requireNonNull(message);
    displayMessage.setText(message + "\n");
  }

  @Override
  public Appendable peekOutput() {
    return delegate.peekOutput();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setVisible() {
    setVisible(true);
  }

  @Override
  public void clear() {
    this.displayMessage.setText("Message shows up here");
    this.displayLayers.setText("Layers will be displayed here");
    this.imageLabel.setIcon(null);
  }

  @Override
  public void renderWarning(String warn) {
    JOptionPane.showMessageDialog(GUIView.this, warn, "Warning", JOptionPane.WARNING_MESSAGE);
  }

  private class GUIMenu extends JMenuBar {

    private JMenuItem blur;
    private JMenuItem sharpening;
    private JMenuItem greyScale;
    private JMenuItem sepia;
    private JMenuItem mosaic;
    private JMenuItem downScaling;
    private JMenuItem fileOpenButton;
    private JMenuItem fileSaveButton;
    private JMenuItem LayerLoadButton;
    private JMenuItem LayerSaveButton;
    private JMenuItem createLayer;
    private JMenuItem deleteLayer;
    private JMenuItem setCurrent;
    private JMenuItem visible;
    private JMenuItem invisible;

    private GUIMenu() {
      add(createLayerMenu());
      add(createOperation());
      add(createLoadAndSave());
      setVisible(true);
    }

    private JMenu createLayerMenu() {
      JMenu menu = new JMenu("Layer(F)");
      menu.setMnemonic(KeyEvent.VK_F);    //设置快速访问符
      createLayer = new JMenuItem("Create Layer(C)", KeyEvent.VK_C);
      createLayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      menu.add(createLayer);
      menu.addSeparator();

      deleteLayer = new JMenuItem("Delete Layer(O)", KeyEvent.VK_D);
      deleteLayer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
      menu.add(deleteLayer);

      setCurrent = new JMenuItem("Set Current(S)", KeyEvent.VK_S);
      setCurrent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      menu.add(setCurrent);

      visible = new JMenuItem("Visible (V)", KeyEvent.VK_V);
      visible.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
      menu.add(visible);

      invisible = new JMenuItem("Invisible(I)", KeyEvent.VK_I);
      invisible.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
      menu.add(invisible);
      return menu;
    }

    private JMenu createOperation() {
      JMenu menu = new JMenu("Operation(O)");
      menu.setMnemonic(KeyEvent.VK_O);

      blur = new JMenuItem("Blur");
      menu.add(blur);
      sharpening = new JMenuItem("Sharpening");
      menu.add(sharpening);
      greyScale = new JMenuItem("Grey Scale");
      menu.add(greyScale);
      sepia = new JMenuItem("Sepia ");
      menu.add(sepia);
      mosaic = new JMenuItem("Mosaic");
      menu.add(mosaic);
      downScaling = new JMenuItem("Down Scaling");
      menu.add(downScaling);
      return menu;
    }

    private JMenu createLoadAndSave() {
      JMenu menu = new JMenu("Load and Save(L)");
      menu.setMnemonic(KeyEvent.VK_L);
      fileOpenButton = new JMenuItem("Open an image");
      menu.add(fileOpenButton);
      fileSaveButton = new JMenuItem("Save current Picture");
      menu.add(fileSaveButton);
      LayerSaveButton = new JMenuItem("Save All Pictures");
      menu.add(LayerSaveButton);
      LayerLoadButton = new JMenuItem("Open multiple layers ");
      menu.add(LayerLoadButton);
      return menu;

    }

    private void addFeatures(Features features) {
      if (features == null) {
        throw new IllegalArgumentException("empty feature");
      }
      blur.addActionListener(evt -> features.blur());
      sharpening.addActionListener(evt -> features.sharpening());
      sepia.addActionListener(evt -> features.sepia());
      greyScale.addActionListener(evt -> features.greyScale());
      mosaic.addActionListener(evt -> features
          .mosaic(JOptionPane.showInputDialog("Please indicate the seeds for mosaic.")));
      downScaling.addActionListener(
          evt -> {
            String[] a = JOptionPaneMultiInput();
            features.downScaling(a[0], a[1]);
          });

      fileSaveButton.addActionListener(evt -> features.save(fileSave(), chooseFormat()));
      fileOpenButton.addActionListener(evt -> features
          .load(fileChooser(new String[]{"jpg", "jpeg", "ppm", "txt", "png", "gif"})));
      LayerSaveButton.addActionListener(evt -> saveAll(features));
      LayerLoadButton.addActionListener(evt -> features
          .loadAll(fileChooser(new String[]{"txt"})));

      createLayer.addActionListener(
          evt -> features
              .create(JOptionPane.showInputDialog("Please indicate a new Layer's name.")));
      deleteLayer
          .addActionListener(evt -> features.delete(optionChooser(features.getLayers(), "delete")));
      setCurrent.addActionListener(
          evt -> features.current(optionChooser(features.getLayers(), "set as current")));
      visible
          .addActionListener(
              evt -> features.visible(optionChooser(features.getLayers(), "be visible")));
      invisible.addActionListener(
          evt -> features.invisible(optionChooser(features.getLayers(), "be invisible")));
    }
  }
}
