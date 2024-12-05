public class WallCollisionEvent extends Event {
    Particle particle;
    Wall wall;

    public WallCollisionEvent(double timeOfEvent, double timeEventCreated, Particle particle, Wall wall) {
        super(timeOfEvent, timeEventCreated);
        this.particle = particle;
        this.wall = wall;
    }

    public boolean isValid() {
        return particle.getLastUpdateTime() <= super._timeEventCreated;
    }

    @Override
    public void updateAfterCollision(double now) {
        particle.updateAfterWallCollision(now, wall);
    }

    @Override
    public String toString() {
        return "WallCollisionEvent [particle=" + particle + ", wall=" + wall + ", timeCreated: " + super._timeEventCreated + ", timeOfEvent: " + super._timeOfEvent + "]";
    }
}
