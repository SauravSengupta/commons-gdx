package com.gemserk.commons.artemis.components;

import com.artemis.Component;
import com.artemis.ComponentTypeManager;
import com.artemis.Entity;
import com.gemserk.commons.artemis.utils.EntityStore;

public class StoreComponent extends Component {

	public static final int type = ComponentTypeManager.getTypeFor(StoreComponent.class).getId();

	private EntityStore store;

	public static StoreComponent get(Entity e) {
		return (StoreComponent) e.getComponent(type);
	}

	public StoreComponent(EntityStore store) {
		this.store = store;
	}
	
	public EntityStore getStore() {
		return store;
	}
}
