package devices.speaker;

public abstract class SpeakerAppDecorator implements Speaker {

    Speaker wrapped;

    SpeakerAppDecorator(Speaker wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void pause() {
        wrapped.pause();
    }

    @Override
    public void play() {
        wrapped.play();
    }

    @Override
    public void stop() {
        wrapped.stop();
    }

    @Override
    public void setVolume(int volume) {
        wrapped.setVolume(volume);
    }

    @Override
    public abstract void printInfos();

    
}
