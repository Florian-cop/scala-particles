error id: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/GameState.scala:`<none>`.
file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/GameState.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 278
uri: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/GameState.scala
text:
```scala
package particles

import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import Direction.*
import scalafx.scene.paint.Color.*

final case class GameState(particules:List[Particle]) {

    def draw(): List[Circle] = {
        particles.map()
        @@new Circle {
            centerX = x
            centerY = y
            radius = radiusCircle
            fill = color
        }
    }
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.