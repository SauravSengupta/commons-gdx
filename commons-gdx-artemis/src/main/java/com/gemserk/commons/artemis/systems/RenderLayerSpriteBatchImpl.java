package com.gemserk.commons.artemis.systems;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.gemserk.commons.artemis.components.Components;
import com.gemserk.commons.artemis.components.FrustumCullingComponent;
import com.gemserk.commons.artemis.components.ParticleEmitterComponent;
import com.gemserk.commons.artemis.components.RenderableComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.artemis.components.SpriteComponent;
import com.gemserk.commons.artemis.components.TextComponent;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.graphics.SpriteBatchUtils;
import com.gemserk.componentsengine.utils.RandomAccessMap;

public class RenderLayerSpriteBatchImpl implements RenderLayer {

	class EntityComponents {
		public RenderableComponent renderableComponent;
		public FrustumCullingComponent frustumCullingComponent;
		public SpatialComponent spatialComponent;
		public SpriteComponent spriteComponent;
		public TextComponent textComponent;
		public ParticleEmitterComponent particleEmitterComponent;
	}

	protected final SpriteBatch spriteBatch;
	// private final OrderedByLayerEntities orderedByLayerEntities;
	protected final OrderedByLayerRenderables orderedByLayerRenderables;
	protected final Camera camera;
	private boolean enabled;

	private final Rectangle entityBounds = new Rectangle();
	private final Vector3 nearCorner = new Vector3();
	private final Vector3 farCorner = new Vector3();
	private final BoundingBox boundingBox = new BoundingBox(); 
	private Factory factory;

	private boolean ownsSpriteBatch;

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Camera camera, SpriteBatch spriteBatch) {
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		// this.orderedByLayerEntities = new OrderedByLayerEntities(minLayer, maxLayer);
		this.orderedByLayerRenderables = new OrderedByLayerRenderables(minLayer, maxLayer);
		this.enabled = true;
		this.factory = new Factory();
		this.ownsSpriteBatch = false;
	}

	public RenderLayerSpriteBatchImpl(int minLayer, int maxLayer, Camera camera) {
		this(minLayer, maxLayer, camera, new SpriteBatch());
		this.ownsSpriteBatch = true;
	}

	@Override
	public void init() {

	}

	@Override
	public void dispose() {
		if (ownsSpriteBatch)
			spriteBatch.dispose();
	}

	@Override
	public boolean belongs(Renderable renderable) {
		return orderedByLayerRenderables.belongs(renderable);
	}

	@Override
	public void add(Renderable renderable) {
		factory.add(renderable.entity);
		orderedByLayerRenderables.add(renderable);
	}

	@Override
	public void remove(Renderable renderable) {
		orderedByLayerRenderables.remove(renderable);
		factory.add(renderable.entity);
	}

	@Override
	public void render() {
		spriteBatch.setProjectionMatrix(camera.combined);

		RandomAccessMap<Entity, EntityComponents> entityComponents = factory.entityComponents;

		spriteBatch.begin();
		for (int i = 0; i < orderedByLayerRenderables.size(); i++) {
			Renderable renderable = orderedByLayerRenderables.get(i);
			
			// RenderableComponent renderableComponent = components.renderableComponent;
			// if (!renderableComponent.isVisible())
			// continue;

			if (!renderable.isVisible())
				continue;

			EntityComponents components = entityComponents.get(renderable.getEntity());
			FrustumCullingComponent frustumCullingComponent = components.frustumCullingComponent;
			if (frustumCullingComponent != null) {

				Spatial spatial = components.spatialComponent.getSpatial();

				entityBounds.set(frustumCullingComponent.bounds);

				entityBounds.setX(entityBounds.getX() + spatial.getX());
				entityBounds.setY(entityBounds.getY() + spatial.getY());

				nearCorner.set(entityBounds.x, entityBounds.y, camera.near);
				farCorner.set(entityBounds.x, entityBounds.y, camera.far);

				boundingBox.set(nearCorner, farCorner);
				if (!camera.frustum.boundsInFrustum(boundingBox))
					continue;
			}

			SpriteComponent spriteComponent = components.spriteComponent;
			if (spriteComponent != null) {
				Sprite sprite = spriteComponent.getSprite();
				sprite.setColor(spriteComponent.getColor());
				sprite.draw(spriteBatch);
			}
			// don't like it will be asking for components all the time.
			TextComponent textComponent = components.textComponent;
			if (textComponent != null) {
				BitmapFont font = textComponent.font;

				if (font.getScaleX() != textComponent.scale) {
					font.setScale(textComponent.scale);
				}

				font.setColor(textComponent.color);
				SpriteBatchUtils.drawMultilineText(spriteBatch, font, //
						textComponent.text, textComponent.x, textComponent.y, textComponent.cx, textComponent.cy);
			}
			ParticleEmitterComponent particleEmitterComponent = components.particleEmitterComponent;
			if (particleEmitterComponent != null) {
				particleEmitterComponent.particleEmitter.draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	class Factory extends EntityComponentsFactory<EntityComponents> {

		@Override
		public EntityComponents newInstance() {
			return new EntityComponents();
		}

		@Override
		public void free(EntityComponents entityComponent) {
			entityComponent.renderableComponent = null;
			entityComponent.frustumCullingComponent = null;
			entityComponent.spatialComponent = null;
			entityComponent.spriteComponent = null;
			entityComponent.textComponent = null;
			entityComponent.particleEmitterComponent = null;
		}

		@Override
		public void load(Entity e, EntityComponents entityComponent) {
			entityComponent.renderableComponent = Components.getRenderableComponent(e);
			entityComponent.frustumCullingComponent = Components.getFrustumCullingComponent(e);
			entityComponent.spatialComponent = Components.getSpatialComponent(e);
			entityComponent.spriteComponent = Components.getSpriteComponent(e);
			entityComponent.textComponent = Components.getTextComponent(e);
			entityComponent.particleEmitterComponent = Components.getParticleEmitterComponent(e);
		}
	}

}