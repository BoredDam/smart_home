package devices.speaker;

public class AmazonMusicApp extends SpeakerAppDecorator {

    public AmazonMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Amazon Music into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        wrapped.printInfos();
        System.out.println("\t \t - Amazon Music. - - - - - Enjoy your music without any ad.");
    }

}