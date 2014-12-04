package com.example.monkeyhunt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.content.Context;

public class HighScore {

	public static Context ctx;
	private static String HIGHSCORE_FILE_NAME = "high_score";
	public static int highScore = 0;


	public static void loadHighScore() {

		try {

			File file = HighScore.ctx.getFileStreamPath(HIGHSCORE_FILE_NAME);

			if (file.exists()) {

				FileInputStream in = HighScore.ctx
						.openFileInput(HIGHSCORE_FILE_NAME);

				InputStreamReader inputStreamReader = new InputStreamReader(in);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line;

				while ((line = bufferedReader.readLine()) != null) {
					HighScore.highScore = Integer.parseInt(line);
				}

				bufferedReader.close();
				inputStreamReader.close();
				in.close();

			}

		} catch (Exception e) {
			System.err.println("Error reading high score!");
			e.printStackTrace();
		}

	}

	public static void saveHighScore() {

		try {

			FileOutputStream fOut = HighScore.ctx.openFileOutput(HIGHSCORE_FILE_NAME, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(String.valueOf(HighScore.highScore));
			osw.flush();
			osw.close();
			fOut.close();

		} catch (Exception e) {
			System.err.println("High Score not saved!");
			e.printStackTrace();
		}

	}

}
