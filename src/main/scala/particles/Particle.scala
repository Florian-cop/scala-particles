package particles
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle


final case class Particle(coordinates: Coordinates, radiusCircle: Double, direction: Direction, color: Color) {
  def draw(): Circle = {
    new Circle {
      centerX = coordinates.x
      centerY = coordinates.y
      radius = radiusCircle
      fill = color
    }
  }

  def move(newDirection: Direction): Particle = {
    val newCoordinates = newDirection match {
      case Direction.NORTH      => coordinates.copy(y = coordinates.y - 1)
      case Direction.SOUTH      => coordinates.copy(y = coordinates.y + 1)
      case Direction.EAST       => coordinates.copy(x = coordinates.x + 1)
      case Direction.WEST       => coordinates.copy(x = coordinates.x - 1)
      case Direction.NORTH_EAST => Coordinates(coordinates.x + 1, coordinates.y - 1)
      case Direction.NORTH_WEST => Coordinates(coordinates.x - 1, coordinates.y - 1)
      case Direction.SOUTH_EAST => Coordinates(coordinates.x + 1, coordinates.y + 1)
      case Direction.SOUTH_WEST => Coordinates(coordinates.x - 1, coordinates.y + 1)
    }
    copy(coordinates = newCoordinates, direction = newDirection)
  }
}
