package pl.wasu.pong;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.net.URL;


public class Music {

    public Music() {

    }

    Sequencer sequencer;

    public boolean isPlaying = false;

    public void startMusic() {
        try {
            URL url = getClass().getResource("/pl/wasu/pong/resources/music.mid");
            Sequence sequence = MidiSystem.getSequence(url);

            if(isPlaying == true) {
                sequencer.stop();
                sequencer.close();
                isPlaying = false;
            } else {
                sequencer = MidiSystem.getSequencer();
                sequencer.setSequence(sequence);
                sequencer.setLoopCount(5);
                sequencer.open();
                sequencer.start();

                isPlaying = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
