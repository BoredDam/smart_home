package devices.speaker;

public class YoutubeMusicApp extends SpeakerAppDecorator {

    YoutubeMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing YouTube Music into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        System.out.println("YouTube Music's app is installed! Enjoy your music!");
    }

}