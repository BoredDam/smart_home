package devices.speaker;

/**
 * Represents the Amazon Music app for the smart speaker.
 * Affects output of printInfos method by adding information about Amazon Music.
 * @author Paolo Volpini
 * @author Damiano Trovato
 */
public class AmazonMusicApp extends SpeakerAppDecorator {

    public AmazonMusicApp(Speaker wrapped) {
        super(wrapped);
        System.out.println("Installing Amazon Music into your smart speaker device...");
    }

    @Override
    public void printAddInfos() {
        printHeader();
        System.out.println("\t \t - Amazon Music. - - - - - Enjoy your music without any ad.");
    }

}