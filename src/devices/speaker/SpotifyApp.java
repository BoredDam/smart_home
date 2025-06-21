package devices.speaker;

/**
 * Represents the Spotify app for the smart speaker.
 * Affects output of printInfos method by adding information about Spotify.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class SpotifyApp extends SpeakerAppDecorator {

    public SpotifyApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Spotify into your smart speaker device...");
    }

    @Override
    public void printAddInfos() {
        printHeader();
        System.out.println("\t \t - Spotify. - - - - - Have a good listen!");  
    }

}