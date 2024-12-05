public class ParticleCollisionEvent extends Event {
    private Particle particleA, particleB;

    public ParticleCollisionEvent(double timeOfEvent, double timeEventCreated, Particle particleA, Particle particleB) {
        super(timeOfEvent, timeEventCreated);
        this.particleA = particleA;
        this.particleB = particleB;
    }

    /**
     * @return if the particles have been updated after the event was created, the event is invalid
     */
    public boolean isValid() {
        return particleA.getLastUpdateTime() <= super.getTimeEventCreated() && particleB.getLastUpdateTime() <= super.getTimeEventCreated();
    }

    /**
     * @return particleA in collision
     */
    public Particle getParticleA() {
        return particleA;
    }

    /**
     * @return particleB
     */
    public Particle getParticleB() {
        return particleB;
    }

    /**
     * updates both particles values in the particle collision
     * @param now current time
     */
    @Override
    public void updateAfterCollision(double now, double width) {
        particleA.updateAfterParticleCollision(now, particleB);
        //System.out.println("Updated after particle collision.");
    }
}
