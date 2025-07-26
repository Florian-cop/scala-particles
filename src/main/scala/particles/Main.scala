package particles

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.application.JFXApp3
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Rectangle2D
import scalafx.scene.Scene
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.White
import scalafx.stage.Screen
import scalafx.util.Duration
import scala.util.Random

object ParticlesApp extends JFXApp3 {
  override def start(): Unit = {
    val numberOfParticles = 1000
    val particleRadius = 2
    val screenBounds = Screen.primary.visualBounds
    val (boardWidth, boardHeight) = (screenBounds.width.intValue, screenBounds.height.intValue)
    val particles = generateParticles(numberOfParticles, particleRadius, boardWidth, boardHeight)

    val state = ObjectProperty(particles)
    val gameState = ObjectProperty(GameState(particles, boardWidth, boardHeight))

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
    List.fill(n) {
      val coordinates = Coordinates(Random.nextInt(width), Random.nextInt(height))
      val direction = Direction.random
      val color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 1)
      Particle(coordinates, radius, direction, color)
    }

  def infiniteTimeline(particles: ObjectProperty[List[Particle]], gameState: ObjectProperty[GameState]): Timeline =
    new Timeline {
      keyFrames = List(KeyFrame(time = Duration(25), onFinished = _ => updateState(particles, gameState)))
      cycleCount = Timeline.Indefinite
    }

  def updateState(state: ObjectProperty[List[Particle]], gameState: ObjectProperty[GameState]): Unit = {
    val board: Map[Coordinates, Direction] = state.value.map(p => p.coordinates -> p.direction).toMap
    state.update(gameState.value.move(board, gameState))
  }
}
