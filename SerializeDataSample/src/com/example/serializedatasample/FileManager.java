package com.example.serializedatasample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class FileManager {

	public static final String FILE_NAME = "object.file";

	/**
	 * シリアライズ
	 * オブジェクト（文字列リスト）をファイルに保存
	 * @param context
	 * @param list
	 * @return
	 */
	public static int Save(Context context, List<Node> list) {

		FileOutputStream outFile;
		ObjectOutputStream outObject;
		try {
			outFile = context.openFileOutput(FILE_NAME, 0);
			outObject = new ObjectOutputStream(outFile);
			outObject.writeObject(list);
			outObject.close();
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
		}

	/**
	 * デシリアライズ
	 * ファイルからオブジェクト（文字列リスト）に読み込み
	 * @param context
	 * @return
	 */
	public static List<Node> loadNode(Context context) {
		List<Node> list = new ArrayList<Node>();

		try {
			FileInputStream inFile = context.openFileInput(FILE_NAME);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			list = (List<Node>) inObject.readObject();
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
		return list;
	}











}
