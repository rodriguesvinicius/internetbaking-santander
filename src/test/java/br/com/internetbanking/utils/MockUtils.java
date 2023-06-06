package br.com.internetbanking.utils;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public final class MockUtils {

	public static String getJson(String filePath) {
		try {
			InputStream file = new FileInputStream("src/test/resources/" + filePath);
			return new String(file.readAllBytes());
		}
		catch (IOException ignored) {

		}
		return "";
	}

}
