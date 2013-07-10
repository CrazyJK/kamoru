package com.kamoru.app.image.domain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import com.kamoru.app.video.util.VideoUtils;

public class Image {

	private String name;
	private String suffix;
	private long size;
	private long lastModified;
	private File file;
	
	private byte[] masterBytes;
	private byte[] webBytes;
	private byte[] thumbBytes;
	
	public Image(File file) {
		this.file = file;
		init();
	}

	private void init() {
		this.name = file.getName();
		this.suffix = VideoUtils.getFileExtension(file);
		this.size = file.length();
		this.lastModified = file.lastModified();
	}

	public String getName() {
		return name;
	}

	public String getSuffix() {
		return suffix;
	}

	public long getSize() {
		return size;
	}

	public long getLastModified() {
		return lastModified;
	}

	public File getFile() {
		return file;
	}

	public byte[] getImageBytes(PictureType type) {
		try {
			switch(type) {
			case MASTER:
				return masterBytes == null ? masterBytes = FileUtils.readFileToByteArray(file) : masterBytes;
			case WEB:
				return webBytes == null ? webBytes = toByteArray(Scalr.resize(ImageIO.read(file), Scalr.Mode.FIT_TO_WIDTH, 500)) : webBytes;
			case THUMBNAIL:
				return thumbBytes == null ? thumbBytes = toByteArray(Scalr.resize(ImageIO.read(file), Method.SPEED, 100, Scalr.OP_ANTIALIAS, Scalr.OP_BRIGHTER)) : thumbBytes;
			default:
				throw new RuntimeException("잘못된 타입");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private byte[] toByteArray(BufferedImage bi) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.setUseCache(false);
		try {
			ImageIO.write(bi, "gif", outputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return outputStream.toByteArray();
	}
	
}
