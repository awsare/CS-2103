public class ParticleCollisionEvent extends Event {
    Particle particleA, particleB;

    public ParticleCollisionEvent(double timeOfEvent, double timeEventCreated, Particle particleA, Particle particleB) {
        super(timeOfEvent, timeEventCreated);
        this.particleA = particleA;
        this.particleB = particleB;
    }

    /**
     * @return if the particles have been updated after the event was created, the event is invalid
     */
    public boolean isValid() {
        return particleA.getLastUpdateTime() <= super._timeEventCreated && particleB.getLastUpdateTime() <= super._timeEventCreated;
    }

    /**
     * updates both particles values in the particle collision
     * @param now current time
     */
    @Override
    public void updateAfterCollision(double now) {
        particleA.updateAfterParticleCollision(now, particleB);
    }
}
