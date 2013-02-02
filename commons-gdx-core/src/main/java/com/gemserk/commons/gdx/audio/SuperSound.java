package com.gemserk.commons.gdx.audio;

import com.badlogic.gdx.audio.Sound;

public class SuperSound implements Sound {

	Sound sound;
	long duration;
	final String soundName;
	
	public SuperSound(Sound originalSound, long duration, String soundName) {
		sound = originalSound;
		this.duration = duration;
		this.soundName = soundName;
	}

	@Override
	public long play() {
		return sound.play();
	}

	@Override
	public long play(float volume) {
		return sound.play(volume);
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		return sound.play(volume, pitch, pan);
	}

	@Override
	public long loop() {
		return sound.loop();
	}

	@Override
	public long loop(float volume) {
		return sound.loop(volume);
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		return sound.loop(volume, pitch, pan);
	}

	@Override
	public void stop() {
		sound.stop();
	}

	@Override
	public void dispose() {
		sound.dispose();
	}

	@Override
	public void stop(long soundId) {
		sound.stop(soundId);
	}

	@Override
	public void setLooping(long soundId, boolean looping) {
		sound.setLooping(soundId, looping);
	}

	@Override
	public void setPitch(long soundId, float pitch) {
		sound.setPitch(soundId, pitch);
	}

	@Override
	public void setVolume(long soundId, float volume) {
		sound.setVolume(soundId, volume);
	}

	@Override
	public void setPan(long soundId, float pan, float volume) {
		sound.setPan(soundId, pan, volume);
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public String getSoundName() {
		return soundName;
	}

	@Override
	public void setPriority(long soundId, int priority) {
		sound.setPriority(soundId, priority);
	}
}
