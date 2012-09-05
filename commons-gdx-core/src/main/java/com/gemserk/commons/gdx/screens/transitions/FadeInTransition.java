package com.gemserk.commons.gdx.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.commons.gdx.GameTransitions;
import com.gemserk.commons.gdx.GameTransitions.TransitionHandler;
import com.gemserk.commons.gdx.Screen;

public class FadeInTransition extends GameTransitions.EnterTransition {

	private final float time;

	private Transition<Color> colorTransition;

	private final Color startColor = new Color(0f, 0f, 0f, 1f);
	private final Color endColor = new Color(0f, 0f, 0f, 0f);

	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	public FadeInTransition(Screen screen, float time) {
		super(screen, time);
		this.time = time;
		
		this.camera = new OrthographicCamera();
		this.shapeRenderer = new ShapeRenderer(64);
	}

	public FadeInTransition(Screen screen, float time, int width, int height) {
		super(screen, time);
		this.time = time;
		
		this.camera = new OrthographicCamera(width, height);
		this.shapeRenderer = new ShapeRenderer(64);
	}

	public FadeInTransition(Screen screen, float time, TransitionHandler transitionHandler) {
		super(screen, time, transitionHandler);
		this.time = time;
		
		this.camera = new OrthographicCamera();
		this.shapeRenderer = new ShapeRenderer(64);
	}

	public FadeInTransition(Screen screen, float time, TransitionHandler transitionHandler,
			int width, int height) {
		super(screen, time, transitionHandler);
		this.time = time;
		
		this.camera = new OrthographicCamera(width, height);
		this.shapeRenderer = new ShapeRenderer(64);
	}

	@Override
	public void init() {
		super.init();
//		colorTransition = Transitions.transitionBuilder(startColor).end(endColor).time(time).build();
		colorTransition = Transitions.transition(startColor).endObject(time, endColor).build();
		Color color = colorTransition.get();

		shapeRenderer.setColor(color);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	@Override
	public void postRender(float delta) {
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.identity();
		
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.filledRect(0, 0, camera.viewportWidth, camera.viewportHeight);
		shapeRenderer.end();
	}

	@Override
	public void internalUpdate(float delta) {
		super.internalUpdate(delta);
		colorTransition.update(delta);
		Color color = colorTransition.get();
		
		shapeRenderer.setColor(color);
	}

}