package view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
/**
 * This class is used to play the video game's music (songs) and its sound effects.
 * Songs are collected via a map while sound effects in an array for efficiency reasons.
 * Clip objects are used to process audio data. Daemon threads are generated for this.
 */
public class DynaSound {
	/**
	 * Singleton instance
	 */
	private static DynaSound instance;
	/**
	 * Names of the songs
	 */
	private static final String[] namesSongs = new String[] {
			// ThreadMenu Songs, switched by DynaMasterCard observer
			"intro","start","account","createAccount",
			"manageAccounts","option","player","stage","statsAccounts",
			// ThreadGame Songs, switched by DynaSlaveGame observer
			"continue","gameOver","paused","starter","winLevel",
			"winStage","level1","level2","level3","level4","level5",
			"level6","level7","level8"
	};
	/**
	 * Names of the sound effects
	 */
	private static final String[] nameEffects = new String[] { 
			"executeOption",  	// 0
			"escFunction", 		// 1
			"cursorMoved",		// 2
			"cursorNegated",	// 3
			"bombExplosion",	// 4
			"collectsItem",		// 5
			"accountCreated",	// 6
			"bombPlaced",		// 7
			"pcDeath",			// 8
			"npcDeath",			// 9
			"skull",			// 10
			"timeOut",			// 11
			"trill",			// 12
			"npcDamaged"		// 13
	};
	/**
	 * Map between songs names and their url
	 */
	private static Map<String, URL> mySongs = new HashMap<>();
	/**
	 * Total number of sound effects
	 */
	private static final int numberOfSE = nameEffects.length;
	/**
	 * url array for sound effects
	 */
	private static URL[] mySoundEffects = new URL[numberOfSE]; // array prevent concurrent exception by multiple methods "playEffect" calls
	/**
	 * Audio data manager for playing song
	 */
	private static Clip clipActualSong;
	/**
	 * Audio data manager for playing sound effects
	 */
	private static Clip clipActualEffect;
	/**
	 * Current song name
	 */
	private static String actualNameSong = "";
	/**
	 * It inhibits playing music
	 */
	private static boolean muteSong, muteEffects;
	/**
	 * Private constructor for Singleton Design Pattern
	 */
	private DynaSound() {
		URL urlSong,urlEffect;
		for(String nameSong : namesSongs) {
			urlSong = getClass().getResource("/songs/"+nameSong+".wav");
			mySongs.put(nameSong, urlSong);
		}
		for(int i = 0; i<numberOfSE;i++) {
			urlEffect = getClass().getResource("/soundEffects/"+nameEffects[i]+".wav");
			mySoundEffects[i] = urlEffect;
		}
	}
	/**
	 * Singleton getInstance method
	 * @return Singleton instance of this class
	 */
	public static DynaSound getInstance() {
		if(instance==null) instance = new DynaSound();
		return instance;
	}
	
	// Getters
	/**
	 * Getter of muteSong
	 * @return its muteSong
	 */
	public static boolean isMuteSong() {return muteSong;}
	/**
	 * Getter of muteEffects
	 * @return its muteEffects
	 */
	public static boolean isMuteEffects() {return muteEffects;}
	
	// Setters
	/**
	 * Setter of muteSong
	 * @param muteSong new muteSong value
	 */
	public static void setMuteSong(boolean muteSong) {
		DynaSound.muteSong = muteSong;
		if(muteSong) {
			closeClipSong();
		}
	}
	/**
	 * Setter of muteEffects
	 * @param muteEffects new muteEffects value
	 */
	public static void setMuteEffects(boolean muteEffects) {
		DynaSound.muteEffects = muteEffects;
	}
	
	// Setter of Files
	/**
	 * The Song Clip opens a song file to play
	 * @param songName song name to play
	 */
	private static void setFileSong(String songName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(mySongs.get(songName));
			clipActualSong = AudioSystem.getClip();
			clipActualSong.open(ais);
		}catch(Exception e) {}
	}
	/**
	 * The Sound Effect Clip opens a sound effect to play
	 * @param i sound effect array index to play
	 */
	private static void setFileSoundEffect(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(mySoundEffects[i]);
			clipActualEffect = AudioSystem.getClip();
			clipActualEffect.open(ais);
		}catch(Exception e) {}
	}
	
	// Player Methods
	/**
	 * Song Clip play the settled song
	 * @param nameSong played song name
	 */
	public static void playSong(String nameSong) {
		if(!muteSong) {
			if(!actualNameSong.equals(nameSong)) {
				closeClipSong();
				setFileSong(nameSong);
				clipActualSong.start();
				clipActualSong.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} else {
			closeClipSong();
		}
	}
	/**
	 * Close the Song Clip
	 */
	private static void closeClipSong() {
		if(clipActualSong!=null) {
			clipActualSong.stop();
			clipActualSong.close();
		}
	}
	/**
	 * Sound Effect Clip plays the specify sound effect.
	 * The LineListener prevents audio crash bug caused by concurrent calls of ClipActualeffect
	 * @param effectCode sound effect index to play
	 */
	public static void playEffect(int effectCode) {
		if(!muteEffects) {
			setFileSoundEffect(effectCode);
			clipActualEffect.addLineListener(new LineListener(){ // this listener prevents bug by multiple calls
			    public void update(LineEvent e){
			        if(e.getType() == LineEvent.Type.STOP){
			            e.getLine().close();
			        }
			    }
			});
			clipActualEffect.start();
		}
	}
	
}
