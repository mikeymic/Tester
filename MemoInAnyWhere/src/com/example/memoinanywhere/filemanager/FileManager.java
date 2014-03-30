package com.example.memoinanywhere.filemanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import com.example.memoinanywhere.data.Images;
import com.example.memoinanywhere.data.Texts;

public class FileManager {


	/**
	 * シリアライズ
	 * オブジェクト（文字列リスト）をファイルに保存
	 */
	public byte[] SerializeObject(Object saveObject) {

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



// デシリアライズ
	public Texts DeSerializeTexts(byte[] stream) {
		Texts texts = new Texts();

		try {
			ByteArrayInputStream inFile = new ByteArrayInputStream(stream);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			texts = (Texts) inObject.readObject();
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
		return texts;
	}

	public Images DeSerializeImages(byte[] stream) {
		Images images = new Images();
		try {
			ByteArrayInputStream inFile = new ByteArrayInputStream(stream);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			images = (Images) inObject.readObject();
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
