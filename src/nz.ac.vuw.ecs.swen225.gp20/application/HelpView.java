package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class HelpView extends JDialog {

  public HelpView(JFrame owner){
    super(owner);
    this.setTitle("Help - Chips Among Us");
    this.setSize(640, 480);
    this.setMinimumSize(new Dimension(320, 480));
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JEditorPane helpViewer = new JEditorPane();
    URL helpFileUrl = null;
    try {
      helpFileUrl = new File("assets/index.html").toURI().toURL();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      helpViewer.setPage(helpFileUrl);
    } catch (IOException e) {
      e.printStackTrace();
    }
    helpViewer.setEditable(false);
    JScrollPane helpViewer_scrollable = new JScrollPane(helpViewer);
    helpViewer.setCaretPosition(0);
    helpViewer_scrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    this.add(helpViewer_scrollable);
  }
}
