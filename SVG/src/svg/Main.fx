package svg;

import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import svg.Utils.*;

/**
 * @author Sandor_Bencze
 */
/** The color of the map areas. */
def fill = Color.rgb(0xB4, 0xC7, 0xB2);

/** The stroke of the map areas. */
def stroke = Color.WHITE;

def map:Map = Map {
  url: "{__DIR__}Hu.fxd"
  selectedDepth: bind map.depth - knob.selectedIndex
  fill: bind fill
  stroke: bind stroke
}

def knob:SequentialKnob = SequentialKnob {
//  items: bind for(i in [0..map.depth]) i
  items: ["Ország", "Régiók", "Megyék"]
  startAngle: -90
  endAngle: 90
  text: "Mélység"
  knobColor: bind fill
  knobStrokeWidth: 1.5
  gaugeColor: bind adjust(fill, -0.4)
  translateX: 90
  translateY: 40
}

Stage {
  title: "Terület"
  scene: Scene {
    fill: Color.IVORY
//    width: 400
//    height: 250
    content: [map, knob]
  }
}