package com.selectphoto.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.selectphoto.R;
import com.selectphoto.utils.CropImageUtils;
import com.selectphoto.utils.FileUtil;
import com.selectphoto.utils.ImageUtils;
import com.selectphoto.utils.PhoneUitls;

import java.io.File;

/**
 * Author: LYBo(342161360@qq.com)
 * Create at: 2016-01-06 20:22
 * Class:
 */
public class CirImageAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cirimage);
        initView();
    }

    protected void initView() {
        final CropImageView mCropImage = (CropImageView) findViewById(R.id.cropImg);
        if (getIntent() != null) {
            String path = getIntent().getStringExtra("path");
            File file = new File(path);
            //图片是否需要旋转
            int degree = ImageUtils.getBitmapDegree(file.getAbsolutePath());
            Bitmap bitmap = ImageUtils.getScaledBitmap(file.getAbsolutePath(), PhoneUitls.ScreenWH(this)[0], PhoneUitls.ScreenWH(this)[1]);
            if (bitmap != null) {
                bitmap = ImageUtils.rotateBitmapByDegree(bitmap, degree);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                mCropImage.setDrawable(drawable, 300, 300);
            } else {
                Toast.makeText(this,getString(R.string.image_notNu),Toast.LENGTH_SHORT).show();
                finish();
            }
            findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        FileUtil.writeImage(mCropImage.getCropImage(), getSDPath() + "/crop.png", 0);
                        Intent mIntent = new Intent();
                        mIntent.putExtra("cropImagePath", getSDPath() + "/crop.png");
                        setResult(CropImageUtils.REQUEST_CropPictureActivity
                                , mIntent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CirImageAct.this,getString(R.string.image_not),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
