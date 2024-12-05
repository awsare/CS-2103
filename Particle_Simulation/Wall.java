public class Wall implements Collidable {
    enum WallSide {
        TOP, BOTTOM, LEFT, RIGHT;
    }

    WallSide _side;
    double _position;

    public Wall(WallSide side, double position) {
        this._side = side;
        this._position = position;
    }

    @Override
    public String toString() {
        return "" + _side;
    }

    @Override
    public double getCollisionTime(Particle other) {
        double collisionTime = 0;
        switch (_side) {
            case TOP -> {
                if (other.getVy() >= 0) {
                    collisionTime =  Double.POSITIVE_INFINITY;
                    break;
                }
//                System.out.println("Particle Y: " + other.getY());
//                System.out.println("Particle Radius: " + other.getRadius());
//                System.out.println("Particle VY: " + other.getVy());
                collisionTime = (_position - (other.getY() - other.getRadius())) / other.getVy();
                //System.out.println("Top: " + collisionTime);
                break;
            }
            case BOTTOM -> {
                if (other.getVy() <= 0) {
                    collisionTime =  Double.POSITIVE_INFINITY;
                    break;
                }
                collisionTime =  (_position - (other.getY() + other.getRadius())) / other.getVy();
                //System.out.println("Bottom: " + collisionTime);

                break;
            }
            case LEFT -> {
                if (other.getVx() >= 0) {
                    collisionTime = Double.POSITIVE_INFINITY;
                    break;
                }
                collisionTime = (_position - (other.getX() - other.getRadius())) / other.getVx();
                //System.out.println("Left: " + collisionTime);

                break;
            }
            case RIGHT -> {
                if (other.getVx() <= 0) {
                    collisionTime = Double.POSITIVE_INFINITY;
                    break;
                }
//                System.out.println("Wall Position: " + _position);
//                System.out.println("Particle X: " + other.getX());
//                System.out.println("Particle Radius: " + other.getRadius());
//                System.out.println("Particle VX: " + other.getVx());

//                System.out.println((other.getX() + other.getRadius()));
//                System.out.println((_position - (other.getX() + other.getRadius())));
                collisionTime = (_position - (other.getX() + other.getRadius())) / other.getVx();
                //System.out.println("Right: " + collisionTime);

                break;
            }
        }
        return collisionTime;
    }
}
