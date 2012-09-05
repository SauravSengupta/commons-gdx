package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.Vector2;

/**
 * Provides an abstraction of spatial concept.
 * 
 * @author acoppes
 * 
 */
public interface Spatial {

	float getX();

	float getY();
	
	Vector2 getPosition();

	void setPosition(float x, float y);
	
	// void setPosition(Vector2 position);
	
	/**
	 * Returns the angle of the spatial in degrees.
	 */
	float getAngle();

	void setAngle(float angle);
	
	float getWidth();
	
	float getHeight();
	
	void setSize(float width, float height);
	
	void set(Spatial spatial);

	/**
	 * Flag to tell the sprite update system to ignore this spatial. This is
	 * needed because removing the spatial component in the middle of an 
	 * update or render pass causes Artemis to crash
	 * @param enable
	 */
	void enableSpriteUpdate(boolean enable);

	boolean spriteUpdateEnabled();
}