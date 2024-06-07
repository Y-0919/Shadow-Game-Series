import javax.sound.sampled.*;


public class Track extends Thread {
    private AudioInputStream stream;
    private Clip clip;

    public Track(int i) {
        try {
            stream = AudioSystem.getAudioInputStream(new java.io.File("res/track" + i + ".wav"));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,stream.getFormat()));
            clip.open(stream);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
        try {
            clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run(){
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}