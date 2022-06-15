package chatclient;

import javax.swing.*;

public abstract class Win extends JFrame {

  private int gap = 10;
  private int x, y;

  public Win(String title, int x, int y, int width, int height, int closing) {
    setupFrame(title, x, y, width, height, closing);

  }

  public Win(String title, int width, int height, int closing) {
    this(title, -1, -1, width, height, closing);
  }

  public int getGap() {
    return gap;
  }

  public int setGap(int gap) {
    return this.gap = gap;
  }

  private void setupFrame(String title, int x, int y, int width, int height, int closing) {
    setTitle(title);
    setDefaultCloseOperation(closing);
    setSize(width, height);
    if (x == -1 && y == -1) {
      setLocationByPlatform(true);
    } else {
      setLocation(x, y);
    }
  }

  protected abstract void setupComponents();

  private void setupVisibility() {
    repaint();
    setVisible(true);
  }

  protected void addRow(JComponent... components) {
    int h = 0;
    int rowHeight = 0;
    for (JComponent component : components) {
      int height = component.getPreferredSize().height;
      if (height > rowHeight) {
        rowHeight = height;
      }
    }
    for (JComponent component : components) {
      int freeSpace = rowHeight - component.getPreferredSize().height;
      component.setLocation(x, y + freeSpace / 2);
      component.setSize(component.getPreferredSize());
      add(component);
      x += component.getSize().width + gap;
      if (component.getSize().height > h) {
        h = component.getSize().height;
      }
    }
    x = gap;
    y += h + gap;
  }

  public void createWindow() {
    x = y = gap;
    getContentPane().removeAll();
    setupComponents();
    setupVisibility();
  }
}
