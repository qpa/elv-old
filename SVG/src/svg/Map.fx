package svg;

import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.fxd.FXDLoader;
import javafx.scene.CustomNode;
import javafx.scene.paint.Color;
import javafx.util.Math.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import svg.Utils.*;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.geometry.HPos;

/**
 * Map, representing a hierachy of areas. The smallest areas are represented by SVGPath shapes,
 * grouped into a hierarchy of denominated regions.
 * @author Sandor_Bencze
 */
public class Map extends CustomNode {
  /** The URL of the map. */
  public-init var url:String;

  /** The color of the map areas. */
  public var fill = Color.rgb(0xB4, 0xC7, 0xB2);

  /** The stroke of the map areas. */
  public var stroke = Color.WHITE;

  /** The color of the map areas. */
  public var selectedFill = bind adjust(fill, 0.1);

  /** The depth of area groups. */
  public-read var depth:Integer = 0;

  /** Highlight darkness. */
  def HIGHLIGHT_DARKNESS = -0.4;
  /** Highlight effect. */
  def HIGHLIGHT = bind InnerShadow {color: adjust(fill, HIGHLIGHT_DARKNESS)};
  /** Selection brightness. */
  def SELECTION_BRIGHTNESS = 0.4;
  /** Selection effect. */
  def SELECTION = ColorAdjust {brightness: SELECTION_BRIGHTNESS};
  /** Highlight selection effect. */
  def HIGHLIGHT_SELECTION = ColorAdjust {brightness: SELECTION_BRIGHTNESS input: HIGHLIGHT};

  /** The currently highlighted map area. You can use the ID of it. */
  public-read var highlightedArea:Node = null on replace previous {
    if(previous != null) {
      if(previous.effect == HIGHLIGHT_SELECTION) {
        previous.effect = SELECTION;
      } else {
        previous.effect = null;
      }
    }
    if(highlightedArea != null) {
      if(highlightedArea.effect == SELECTION) {
        highlightedArea.effect = HIGHLIGHT_SELECTION;
      } else {
        highlightedArea.effect = HIGHLIGHT;
      }
    }
  };
  /** The selected map areas. */
  public-read var selectedAreas:Node[] = [];

  /** The depth of area groups. */
  public var selectedDepth:Integer;

  override function create() {
    map.onMouseExited = function(evt:MouseEvent) {
      highlightedArea = null;
    }
    name.translateX =  - name.boundsInLocal.width / 2;
    Group {
      content: [map, name]
//      translateY: -25
    }
  }

  def map:Group =  {
    def rawMap:Group = FXDLoader.load(url) as Group;
    initialize(rawMap, 0);
    rawMap
  }

  def name:Label = Label {
    var higlightedBouds:Bounds = bind localToAncestor(highlightedArea, selectedDepth);
    var centerX = (higlightedBouds.minX + higlightedBouds.maxX) / 2
    textFill: bind adjust(fill, HIGHLIGHT_DARKNESS)
    font: Font { size: 11 }
    text: bind "{highlightedArea.id}"
    width: bind highlightedArea.id.length() * name.font.size
    hpos: HPos.CENTER
    translateX: bind (higlightedBouds.minX + higlightedBouds.maxX) / 2 - name.width / 2
    translateY: bind (higlightedBouds.minY + higlightedBouds.maxY) / 2
    visible: bind if(highlightedArea.id == "") false else true
  }

  /** Recursive initialising function. */
  function initialize(group:Group, actualDepth:Integer):Void {
    // Count the depth.
    var aDepth = actualDepth + 1;
    depth = max(depth, aDepth);
    
    for(i in [0..<sizeof group.content]) {
      var node = group.content[i];
      if(node instanceof Group) {
        initialize(node as Group, aDepth)
      }
      else if(node instanceof SVGPath) {
        var rPath = (node as SVGPath);
        var area:Area = Area {
          id: rPath.id
          content: rPath.content
          fill: bind if(area.selected) selectedFill else fill
          fillRule: rPath.fillRule
          stroke: bind stroke
          strokeLineCap: rPath.strokeLineCap
          strokeWidth: rPath.strokeWidth
          // Set highlighted area.
          onMouseEntered: function(evt:MouseEvent) {
            var parent:Node = area;
            for(j in [0..<selectedDepth]) {
              parent = parent.parent;
            }
            highlightedArea = parent;
          }
          // Set selected areas.
          onMouseClicked: function(evt:MouseEvent) {
            var isSelected = selected(highlightedArea);
            select(highlightedArea, (not isSelected));
          }
        }
        group.content[i] = area;
      }
    }
  }
  
  /** Recursive selecting function. */
  function selected(node:Node):Boolean {
    if(node instanceof Area) {
        if((node as Area).selected) return true;
    } else if(node instanceof Group) {
      var group = node as Group;
      for(i in [0..<sizeof group.content]) {
        if(selected(group.content[i])) return true;
      }
    }
    false
  }

  /** Recursive selecting function. */
  function select(node:Node, selected:Boolean):Void {
    if(node instanceof Area) {
      (node as Area).selected = selected;
      if(selected) {
        insert node into selectedAreas;
      } else {
        delete node from selectedAreas;
      }
    } else if(node instanceof Group) {
      var group = node as Group;
      for(i in [0..<sizeof group.content]) {
        select(group.content[i], selected)
      }
    }
  }
}

/** Class for overriding SVGPath by adding the "selected" attribute.*/
class Area extends SVGPath {
  public var selected:Boolean
}

