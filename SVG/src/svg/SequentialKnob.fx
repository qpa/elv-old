package svg;

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.stage.*;
import javafx.util.Math.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import svg.Utils.*;
import javafx.scene.text.Font;

/**
 * Knob for sequential selection.
 * @author Sandor_Bencze
 */

public class SequentialKnob extends CustomNode {
  /** The sequence of items. */
  public var items:Object[] = [1, 2, 3, 4, 5] on replace {
    workValue = 0;
    workAngle = 0;
    currentAngle = 0;
  }
  /** Count of items. */
  def count:Integer = bind sizeof items;

  /** The selected index. */
  public var selectedIndex = 0 on replace {
    /** Check limits. */
    if (selectedIndex < 0) { selectedIndex = 0 }
    if (selectedIndex > sizeof items) { selectedIndex = sizeof items }
    // Set current angle and working values.
    workValue = selectedIndex;
    workAngle = workValue * ((endAngle - startAngle) / (count - 1));
    currentAngle = workAngle;
  }

  /** The selected item. */
  public-read var selectedItem = bind items[selectedIndex];

  /** The gauge starting angle (between -180 and 180). */
  public var startAngle:Float = -120;
  /** Angle correction. */
  def angleCorrection =  bind startAngle - 90;

  /** The gauge ending angle (between -180 and 180). */
  public var endAngle:Float = 120;

  /**
   * The width of the knob (without the gauge).
   * To extend  the visibility of the gauge, increase the width of the knob.
   */
  public var knobWidth:Float = 20.0;

  /** The width of the knob stroke. */
  public var knobStrokeWidth:Float = 2.5;

  /** The color of the knob. */
  public var knobColor:Color = Color.DARKGREY;
  
  /** The fill of the gauge. */
  public var gaugeColor:Paint = Color.BLACK;

  /** The label text.  */
  public var text = "";

  /** The label text angle (between -180 and 180). */
  public var textAngle:Float = -90.0;

  /** The working value. */
  var workValue:Float = 0 on replace {
    selectedIndex = round(workValue);
  }
  /** Current angle (0-360) in the gauge. */
  var currentAngle:Float on replace {
    workValue = currentAngle / ((endAngle - startAngle) / (count - 1));
  };
  /** Working angle in the gauge. */
  var workAngle:Float;
  /** Previous mouse position. */
  var prevX:Float;
  var prevY:Float;

  override protected function create():Node {
    def knobControl = Group {
      blocksMouse: true
      content: [
        knob,
        gauge,
        label
      ]
      onMousePressed: function(evt:MouseEvent) {
        prevX = evt.x;
        prevY = evt.y;
      }
      onMouseDragged: function(evt:MouseEvent) {
        // calculate angle to the previous mouse position
        var a1 = getAngle(knobWidth / 2, knobWidth / 2, prevX, prevY );
        // calculate angle to the current mouse position
        var a2 = getAngle(knobWidth / 2, knobWidth / 2, evt.x, evt.y );
        // store current mouse position as previous position
        prevX = evt.x;
        prevY = evt.y;
        // difference between angles (previous and current)
        if (a2 - a1 > 180) { a2 = a2 - 360 };
        if (a2 - a1 < -180) { a1 = a1 - 360 };
        var variation = a2 - a1;
        // accumulates angle variations in the mouse position
        workAngle += variation;

        if(workAngle < 0) {
          currentAngle = 0;
        } else if(workAngle > endAngle - startAngle) {
          currentAngle = endAngle - startAngle;
        } else {
          currentAngle = workAngle;
        }
      }
      onMouseReleased: function(e) {
        workValue = round(workValue);
        currentAngle = workValue * ((endAngle - startAngle) / (count - 1));
        workAngle = currentAngle;
      }
    }
    knobControl.translateX = knobControl.layoutBounds.width / 2 + 2;
    knobControl.translateY = knobControl.layoutBounds.height / 2 + 2;
    knobControl
  }

  /** The knob. */
  def knob:Node[] = {
    var knobElements:Node[] = [];
    // Create the knob disk.
    def disk = Circle {
      radius: bind knobWidth/2
      fill: bind if(knobColor == null) null else LinearGradient {
        startX: 0
        startY: 0
        endX: 0
        endY: 1
        stops: [
          Stop { offset: 0.0 color: adjust(knobColor, 0.2) },
          Stop { offset: 0.5 color: knobColor },
          Stop { offset: 1.0 color: adjust(knobColor, 0.1) }
        ]
      }
      stroke: bind if(knobColor == null) null else  LinearGradient {
        startX: 0
        startY: 0
        endX: 0
        endY: 1
        stops: [
          Stop { offset: 0.0 color: adjust(knobColor, -0.2) },
          Stop { offset: 1.0 color: adjust(knobColor, 0.2) }
        ]
      }
      strokeWidth: bind knobStrokeWidth
      onMouseEntered: function(evt:MouseEvent) {
        opacity = 0.8
      }
      onMouseExited: function(evt:MouseEvent) {
        opacity = 1
      }
    }
    insert disk into knobElements;
    // Create indicator origin.
    insert Circle {
      fill: bind gaugeColor
      radius: 3
    } into knobElements;
    // Create indicator pointer.
    insert Line {
      transforms: Rotate {
        angle: bind currentAngle + angleCorrection
      }
      endX: bind knobWidth / 2
      endY: 0
      stroke: bind gaugeColor
      strokeWidth: 2
    } into knobElements;
    knobElements;
  }

  /** The gauge. */
  def gauge:Node[] = {
    var gaugeDots:Node[] = [];
    var angle = min(endAngle - startAngle, 360);
    // Degrees between gauge lines.
    var gaugeStep = angle / (count - 1) ;
    for (i in [0..<count]) {
      // Math to calculate centers.
      var rad = toRadians(i * gaugeStep + angleCorrection);
      var hor = cos(rad);
      var ver = sin(rad);
      def dot =  Circle {
        var radialDistance = knobWidth / 2 + 4
        centerX: bind hor * (radialDistance)
        centerY: bind ver * (radialDistance)
        radius: 2
        fill: bind gaugeColor
        smooth: true
      }
      def point =  Circle {
        var radialDistance = knobWidth / 2 + 4
        centerX: bind hor * (radialDistance)
        centerY: bind ver * (radialDistance)
        radius: 1
        fill: bind knobColor
        smooth: true
      }
      def gaugeDot:Group = Group {
        cursor: Cursor.HAND
        content: [ dot, point ]
        onMouseClicked: function(evt:MouseEvent) {
          currentAngle = i * gaugeStep;
        }
        onMouseEntered: function(evt:MouseEvent) {
          labelText = "{items[i].toString()}";
          label.parent.requestLayout();
        }
        onMouseExited: function(evt:MouseEvent) {
          labelText = text;
          label.parent.requestLayout();
        }
      }
      insert gaugeDot into gaugeDots;
    }
    gaugeDots
  }

  /** Teh varying text of the label. */
  var labelText:String = text on replace {
  }

  /** The label. Used to also to display the value of the gauge items. */
  def label:Label = Label {
    width: bind labelText.length() * label.font.size
    font: Font {size: 11}
    text: bind labelText
    textFill: bind gaugeColor
    translateX: bind - label.width / 2
    translateY: bind knobWidth / 2 + 4
    hpos: HPos.CENTER
//    transforms: [Rotate {angle: textAngle}]
  }

  /**
   * Calculates angle between two positions and the center of the knob.
   * @param x1 previous horizontal coordinate of the mouse
   * @param y1 previous vertical coordinate of the mouse
   * @param x2 current horizontal coordinate of the mouse
   * @param y2 current vertical coordinate of the mouse
   * @return angle between the two positions and the center of the knob (0-360)
   */
  function getAngle(x1:Float, y1:Float, x2:Float, y2:Float):Float {
    // to avoid a division by zero
    var add = if (y2 - y1 == 0) then 0.0001 else 0.0;
    // calculate the angle between the two points
    var deg = toDegrees(atan(( x2 - x1 ) / ( y2 - y1 + add )));
    // if current vertical coordinate < than previous vertical coordinate
    if (y2 < y1) { // if calculated degrees <0
      if (deg < 0) { // opposite sign
        deg = -deg
      } else { // complement to 360 degrees
        deg = 360 - deg
      }
    } else { // if current vertical coordinate >= than previous vertical coordinate
      // complement to 180 degrees
      deg = 180 - deg
    };
    deg;
  }
}

function run() {
  Stage {
    width: 300
    height: 300
    scene: Scene {
      content: [
        SequentialKnob {
          translateX: 100
          translateY: 100
        }
      ]
    }
  }
}
