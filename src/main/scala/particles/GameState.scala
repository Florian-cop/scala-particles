package particles

import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import Direction.*
import scalafx.scene.paint.Color.*
import scalafx.beans.property.{BufferProperty, IntegerProperty, ObjectProperty}

final case class GameState(particles: List[Particle], boardWidth: Int, boardHeight: Int) {

  def draw(): List[Circle] =
    particles.map(_.draw())

  private def bounceDirection(direction: Direction,
                              hitLeft: Boolean,
                              hitRight: Boolean,
                              hitTop: Boolean,
                              hitBottom: Boolean): Direction = {
    val invertX = hitLeft || hitRight
    val invertY = hitTop || hitBottom
    (invertX, invertY, direction) match {
      case (true, true, Direction.NORTH_EAST)    => Direction.SOUTH_WEST
      case (true, true, Direction.NORTH_WEST)    => Direction.SOUTH_EAST
      case (true, true, Direction.SOUTH_EAST)    => Direction.NORTH_WEST
      case (true, true, Direction.SOUTH_WEST)    => Direction.NORTH_EAST
      case (true, true, Direction.NORTH)         => Direction.SOUTH
      case (true, true, Direction.SOUTH)         => Direction.NORTH
      case (true, true, Direction.EAST)          => Direction.WEST
      case (true, true, Direction.WEST)          => Direction.EAST
      case (true, false, Direction.EAST)         => Direction.WEST
      case (true, false, Direction.WEST)         => Direction.EAST
      case (true, false, Direction.NORTH_EAST)   => Direction.NORTH_WEST
      case (true, false, Direction.NORTH_WEST)   => Direction.NORTH_EAST
      case (true, false, Direction.SOUTH_EAST)   => Direction.SOUTH_WEST
      case (true, false, Direction.SOUTH_WEST)   => Direction.SOUTH_EAST
      case (false, true, Direction.NORTH)        => Direction.SOUTH
      case (false, true, Direction.SOUTH)        => Direction.NORTH
      case (false, true, Direction.NORTH_EAST)   => Direction.SOUTH_EAST
      case (false, true, Direction.NORTH_WEST)   => Direction.SOUTH_WEST
      case (false, true, Direction.SOUTH_EAST)   => Direction.NORTH_EAST
      case (false, true, Direction.SOUTH_WEST)   => Direction.NORTH_WEST
      case _                                     => direction
    }
  }

  def neighboursOf(coordinates: Coordinates, width: Int, height: Int): List[Coordinates] =
    (for {
      i <- -1 to 1
      j <- -1 to 1
      if (i, j) != (0, 0) &&
         (0 until width).contains(coordinates.x + i) &&
         (0 until height).contains(coordinates.y + j)
    } yield Coordinates(coordinates.x + i, coordinates.y + j)).toList


  def hasNeighbour(particle: Particle,
                   board: Map[Coordinates, Direction],
                   width: Int,
                   height: Int): Boolean =
    neighboursOf(particle.coordinates, width, height)
      .exists(coordinates => board.contains(coordinates))


  def move(initialBoard: Map[Coordinates, Direction],
           gameState: ObjectProperty[GameState]): List[Particle] = {
    var board = initialBoard
    val updatedParticles = gameState.value.particles.map { particle =>
      val hitLeft   = particle.coordinates.x < 0
      val hitRight  = particle.coordinates.x >= boardWidth
      val hitTop    = particle.coordinates.y < 0
      val hitBottom = particle.coordinates.y >= boardHeight

      val newDirection: Direction =
        if (hasNeighbour(particle, board, boardWidth, boardHeight))
          Direction.random
        else
          bounceDirection(particle.direction, hitLeft, hitRight, hitTop, hitBottom)

      val newParticle = particle.move(newDirection)
      board = board.updated(newParticle.coordinates, newDirection)
      newParticle
    }

    val newState = gameState.value.copy(particles = updatedParticles)
    gameState.update(newState)
    updatedParticles
  }
}
