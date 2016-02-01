package com.selectphoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.selectphoto.utils.CropImageUtils;
import com.selectphoto.utils.FileUtil;
import com.selectphoto.view.CircleImageView;

public class SelectPhotoActivity extends Activity implements View.OnClickListener {
    private CircleImageView personal_imv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        personal_imv_photo = (CircleImageView) findViewById(R.id.personal_imv_photo);
        personal_imv_photo.setOnClickListener(this);
    }

    public void showImagePickDialog() {
        String title = getString(R.string.get_img);
        String[] choices = new String[]{getString(R.string.photograph), getString(R.string
                .choose_from_phone)};
        new AlertDialog.Builder(this).setTitle(title).setItems(choices, new DialogInterface
                .OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        CropImageUtils.takeFromCamera(SelectPhotoActivity.this, CropImageUtils.IMAGE_CODE, CropImageUtils.fileName);
                        break;
                    case 1:
                        CropImageUtils.takeFromGallery(SelectPhotoActivity.this, CropImageUtils.IMAGE_CODE);
                        break;
                }
            }
        }).setNegativeButton(getString(R.string.app_cancel), null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            // 拍照和本地选择获取图片
            case CropImageUtils.IMAGE_CODE:
                CropImageUtils.cropPicture(data, SelectPhotoActivity.this);
                break;
            // 裁剪图片后结果
            case CropImageUtils.REQUEST_CropPictureActivity:
                String path = data.getStringExtra("cropImagePath");
                personal_imv_photo.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeFile(path)));
                FileUtil.deleteFile(path);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_imv_photo:
                showImagePickDialog();
                break;
        }
    }
}

