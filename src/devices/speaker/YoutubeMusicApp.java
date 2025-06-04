package devices.speaker;

public class YoutubeMusicApp extends SpeakerAppDecorator {

    public YoutubeMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing YouTube Music into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        super.printInfos();
        System.out.println("\t \t - YouTube Music - - - - - Enjoy your music!");
    }

}