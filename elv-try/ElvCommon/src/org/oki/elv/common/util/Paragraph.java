package org.oki.elv.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Paragraph. A label from DOT (.) separated depths and a text. Ex.: <code><b>1.2.3=Text</b></code>.
 * A paragraph can have parent paragraph and children paragraphs, so a hierarchy can be represented.
 * @author Elv
 */
public abstract class Paragraph {
  /** Separator. */
  public static final String SEPARATOR = "=";
  /** Dot. */
  public static final String DOT = ".";
  /** Comma. */
  public static final String COMMA = ",";
  /** Space. */
  public static final String SPACE = " ";
  /** Bar. */
  public static final String BAR = "|";
  /** Default line. */
  public static final String DEFAULT_LABEL = "";
  /** Default line. */
  public static final String DEFAULT_TEXT = " <*>";
  
  /** The depth of the paragraph. */
  protected int depth;
  /** The label of the paragraph. */
  protected String label;
  /** The text of the paragraph. */
  protected String text;
  /** The parent of the paragraph. */
  protected Paragraph parent;
  /** The children of the paragraph. */
  protected List<Paragraph> children;
  
  /**
   * Constructor.
   * @param label
   * @param text
   */
  public Paragraph(String label, String text) {
    this.depth = label.split(Pattern.quote(DOT)).length;
    this.label = label;
    this.text = text;
  }

  /**
   * Getter of the depth.
   * @return the depth.
   */
  public final int getDepth() {
    return depth;
  }

  /**
   * Getter of the label.
   * @return the label.
   */
  public final String getLabel() {
    return label;
  }

  /**
   * Getter of the text.
   * @return the text.
   */
  public String getText() {
    return text;
  }
  
  /**
   * Getter of the comparable part of the text, which is used by the <code>load</code> method.
   * @return a string for comparing.
   */
  protected abstract String getComparableText();

  /**
   * Getter of the parent.
   * @return the parent.
   */
  public Paragraph getParent() {
    return parent;
  }

  /**
   * Setter of the parent.
   * @param parent the parent to set.
   */
  public void setParent(Paragraph parent) {
    this.parent = parent;
  }

  /**
   * Getter of the children.
   * @return the children.
   */
  public List<Paragraph> getChildren() {
    return children;
  }

  /**
   * Setter of the children.
   * @param children the children to set.
   */
  public void setChildren(List<Paragraph> children) {
    this.children = children;
  }

  /**
   * Tester of children.
   * @return true, if this paragraph has no children.
   */
  public boolean isLeaf() {
    return children == null || children.size() == 0;
  }
  
  /**
   * Comparator of depths.
   * @param paragraph
   * @return true, if this paragraph has the same depth as the given paragraph.
   */
  public boolean isPeer(Paragraph paragraph) {
    return getDepth() == paragraph.getDepth(); 
  }
  
  /**
   * Comparator of depths.
   * @param paragraph
   * @return true, if this paragraph is deeper as the given paragraph.
   */
  public boolean isDeeper(Paragraph paragraph) {
    return getDepth() > paragraph.getDepth(); 
  }

  @Override
  public String toString() {
    return label + SEPARATOR + getText();
  }
  
  /**
   * Hierarchy loader.
   * @param fileReader
   * @param isDefault if true, the default line is read.
   * @param collator for creating sorted child lists with <code>getComparableText()</code>.
   * @throws IOException 
   */
  public final void load(BufferedReader fileReader, boolean isDefault, Collator collator) throws IOException {
    Paragraph rootParagraph = fromLine(DEFAULT_LABEL + SEPARATOR + DEFAULT_TEXT, true);
    Paragraph previousParagraph = rootParagraph;
    String line;
    while((line = fileReader.readLine()) != null) {
      Paragraph lineParagraph = fromLine(line, isDefault);
      for(Paragraph iteratorParagraph = previousParagraph; iteratorParagraph != null; iteratorParagraph = iteratorParagraph.getParent()) {
        if(lineParagraph.isDeeper(iteratorParagraph)) {
          lineParagraph.setParent(iteratorParagraph);
          // Create sorted child list.
          int insertIndex = 0;
          if(collator != null) {
            for(Paragraph iP : iteratorParagraph.children) {
              insertIndex++;
              if(collator.compare(lineParagraph.getComparableText(), iP.getComparableText()) <= 0) {
                break;
              }
            }
          }
          else {
            insertIndex = iteratorParagraph.children.size();
          }
          iteratorParagraph.children.add(insertIndex, lineParagraph);
        }
      }
      previousParagraph = lineParagraph;
    }
    children = rootParagraph.children;
  }
  
  /**
   * Hierarchy storer.
   * @param fileWriter
   */
  public final void store(PrintWriter fileWriter) {
    store(children, fileWriter);
  }
  
  /**
   * Recursive storer.
   * @param children
   * @param fileWriter
   */
  private void store(List<Paragraph> children, PrintWriter fileWriter) {
    if(children == null) {
      return;
    }
    for(Paragraph iteratorChild : children) {
      if(iteratorChild.isLeaf()) {
        fileWriter.println(iteratorChild.label + SEPARATOR + iteratorChild.getText());
      }
      else {
        store(iteratorChild.children, fileWriter);
      }
    }
  }
  
  /**
   * Creates a <code>Paragraph</code> from the given string.
   * @param line a string representation of a Paragraph.
   * @param isDefault if true, the default line is read.
   * @return the constructed Paragraph.
   */
  protected abstract <T extends Paragraph> T fromLine(String line, boolean isDefault);
}
