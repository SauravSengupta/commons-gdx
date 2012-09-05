package com.gemserk.commons.gdx.games;

import com.badlogic.gdx.math.Vector2;

public class SpatialHierarchicalImpl implements Spatial {

	private final Vector2 aux = new Vector2();

	private final Vector2 localPosition = new Vector2();
	private final Vector2 absolutePosition = new Vector2();

	private float localAngle;
	private float w, h;

	private Spatial parent;

	public static SpatialHierarchicalImpl hierarchicalWithParent(Spatial parent, boolean updateWithParent) {
		SpatialHierarchicalImpl spatialHierarchicalImpl = new SpatialHierarchicalImpl();
		if (!updateWithParent)
			spatialHierarchicalImpl.parent = parent;
		else
			spatialHierarchicalImpl.setParent(parent);
		return spatialHierarchicalImpl;
	}

	public Spatial getParent() {
		return parent;
	}

	private boolean spriteUpdateEnabled;

	public SpatialHierarchicalImpl(Spatial parent, float width, float height) {
		this(parent, width, height, 0f, 0f, 0f);
	}

	public SpatialHierarchicalImpl(Spatial parent, float width, float height, float localX, float localY, float localAngle) {
		this.parent = parent;
		this.localAngle = 0f;
		setPosition(parent.getX(), parent.getY());
		setAngle(parent.getAngle());
		setSize(width, height);
		setLocalPosition(localX, localY);
		setLocalAngle(localAngle);
	}

	public SpatialHierarchicalImpl() {
		
	}

	public SpatialHierarchicalImpl(Spatial parent) {
		this(parent, parent.getWidth(), parent.getHeight());
	}

	public void setParent(Spatial parent) {
		Vector2 position = getPosition();
		float angle = getAngle();
		this.parent = parent;
		setPosition(position.x, position.y);
		setAngle(angle);
	}

	@Override
	public float getX() {
		return getPosition().x;
	}

	@Override
	public float getY() {
		return getPosition().y;
	}

	@Override
	public Vector2 getPosition() {
		aux.set(localPosition);
		aux.rotate(parent.getAngle());
		absolutePosition.set(aux.x + parent.getX(), aux.y + parent.getY());
		return absolutePosition;
	}

	@Override
	public void setPosition(float x, float y) {
		localPosition.set(x - parent.getX(), y - parent.getY());
		localPosition.rotate(-parent.getAngle());
	}

	public void setLocalPosition(float x, float y) {
		localPosition.set(x, y);
	}

	@Override
	public float getAngle() {
		return localAngle + parent.getAngle();
	}

	public void setLocalAngle(float angle) {
		this.localAngle = angle;
	}

	@Override
	public void setAngle(float angle) {
		localAngle = angle - parent.getAngle();
	}

	@Override
	public float getWidth() {
		return w;
	}

	@Override
	public float getHeight() {
		return h;
	}

	@Override
	public void setSize(float width, float height) {
		this.w = width;
		this.h = height;
	}

	@Override
	public void set(Spatial spatial) {
		setPosition(spatial.getX(), spatial.getY());
		setAngle(spatial.getAngle());
		setSize(spatial.getWidth(), spatial.getHeight());
	}

	@Override
	public void enableSpriteUpdate(boolean enable) {
		this.spriteUpdateEnabled = enable;
	}

	@Override
	public boolean spriteUpdateEnabled() {
		return spriteUpdateEnabled;
	}

}