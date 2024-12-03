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
    public double getCollisionTime(Particle other) {
        double collisionTime = 0;
        switch (_side) {
            case TOP -> {
                if (other.getVy() >= 0) {
                    collisionTime =  Double.POSITIVE_INFINITY;
                }
                System.out.println("Top Wall Collision Time: " + (_position - (other.getY() - other.getRadius())) / other.getVy());
                collisionTime =  (_position - (other.getY() - other.getRadius())) / other.getVy();
                break;
            }
            case BOTTOM -> {
                if (other.getVy() <= 0) {
                    collisionTime =  Double.POSITIVE_INFINITY;
                }
                System.out.println("Bottom Wall Collision Time: " + (_position - (other.getY() + other.getRadius())) / other.getVy());
                collisionTime =  (_position - (other.getY() + other.getRadius())) / other.getVy();
                break;
            }
            case LEFT -> {
                if (other.getVx() >= 0) {
                    collisionTime = Double.POSITIVE_INFINITY;
                }
                System.out.println("Left Wall Collision Time: " + (_position - (other.getX() - other.getRadius())) / other.getVx());
                collisionTime = (_position - (other.getX() - other.getRadius())) / other.getVx();
                break;
            }
            case RIGHT -> {
                if (other.getVx() <= 0) {
                    collisionTime = Double.POSITIVE_INFINITY;
                }
//                System.out.println("Wall Position: " + _position);
//                System.out.println("Particle X: " + other.getX());
//                System.out.println("VX: " + other.getVx());

                System.out.println((other.getX() + other.getRadius()));
                System.out.println((_position - (other.getX() + other.getRadius())));
                System.out.println("Right Wall Collision Time: " + (_position - (other.getX() + other.getRadius())) / other.getVx());
                collisionTime = (_position - (other.getX() + other.getRadius())) / other.getVx();
                break;
            }
        };
        double SMALL = 1e-6;

//        return Math.max(SMALL, collisionTime);
        return collisionTime;

//        double top = getTopCollisionTime(other);
//        double bottom = getBottomCollisionTime(other);
//        double left = getLeftCollisionTime(other);
//        double right = getRightCollisionTime(other);
//
//        return switch (_side) {
//            case TOP -> {
//                if (top < left && top < right) {
//                    yield top;
//                }
//                yield Double.POSITIVE_INFINITY;
//            }
//            case BOTTOM -> {
//                if (bottom < left && bottom < right) {
//                    yield bottom;
//                }
//                yield Double.POSITIVE_INFINITY;
//            }
//            case LEFT -> {
//                if (left < top && left < bottom) {
//                    yield left;
//                }
//                yield Double.POSITIVE_INFINITY;
//            }
//            case RIGHT -> {
//                if (right < top && right < bottom) {
//                    yield right;
//                }
//                yield Double.POSITIVE_INFINITY;
//            }
//        };
    }

//    private double getTopCollisionTime(Particle other) {
//        if (other.getVy() >= 0) {
//            return Double.POSITIVE_INFINITY;
//        }
//        return (_position - (other.getY() - other.getRadius())) / other.getVy();
//    }
//
//    private double getBottomCollisionTime(Particle other) {
//        if (other.getVy() <= 0) {
//            return Double.POSITIVE_INFINITY;
//        }
//        return (_position - (other.getY() + other.getRadius())) / other.getVy();
//    }
//
//    private double getLeftCollisionTime(Particle other) {
//        if (other.getVx() >= 0) {
//            return Double.POSITIVE_INFINITY;
//        }
//        return (_position - (other.getX() - other.getRadius())) / other.getVx();
//    }
//
//    private double getRightCollisionTime(Particle other) {
//        if (other.getVx() <= 0) {
//            return Double.POSITIVE_INFINITY;
//        }
//        return (_position - (other.getX() + other.getRadius())) / other.getVx();
//    }
}
