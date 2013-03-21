package svg;

import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Bounds;

/**
 * @author Sandor_Bencze
 */

public class Utils {
}

/**
 * Adjusts the the given color with the given offset. The offset must be between 0 and 1.
 * If the offset is positive, the color is brightened, if is negative is darkened.
 * @return the adjusted color.
 */
public function adjust(color:Color, offset:Float):Color {
  /** Adjusted red. */
  def R = color.red + offset;
  def RED = if(R < 0) 0 else if(R > 1) 1 else R;
  /** Adjusted green. */
  def G = color.green + offset;
  def GREEN = if(G < 0) 0  else if(G > 1) 1 else G;
  /** Adjusted blue. */
  def B = color.blue + offset;
  def BLUE = if(B < 0) 0 else if(B > 1) 1 else B;
  /** Adjusted color. */
  Color.color(RED, GREEN, BLUE, color.opacity);
}

/**
 * Inverts the the given color.
 * @return the inverted color.
 */
public function invert(color:Color):Color {
  /** Adjusted red. */
  def RED = 1 - color.red;
  /** Adjusted green. */
  def GREEN = 1 - color.green;
  /** Adjusted blue. */
  def BLUE = 1 - color.blue;
  /** Adjusted color. */
  Color.color(RED, GREEN, BLUE, color.opacity);
}

public function localToAncestor(node:Node, ancestorDepth:Integer):Bounds {
  var parent = node;
  var bounds = node.boundsInLocal;
  for(j in [0..<ancestorDepth]) {
    parent = parent.parent;
    bounds = parent.localToScene(bounds);
  }
  bounds
}

/**
 * 
 */
public class Rollover {
  /** The target node of the rollover effect. */
  public-init var target:Node;
  /** The value of opacity. */
  public-init var opacity:Float = 0.8;

  def fader = Timeline {
    keyFrames: [
      at (0s) { target.opacity => 1.0 },
      at (0.2s) { target.opacity => opacity }
    ]
  }

  init {
    target.onMouseEntered = function(evt:MouseEvent) {
      fader.rate = 1.0;
      fader.play();
    }
    target.onMouseExited = function(evt:MouseEvent) {
      fader.rate = -1.0;
      fader.play();
    }
  }
}
