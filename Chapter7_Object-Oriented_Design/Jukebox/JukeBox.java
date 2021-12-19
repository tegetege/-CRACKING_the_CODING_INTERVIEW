package JukeBox;

public class JukeBox {
    private Song currentSong;
    private SongSelector songSelector;

    public JukeBox(ArrayList<CD> cdCollection) {
        this.cdCollection = cdCollection;
        this.songSelector = new SongSelector(this.cdCollection);
    }

    public Song playMusic() {
        this.songSelector.setSong(1); // CDの１番目の曲を選択
        this.currentSong = this.songSelector.getCurrentSong();
        return this.currentSong;
    }

    public Song changeMusic() {
        this.songSelector.stopCurrentSong(); // 今再生中の曲をストップ
        this.songSelector.setSong(2); // CDの２番目の曲を選択
        this.currentSong = this.songSelector.getCurrentSong();
        return this.currentSong;
    }

    public void stopMusic() {
        this.songSelector.stopCurrentSong(); // 今再生中の曲をストップ
        this.currentSong = null;
    }
}