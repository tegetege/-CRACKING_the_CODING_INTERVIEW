package JukeBox;

public class JukeBox {
    private Song currentSong;
    private SongSelector songSelector;

    public JukeBox(ArrayList<CD> cdCollection, SongSelector songSelector ) {
        this.cdCollection = cdCollection;
        this.songSelector = songSelector;
    }

    public Song playMusic() {
        this.currentSong = this.songSelector.getCurrentSong();
        return this.currentSong;
    }

    public Song changeMusic() {
        this.currentSong = this.songSelector.getCurrentSong();
        return this.currentSong;
    }

    public void stopMusic() {
        this.currentSong = null;
    }
}