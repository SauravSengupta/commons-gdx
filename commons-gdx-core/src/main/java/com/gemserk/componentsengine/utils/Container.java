package com.gemserk.componentsengine.utils;

public class Container {

	private float current;
	private float total;
	private float old;
	private float original;
	
	public void setCurrent(float current) {
		if (current > total)
			current = total;
		if (current < 0f)
			current = 0f;
		this.current = current;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	public void set(float total, float current) {
		setTotal(total);
		setCurrent(current);
	}
	
	public void set(Container container) {
		set(container.getTotal(), container.getCurrent());
	}

	public float getCurrent() {
		return current;
	}

	public float getTotal() {
		return total;
	}
	
	public float getPercentage() {
		return current / total;
	}

	public float getOld() {
		return old;
	}

	public void updateOld() {
		old = current;
	}

	public Container() {
		current = total = original = 0;
	}

	public Container(float current, float total) {
		super();
		this.current = current;
		this.total = total;
		this.original = current;
		
		this.old = -1;
	}
	
	public Container(float total) {
		this(total, total);
	}
	
	public Container(Container container) {
		this.current = container.getCurrent();
		this.total = container.getTotal();
		this.old = container.getOld();
		this.original = container.getOriginal();
	}

	private float getOriginal() {
		return original;
	}
	
	public boolean isFull() {
		return current == total;
	}

	public boolean isEmpty() {
		return current == 0;
	}

	/**
	 * Removes from the container the specified amount.
	 * 
	 * @param value
	 *            The amount to be remove.
	 */
	public void remove(float value) {
		setCurrent(current - value);
	}

	/**
	 * Fills the container the specified amount.
	 * 
	 * @param value
	 *            The amount to fill.
	 */
	public void add(float value) {
		setCurrent(current + value);
	}

	public void reset() {
		setCurrent(original);
	}
	
	@Override
	public String toString() {
		return "[" + getCurrent() + "/" + getTotal() + "]";
	}

}
