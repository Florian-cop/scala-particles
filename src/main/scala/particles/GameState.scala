package particles

import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import Direction.*
import scalafx.scene.paint.Color.*
import scalafx.beans.property.{BufferProperty, IntegerProperty, ObjectProperty}

final case class GameState(particules: List[Particle], boardWidth: Int, boardHeight: Int) {

  def draw(): List[Circle] =
    particules.map(_.draw())

  private def bounceDirection(direction: Direction,
                              hitLeft: Boolean,
                              hitRight: Boolean,
                              hitTop: Boolean,
                              hitBottom: Boolean): Direction = {
    val invertX = hitLeft || hitRight
    val invertY = hitTop || hitBottom
    (invertX, invertY, direction) match {
      case (true, true, Direction.NORD_EST)    => Direction.SUD_OUEST
      case (true, true, Direction.NORD_OUEST)  => Direction.SUD_EST
      case (true, true, Direction.SUD_EST)     => Direction.NORD_OUEST
      case (true, true, Direction.SUD_OUEST)   => Direction.NORD_EST
      case (true, true, Direction.NORD)        => Direction.SUD
      case (true, true, Direction.SUD)         => Direction.NORD
      case (true, true, Direction.EST)         => Direction.OUEST
      case (true, true, Direction.OUEST)       => Direction.EST
      case (true, false, Direction.EST)        => Direction.OUEST
      case (true, false, Direction.OUEST)      => Direction.EST
      case (true, false, Direction.NORD_EST)   => Direction.NORD_OUEST
      case (true, false, Direction.NORD_OUEST) => Direction.NORD_EST
      case (true, false, Direction.SUD_EST)    => Direction.SUD_OUEST
      case (true, false, Direction.SUD_OUEST)  => Direction.SUD_EST
      case (false, true, Direction.NORD)       => Direction.SUD
      case (false, true, Direction.SUD)        => Direction.NORD
      case (false, true, Direction.NORD_EST)   => Direction.SUD_EST
      case (false, true, Direction.NORD_OUEST) => Direction.SUD_OUEST
      case (false, true, Direction.SUD_EST)    => Direction.NORD_EST
      case (false, true, Direction.SUD_OUEST)  => Direction.NORD_OUEST
      case _                                   => direction
    }
  }

  def neightboursOf(x: Int, y: Int, width: Int, height: Int): List[(Int, Int)] =
    (for {
      i <- -1 to 1
      j <- -1 to 1
      if (i, j) != (0, 0) &&
         (0 until width).contains(x + i) &&
         (0 until height).contains(y + j)
    } yield (x + i, y + j)).toList

  def hasNeighbour(p: Particle,
                   board: Map[(Int, Int), Direction],
                   width: Int,
                   height: Int): Boolean =
    neightboursOf(p.x, p.y, width, height)
      .exists(coord => board.contains(coord))

  def move(initialBoard: Map[(Int, Int), Direction],
           gameState: ObjectProperty[GameState]): List[Particle] = {
    var board = initialBoard

    val updatedParticles = gameState.value.particules.map { particle =>
      val hitLeft   = particle.x < 0
      val hitRight  = particle.x >= boardWidth
      val hitTop    = particle.y < 0
      val hitBottom = particle.y >= boardHeight
      val coord     = (particle.x, particle.y)

      val newDirection: Direction =
        if (hasNeighbour(particle, board, boardWidth, boardHeight))
          Direction.random
        else
          bounceDirection(particle.direction, hitLeft, hitRight, hitTop, hitBottom)

      val newParticle = particle.move(newDirection)
      board = board.updated((newParticle.x, newParticle.y), newDirection)
      newParticle
    }

    val newState = gameState.value.copy(particules = updatedParticles)
    gameState.update(newState)
    updatedParticles
  }
}
