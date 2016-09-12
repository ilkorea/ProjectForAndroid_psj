package kr.mindwing.d07_mygallery;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Fast campus 안드로이드 앱 개발 프로젝트 CAMP
 * MyGallery (1/3) (by mindwing)
 */
public class Utils {
    public static final String TRANSITION_NAME = "hamuguna";

    public static final String[] PROJECTION_GALLERY = new String[]{MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE};

    public static void collectPicturesInfo(Context ctx, ArrayList<String> imagePaths,
                                           ArrayList<String> imageNames) {
        Cursor imageCursor = ctx
                .getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        PROJECTION_GALLERY, null, null, null);

        if (imageCursor == null) {
            return;
        }

        if (!imageCursor.moveToFirst()) {
            return;
        }

        String imageName;
        String imagePath;

        int imagePathCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int imageNameCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);

        while (imageCursor.moveToNext()) {
            imagePath = imageCursor.getString(imagePathCol);
            imageName = imageCursor.getString(imageNameCol);

            if (imagePath != null) {
                imagePaths.add(imagePath);
                imageNames.add(imageName);
            }
        }

        imageCursor.close();
    }

    public static Bitmap getBitmap(String path, boolean thumbnail) {
        BitmapFactory.Options opt = new BitmapFactory.Options();

        opt.inSampleSize = thumbnail ? 4 : 2;

        return BitmapFactory.decodeFile(path, opt);
    }
}
