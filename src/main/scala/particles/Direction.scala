package particles

import scala.util.Random

enum Direction {
  case NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST
}

object Direction {
  def random: Direction = {
    val values = Direction.values
    values(Random.nextInt(values.length))
  }
}
