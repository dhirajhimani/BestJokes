package com.forthepeople.bestjokes.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

public class Utils {

	public static ArrayList<String> listAssetFiles(Context context, String path) {

		String[] list;
		ArrayList<String> files = null;
		try {
			list = context.getAssets().list(path);
			files = new ArrayList<String>(Arrays.asList(list));
			// if (list.length > 0) {
			// // This is a folder
			// for (String file : list) {
			// if (!listAssetFiles(context, path + "/" + file))
			// return false;
			// }
			// } else {
			// // This is a file
			// // TODO: add file name to an array list
			// }
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}
}
