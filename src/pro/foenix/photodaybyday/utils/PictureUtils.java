package pro.foenix.photodaybyday.utils;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.view.Display;

public class PictureUtils {
	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognise a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}

			}
		}

		return degree;
	}

	public static BitmapDrawable getScaledDrawable(Activity a, String path) {
		Display display = a.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		//int width = size.x;
		//int height = size.y;
		float destWidth = size.x;
		float destHeight = size.y;
		// Чтение размеров изображения на диске
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		float srcWidth = options.outWidth;
		float srcHeight = options.outHeight;
		int inSampleSize = 1;
		if (srcHeight > destHeight || srcWidth > destWidth) {
			if (srcWidth > srcHeight) {
				inSampleSize = Math.round(srcHeight / destHeight);
			} else {
				inSampleSize = Math.round(srcWidth / destWidth);
			}
		}
		options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		Matrix matrix = new Matrix();
		matrix.postRotate(PictureUtils.getExifOrientation(path));
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return new BitmapDrawable(a.getResources(), bitmap);
	}
}
