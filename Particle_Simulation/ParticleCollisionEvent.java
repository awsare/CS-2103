public class ParticleCollisionEvent extends Event {
    Particle particleA, particleB;

    public ParticleCollisionEvent(double timeOfEvent, double timeEventCreated, Particle particleA, Particle particleB) {
        super(timeOfEvent, timeEventCreated);
        this.particleA = particleA;
        this.particleB = particleB;
    }

    public boolean isValid() {
        return particleA.getLastUpdateTime() <= super._timeEventCreated && particleB.getLastUpdateTime() <= super._timeEventCreated;
    }

    @Override
    public void updateAfterCollision(double now) {
        particleA.updateAfterParticleCollision(now, particleB);
    }
}
