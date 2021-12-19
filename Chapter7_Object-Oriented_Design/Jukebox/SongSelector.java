package JukeBox;

public class SongSelector {
    private ArrayList<Song> songCollection;
    private Song currentSong;
    /**
     * コンストラクタ
     * songCollection: 曲をリストで持っているCDやレコードデータを受け取る
     * */
    public void SongSelector(ArrayList<Song> songCollection) { this.songCollection = songCollection; }

    public void setSong(int num) {
        currentSong = this.songCollection[num];
    }

    public Song getCurrentSong() {
        currentSong.playing = true;
        return currentSong;
    }

    public void stopCurrentSong() {
        currentSong.playing = false;
    }
}