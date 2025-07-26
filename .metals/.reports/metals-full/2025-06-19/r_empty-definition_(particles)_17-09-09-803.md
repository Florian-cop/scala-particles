error id: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Particle.scala:update.
file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Particle.scala
empty definition using pc, found symbol in pc: update.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -directionState/update.
	 -directionState/update#
	 -directionState/update().
	 -scala/Predef.directionState.update.
	 -scala/Predef.directionState.update#
	 -scala/Predef.directionState.update().
offset: 557
uri: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Particle.scala
text:
```scala
package particles
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

final case class Particle(x: Int, y: Int, radiusCircle:Double, direction: Direction, color: Color){
    def draw(): Circle = {
        new Circle {
            centerX = x
            centerY = y
            radius = radiusCircle
            fill = color
        }
    }

    def move(direction: Direction): Circle = {
        case UP    => directionState.update(Direction.UP)
      case DOWN  => directionState.update(Direction.DOWN)
      case LEFT  => directionState.u@@pdate(Direction.LEFT)
      case RIGHT => directionState.update(Direction.RIGHT)
        new Circle {
            centerX = x
            centerY = y
            radius = radiusCircle
            fill = color
        }
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: update.