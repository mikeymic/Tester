package com.example.serializedatasample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import android.content.Context;

public class FileManager {


	/**
	 * シリアライズ
	 * オブジェクト（文字列リスト）をファイルに保存
	 * @param context
	 * @param node
	 * @return
	 */
	public byte[] Save(Context context, Node node) {

		ByteArrayOutputStream outFile;
		ObjectOutputStream outObject;
		byte[] byteNode = null;
		try {
			outFile = new ByteArrayOutputStream();
			outObject = new ObjectOutputStream(outFile);
			outObject.writeObject(node);
			byteNode = outFile.toByteArray();
			outObject.close();
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteNode;
		}

	/**
	 * デシリアライズ
	 * ファイルからオブジェクト（文字列リスト）に読み込み
	 * @param context
	 * @return
	 */
	public Node loadNode(byte[] stream) {
		Node node = new Node();

		try {
			ByteArrayInputStream inFile = new ByteArrayInputStream(stream);
			ObjectInputStream inObject = new ObjectInputStream(inFile);
			node = (Node) inObject.readObject();
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
		return node;
	}











}
