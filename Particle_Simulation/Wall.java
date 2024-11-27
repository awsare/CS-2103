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
//        return switch (_side) {
//            case TOP -> {
//                if (other.getVy() >= 0) {
//                    yield Double.POSITIVE_INFINITY;
//                }
//                yield (_position - (other.getY() - other.getRadius())) / other.getVy();
//            }
//            case BOTTOM -> {
//                if (other.getVy() <= 0) {
//                    yield Double.POSITIVE_INFINITY;
//                }
//                yield (_position - (other.getY() + other.getRadius())) / other.getVy();
//            }
//            case LEFT -> {
//                if (other.getVx() >= 0) {
//                    yield Double.POSITIVE_INFINITY;
//                }
//                yield (_position - (other.getX() - other.getRadius())) / other.getVx();
//            }
//            case RIGHT -> {
//                if (other.getVx() <= 0) {
//                    yield Double.POSITIVE_INFINITY;
//                }
//                yield (_position - (other.getX() + other.getRadius())) / other.getVx();
//            }
//        };

        double top = getTopCollisionTime(other);
        double bottom = getBottomCollisionTime(other);
        double left = getLeftCollisionTime(other);
        double right = getRightCollisionTime(other);

        return switch (_side) {
            case TOP -> {
                if (top < left && top < right) {
                    yield top;
                }
                yield Double.POSITIVE_INFINITY;
            }
            case BOTTOM -> {
                if (bottom < left && bottom < right) {
                    yield bottom;
                }
                yield Double.POSITIVE_INFINITY;
            }
            case LEFT -> {
                if (left < top && left < bottom) {
                    yield left;
                }
                yield Double.POSITIVE_INFINITY;
            }
            case RIGHT -> {
                if (right < top && right < bottom) {
                    yield right;
                }
                yield Double.POSITIVE_INFINITY;
            }
        };
    }

    private double getTopCollisionTime(Particle other) {
        if (other.getVy() >= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (_position - (other.getY() - other.getRadius())) / other.getVy();
    }

    private double getBottomCollisionTime(Particle other) {
        if (other.getVy() <= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (_position - (other.getY() + other.getRadius())) / other.getVy();
    }

    private double getLeftCollisionTime(Particle other) {
        if (other.getVx() >= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (_position - (other.getX() - other.getRadius())) / other.getVx();
    }

    private double getRightCollisionTime(Particle other) {
        if (other.getVx() <= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (_position - (other.getX() + other.getRadius())) / other.getVx();
    }
}
