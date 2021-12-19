package JukeBox;

public class SongSelector {
    private Song currentSong;
    public void SongSelector(Song s) { currentSong = s; }
    public void setSong(Song s) { currentSong = s; }
    public Song getCurrentSong() { return currentSong; }
}