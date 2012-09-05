package com.gemserk.commons.gdx.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.gemserk.commons.gdx.Game;
import com.gemserk.commons.gdx.GameTransitions.ScreenTransition;
import com.gemserk.commons.gdx.GameTransitions.TransitionHandler;
import com.gemserk.commons.gdx.GameTransitions.TransitionScreen;
import com.gemserk.commons.gdx.Screen;

public class TransitionBuilder {

	// should be named ScreenTransitionBuilder because it builds a ScreenTransition

	private final Screen screen;
	private final Game game;

	float leaveTime;
	float enterTime;

	boolean shouldDisposeCurrentScreen;
	boolean shouldRestartNextScreen;

	TransitionHandler leaveTransitionHandler = new TransitionHandler();
	TransitionHandler enterTransitionHandler = new TransitionHandler();

	private boolean transitioning = false;
	private int width;
	private int height;

	public TransitionBuilder leaveTime(float leaveTime) {
		this.leaveTime = leaveTime;
		return this;
	}

	public TransitionBuilder enterTime(float enterTime) {
		this.enterTime = enterTime;
		return this;
	}

	public TransitionBuilder leaveTime(int leaveTime) {
		return leaveTime((float) leaveTime * 0.001f);
	}

	public TransitionBuilder enterTime(int enterTime) {
		return enterTime((float) enterTime * 0.001f);
	}

	public TransitionBuilder width(int width) {
		this.width = width;
		return this;
	}

	public TransitionBuilder height(int height) {
		this.height = height;
		return this;
	}

	public TransitionBuilder disposeCurrent() {
		this.shouldDisposeCurrentScreen = true;
		return this;
	}

	public TransitionBuilder disposeCurrent(boolean disposeCurrent) {
		this.shouldDisposeCurrentScreen = disposeCurrent;
		return this;
	}

	public TransitionBuilder restartScreen() {
		this.shouldRestartNextScreen = true;
		return this;
	}
	
	public TransitionBuilder restartScreen(boolean restart) {
		this.shouldRestartNextScreen = restart;
		return this;
	}

	public TransitionBuilder leaveTransitionHandler(TransitionHandler transitionHandler) {
		this.leaveTransitionHandler = transitionHandler;
		return this;
	}
	
	public TransitionBuilder enterTransitionHandler(TransitionHandler transitionHandler) {
		this.enterTransitionHandler = transitionHandler;
		return this;
	}

	public TransitionBuilder parameter(String key, Object value) {
		screen.getParameters().put(key, value);
		return this;
	}

	public TransitionBuilder(final Game game, final Screen screen) {
		this.game = game;
		this.screen = screen;
		this.leaveTransitionHandler = new TransitionHandler();
		this.leaveTime = 0.25f;
		this.enterTime = 0.25f;
		
		// Default to screen dimensions
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
	}

	public void start() {
		if (transitioning) {
			Gdx.app.log("Commons-gdx", "Can't start a new transition if already in a transition");
			return;
		}

		transitioning = true;

		final Screen currentScreen = game.getScreen();
		game.setScreen(new TransitionScreen(new ScreenTransition( //
				new FadeOutTransition(currentScreen, leaveTime, new TransitionHandler() {
					
					@Override
					public void onBegin() {
						super.onBegin();
						leaveTransitionHandler.onBegin();
					}

					@Override
					public void onEnd() {
						super.onEnd();
						leaveTransitionHandler.onEnd();
						if (shouldRestartNextScreen)
							screen.dispose();
					}
					
				}, width, height), //
				new FadeInTransition(screen, enterTime, new TransitionHandler() {
					
					@Override
					public void onBegin() {
						super.onBegin();
						enterTransitionHandler.onBegin();
					}
					
					public void onEnd() {
						super.onEnd();
						enterTransitionHandler.onEnd();
						game.setScreen(screen, true);
						// disposes current transition screen, not previous screen.
						if (shouldDisposeCurrentScreen)
							currentScreen.dispose();
						transitioning = false;
					};
					
				}, width, height))) {
			@Override
			public void resume() {
				super.resume();
				Gdx.input.setCatchBackKey(true);
			}
		});
	}

}