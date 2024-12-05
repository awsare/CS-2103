public class WallCollisionEvent extends Event {
    private Particle particle;
    private Wall wall;

    public WallCollisionEvent(double timeOfEvent, double timeEventCreated, Particle particle, Wall wall) {
        super(timeOfEvent, timeEventCreated);
        this.particle = particle;
        this.wall = wall;
    }

    /**
     * @return if the particle has been updated after the event was created, the event is invalid
     */
    public boolean isValid() {
        return particle.getLastUpdateTime() <= super.getTimeEventCreated();
    }

    /**
     * @return particle in collision
     */
    public Particle getParticle() {
        return particle;
    }

    /**
     * @return wall in collision
     */
    public Wall getWall() {
        return wall;
    }

    /**
     * updates particle values in wall collision
     * @param now current time
     */
    @Override
    public void updateAfterCollision(double now, double width) {
//        System.out.println("Colliding with wall " + wall._side);
//        System.out.println("VX is " + particle.getVx() + " and VY is " + particle.getVy());
        particle.updateAfterWallCollision(now, wall, width);
//        System.out.println("Updated after wall collision.");
//        System.out.println("VX is " + particle.getVx() + " and VY is " + particle.getVy());
    }

    @Override
    public String toString() {
        return "WallCollisionEvent [particle=" + particle + ", wall=" + wall + ", timeCreated: " + super.getTimeEventCreated() + ", timeOfEvent: " + super.getTimeOfEvent() + "]";
    }
}
