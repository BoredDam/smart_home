package devices.speaker;

/**
 * Represents the YouTube Music app for the smart speaker.
 * Affects output of printInfos method by adding information about YouTube Music.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class YoutubeMusicApp extends SpeakerAppDecorator {

    public YoutubeMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println( "Installing YouTube Music into your smart speaker device...");
    }

    @Override
    public void printAddInfos() {
        printHeader();
        System.out.println("\t \t - YouTube Music - - - - - Enjoy your music!");
    }

}