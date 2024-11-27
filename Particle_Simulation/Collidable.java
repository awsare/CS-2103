public interface Collidable {
    /**
     * Computes and returns the time when (if ever) this object will collide with another particle,
     * or infinity if the two objects will never collide given their current velocities.
     * DO NOT CHANGE THE MATH IN THIS METHOD
     * @param other the other particle to consider
     * @return the time with the particles will collide, or infinity if they will never collide
     */
    double getCollisionTime(Particle other);
}
