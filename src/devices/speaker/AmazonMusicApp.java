package devices.speaker;

public class AmazonMusicApp extends SpeakerAppDecorator {

    AmazonMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Amazon Music into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        System.out.println("Amazon Music's app is installed! Enjoy your music without any ad.");
    }

}