package com.example.overlaysample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.graphics.Bitmap;

public class FileManager {


	/**
	 * シリアライズ
	 * オブジェクト（文字列リスト）をファイルに保存
	 */
	public byte[] SerializeObject(Bitmap saveObject) {

		ByteArrayOutputStream outFile;
		ObjectOutputStream outObject;
		byte[] byteStream = null;
		try {
			outFile = new ByteArrayOutputStream();
			outObject = new ObjectOutputStream(outFile);
			outObject.writeObject(saveObject);
			byteStream = outFile.toByteArray();
			outObject.close();
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteStream;
		}


	public Bitmap DeSerializeImages(byte[] stream) {
		Bitmap images = null;
		try {
			ByteArrayInputStream inFile = new ByteArrayInputStream(stream);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			images = (Bitmap) inObject.readObject();
			inObject.close();
			inFile.close();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return images;
	}














}
