package devices.speaker;

public abstract class SpeakerAppDecorator extends Speaker {

    Speaker wrapped;

    public SpeakerAppDecorator(Speaker wrapped) {
        super(wrapped.getName());
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
    public void printInfos() {
        wrapped.printInfos();
    }

    
}
