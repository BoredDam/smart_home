package devices.speaker;

public class SpotifyApp extends SpeakerAppDecorator {

    public SpotifyApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Spotify into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        super.printInfos();
        System.out.println("\t \t - Spotify. - - - - - Have a good listen!");  
    }

}