package pro.foenix.photodaybyday.ui.fragments;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import pro.foenix.photodaybyday.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class DayCameraFragment extends Fragment {
	private static final String TAG = "DayCameraFragment";
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainer;
	public static final String EXTRA_PHOTO_FILENAME = "pro.foenix.photodaybyday.photo_filename";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_day_camera, parent, false);
		ImageButton butShot = (ImageButton) v.findViewById(R.id.ib_shot);
		butShot.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//getActivity().finish();
				if (mCamera != null) {
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				}
			}
		});
		mSurfaceView = (SurfaceView) v.findViewById(R.id.sv_camera);
		SurfaceHolder holder = mSurfaceView.getHolder();
		mProgressContainer = v.findViewById(R.id.progress_container);
		mProgressContainer.setVisibility(View.INVISIBLE);
		//  до 3.0.
		//holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {
			public void surfaceCreated(SurfaceHolder holder) {
				// ѕриказываем камере использовать указанную
				// поверхность как область предварительного просмотра
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);
					}
				} catch (IOException exception) {
					Log.e(TAG, "Error setting up preview display", exception);
				}
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				// ƒальнейший вывод на поверхности невозможен,
				// прекращаем предварительный просмотр.
				if (mCamera != null) {
					mCamera.stopPreview();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
				if (mCamera == null)
					return;
				// –азмер поверхности изменилс€; обновить размер
				// области предварительного просмотра камеры
				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
				parameters.setPreviewSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				} catch (Exception e) {
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}
			}
		});
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		mCamera = Camera.open(0);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes) {
			int area = s.width * s.height;
			if (area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}

	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		public void onShutter() {
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};
	private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			String filename = UUID.randomUUID().toString() + ".jpg";
			FileOutputStream os = null;
			boolean success = true;
			try {
				os = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
				os.write(data);
			} catch (Exception e) {
				Log.e(TAG, "Error writing to file " + filename, e);
				success = false;
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (Exception e) {
					Log.e(TAG, "Error closing file " + filename, e);
					success = false;
				}
			}
			if (success) {
				Log.i(TAG, "JPEG saved at " + filename);
			}
			// »м€ файла фотографии записываетс€ в интент результата
			if (success) {
				Intent i = new Intent();
				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			} else {
				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			getActivity().finish();
		}
	};
}
