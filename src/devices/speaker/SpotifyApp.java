package devices.speaker;

public class SpotifyApp extends SpeakerAppDecorator {

    SpotifyApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Spotify into your smart speaker device...");
    }

    @Override
    public void printInfos() {
        System.out.println("Spotify's app is installed! Have a good listen!");
    }

}