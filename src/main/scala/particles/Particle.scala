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

    def move(newDirection: Direction): Particle = {
        val (newX, newY) = newDirection match {
            case Direction.NORD        => (x, y - 1)
            case Direction.SUD         => (x, y + 1)
            case Direction.EST         => (x + 1, y)
            case Direction.OUEST       => (x - 1, y)
            case Direction.NORD_EST    => (x + 1, y - 1)
            case Direction.NORD_OUEST  => (x - 1, y - 1)
            case Direction.SUD_EST     => (x + 1, y + 1)
            case Direction.SUD_OUEST   => (x - 1, y + 1)
        }
        Particle(newX, newY, radiusCircle, newDirection, color)
    }
}
