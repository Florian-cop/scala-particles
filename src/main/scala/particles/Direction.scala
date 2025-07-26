package particles

import scala.util.Random

enum Direction {
  case NORD, SUD, EST, OUEST, NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST
}

object Direction {
  def random: Direction = {
    val values = Direction.values
    values(Random.nextInt(values.length))
  }
}
