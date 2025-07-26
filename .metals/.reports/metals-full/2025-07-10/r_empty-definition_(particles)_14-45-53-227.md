error id: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Main.scala:
file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Main.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1373
uri: file:///C:/1--Cours%20ESGI/4ESGI/Scala/particles/src/main/scala/particles/Main.scala
text:
```scala
package particles

import particles.Direction
import scalafx.animation.Timeline.*
import scalafx.animation.{AnimationTimer, KeyFrame, Timeline}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.{JFXApp3, Platform}
import scalafx.beans.property.{BufferProperty, IntegerProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Rectangle2D
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.Circle
import scalafx.stage.Screen
import scalafx.util.Duration

import scala.collection.immutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps
import scala.util.Random

object ParticlesApp extends JFXApp3 {

  override def start(): Unit = {
    val numberOfParticles: Int                = 2
    val particleRadius: Int                   = 3 // rayon de chaque particule
    val screenBounds: Rectangle2D             = Screen.primary.visualBounds
    val (boardWidth, boardHeight): (Int, Int) = (screenBounds.width.intValue, screenBounds.height.intValue)
    val particles: List[Particle]             = generateParticles(numberOfParticles, particleRadius, boardWidth, boardHeight)
    val particlesCoord: List(Int, Int)        = nu@@ll

    val state: ObjectProperty[List[Particle]] = ObjectProperty(particles)

    val gameState: ObjectProperty[GameState] = ObjectProperty(
      GameState(particles, boardWidth, boardHeight)
    )

    stage = new PrimaryStage {
      title = "Particles"
      width = boardWidth
      height = boardHeight
      scene = new Scene {
        fill = White
        content = gameState.value.draw()
        state.onChange {
          content = gameState.value.draw()
        }
      }
    }

    infiniteTimeline(state, gameState).play()
  }

  def generateParticles(n: Int, radius: Int, width: Int, height: Int): List[Particle] =
    // List
    //   .fill(n) {
    //     val (x, y)    = (Random.nextInt(width), Random.nextInt(height))                             // position aléatoire
    //     val direction = Direction.random
    //     val color     = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 1) // couleur aléatoire

    //     Particle(x, y, radius, direction, color)
    //   }
    List(
      Particle(100, 140, radius, Direction.NORD, Color.Red),
      Particle(100, 100, radius, Direction.SUD, Color.Blue)
    )

  def infiniteTimeline(particles: ObjectProperty[List[Particle]], gameState: ObjectProperty[GameState]): Timeline =
    new Timeline {
      keyFrames = List(KeyFrame(time = Duration(250), onFinished = _ => updateState(particles, gameState)))
      cycleCount = Indefinite
    }

  def updateState(state: ObjectProperty[List[Particle]], gameState: ObjectProperty[GameState]): Unit = {
    val board = state.value.groupBy(p => (p.x, p.y))
    state.update(gameState.value.move(board, gameState))
  }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 