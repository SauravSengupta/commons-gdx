package com.gemserk.commons.gdx.resources.dataloaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class TextureDataLoader extends DisposableDataLoader<Texture> {
	
	private TextureFilter minFilter;
	private TextureFilter magFilter;
	private Format format;

	public TextureDataLoader(FileHandle fileHandle) {
		super(fileHandle);
		
		minFilter = TextureFilter.Nearest;
		magFilter = TextureFilter.Nearest;
		format = null;
	}

	public TextureDataLoader(FileHandle fileHandle, boolean linearFilter) {
		this(fileHandle);
		
		if (linearFilter)
			minFilter = TextureFilter.Linear;
		else
			minFilter = TextureFilter.Nearest;
	}

	public TextureDataLoader(FileHandle fileHandle, TextureFilter minFilter,
			TextureFilter magFilter) {
		this(fileHandle);
		
		this.minFilter = minFilter;
		this.magFilter = magFilter;
	}
	
	public TextureDataLoader(FileHandle fileHandle, TextureFilter minFilter,
			TextureFilter magFilter, Format format) {
		this(fileHandle, minFilter, magFilter);
		
		this.format = format;
	}
	
	@Override
	public Texture load() {
		boolean useMipMaps = false;
		if (minFilter.isMipMap() || magFilter.isMipMap())
			useMipMaps = true;
		
		Texture texture = new Texture(fileHandle, format, useMipMaps);
		texture.setFilter(minFilter, magFilter);
		return texture;
	}

}