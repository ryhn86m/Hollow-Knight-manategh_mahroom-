package MyGame.model.enums;

import lombok.Getter;

@Getter
public enum MusicTracks {
    End_Game("end","end_game.mp3"),
        Menu2("Enter2","HollowKnight2.mp3"),
        Enter("Enter", "EnterHallownest.mp3"),
        Forgotten("Forgotten","Crossroads.mp3"),
        FireBG("fire","fireBG.mp3"),
        CrystalPeak("CrystalPeak","Crystal Peak.mp3");
        MusicTracks(String trackName, String path) {
            this.trackName = trackName;
            this.path = path;
        }

        private final String trackName;
        private final String path;

        public static MusicTracks findMusic(String music) {
            for (MusicTracks m : MusicTracks.values()) {
                if (m.getTrackName().equals(music)) {
                    return m;
                }
            }
            return null;
        }

}

