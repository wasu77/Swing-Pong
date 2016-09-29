import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.net.URL;

/**
 * Created by maciek on 29.09.16.
 */
public class Music {

    public Music() {

    }

    Sequencer sequencer;

    public boolean isPlaying = false;

    public void startMusic() {
        try {
            URL url = getClass().getResource("/music.mid");
            Sequence sequence = MidiSystem.getSequence(url);

            if(isPlaying == true) {
                sequencer.stop();
                sequencer.close();
                isPlaying = false;
            } else {
                sequencer = MidiSystem.getSequencer();
                sequencer.setSequence(sequence);
                sequencer.open();
                sequencer.start();

                isPlaying = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
