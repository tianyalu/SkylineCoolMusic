package com.sty.skyline.coolmusic.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Android 6.0 上权限分为<b>正常</b>和<b>危险</b>级别
 * <ul>
 *     <li>正常级别权限：开发者仅仅需要在AndroidManifest.xml中声明后应用就会被允许拥有的权限，如：android.permission.INTERNET</li>
 *     <li>危险级别权限：开发者需要在AndroidManifest.xml中声明，并且在允许时要动态申请，用户同意后应用才会被允许拥有该权限，如android.permission.WRITE_EXTERNAL_STORAGE</li>
 * </ul>
 * 有米的以下权限需要在Android 6.0 上被允许，有米广告才能正常工作，开发者需要在调用有米的任何代码前提前让用户允许权限
 * <ul>
 *     <li>必须申请的权限</li>
 *     <ul>
 *         <li>android.permission.READ_PHONE_STATE</li>
 *         <li>android.permission.WRITE_EXTERNAL_STORAGE</li>
 *     </ul>
 *     <li>可选的申请权限</li>
 *     <ul>
 *         <li>android.permission.ACCESS_FINE_LOCATION</li>
 *     </ul>
 * </ul>
 * Created by Shi Tianyi on 2018/5/5/0005.
 */

public class PermissionHelper {
    private static final String TAG = PermissionHelper.class.getSimpleName();

    /**
     * 小tips:这里的int数值不能太大，否则不会弹出请求权限提示，测试的时候改到1000就不会弹出请求了
     */
    private final static int READ_PHONE_STATE_CODE = 101;
    private final static int WRITE_EXTERNAL_STORAGE_CODE = 102;
    private final static int REQUEST_OPEN_APPLICATION_SETTINGS_CODE = 12345;

    private Activity mActivity;
    private OnApplyPermissionListener onApplyPermissionListener;

    private PermissionModel[] mPermissionModels = new PermissionModel[]{
            new PermissionModel("电话", Manifest.permission.READ_PHONE_STATE,
                    "我们需要读取手机信息的权限来标识您的身份", READ_PHONE_STATE_CODE),
            new PermissionModel("存储空间", Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "我们需要您允许我们读写您的存储卡以保存一些临时数据", WRITE_EXTERNAL_STORAGE_CODE)
    };

    public PermissionHelper(Activity activity){
        mActivity = activity;
    }

    public void setOnApplyPermissionListener(OnApplyPermissionListener listener){
        this.onApplyPermissionListener = listener;
    }

    /**
     * 该方法演示如何在Android 6.0上运行时申请权限
     */
    public void applyPermissions(){
        try{
            for(PermissionModel model : mPermissionModels){
                if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, model.permission)){
                    ActivityCompat.requestPermissions(mActivity, new String[]{model.permission}, model.requestCode);
                    return;
                }
            }
            if(onApplyPermissionListener != null){
                onApplyPermissionListener.onAfterApplyAllPermission();
            }
        }catch (Throwable e){
            LogUtils.e(TAG, e);
        }
    }

    /**
     * 对应Activity的 {@code onRequestPermissionsResult(...)}方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case READ_PHONE_STATE_CODE:
            case WRITE_EXTERNAL_STORAGE_CODE:
                //如果用户不允许，我们视情况发起二次请求或者引导用户到应用页面手动打开
                if(PackageManager.PERMISSION_GRANTED != grantResults[0]){
                    //二次请求，表现为：以前请求过这个权限，但是用户拒绝了
                    //在第二次请求的时候，会有一个”不再提示"的checkbox
                    //因此这里需要给用户解释一下我们为什么需要这个权限，否则用户可能会永久不再激活这个申请
                    //方便用户理解我们为什么需要这个权限
                    if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])){
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                                .setTitle("权限申请")
                                .setMessage(findPermisssionExplain(permissions[0]))
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        applyPermissions();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.show();
                    }else{ //到这里表示已经第3+次请求，而且此时用户永久拒绝了，此时我们引导用户到应用权限页面让用户手动打开权限
                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                                .setTitle("权限申请")
                                .setMessage("请在打开的窗口的权限中开启" + findPermisssionName(permissions[0]) + "权限，以正常使用本应用")
                                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        openApplicationSettings(REQUEST_OPEN_APPLICATION_SETTINGS_CODE);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mActivity.finish();
                                    }
                                });
                        builder.setCancelable(false);
                        builder.show();
                    }
                    return;
                }

                //到这里表示用户允许了本次请求，我们继续检查是否还有待申请的权限没有申请
                if(isAllRequestedPermissionGranted()){
                    if(onApplyPermissionListener != null){
                        onApplyPermissionListener.onAfterApplyAllPermission();
                    }
                }else{
                    applyPermissions();
                }
                break;
            default:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_OPEN_APPLICATION_SETTINGS_CODE:
                if(isAllRequestedPermissionGranted()){
                    if(onApplyPermissionListener != null){
                        onApplyPermissionListener.onAfterApplyAllPermission();
                    }
                }else{
                    mActivity.finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 打开应用设置界面
     * @param requestCode
     * @return
     */
    private boolean openApplicationSettings(int requestCode){
        try{
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + mActivity.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            // Android L 之后Activity的启动模式发生了一些变化
            // 如果用了下面的Intent.FLAG_ACTIVITY_NEW_TASK,并且是startActivityForResult
            // 那么会在打开新的activity的时候就会立即回调onActivityResult
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivityForResult(intent, requestCode);
            return true;
        }catch (Throwable e){
            LogUtils.e(TAG, e);
        }
        return false;
    }

    /**
     * 判断是否所有的权限都被授权了
     * @return
     */
    public boolean isAllRequestedPermissionGranted(){
        for(PermissionModel model : mPermissionModels){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, model.permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 查找申请权限的解释短语
     * @param permission
     * @return
     */
    private String findPermisssionExplain(String permission){
        if(mPermissionModels != null){
            for(PermissionModel model : mPermissionModels){
                if(model != null && model.permission != null && model.permission.equals(permission)){
                    return model.explain;
                }
            }
        }
        return null;
    }

    /**
     * 查找申请权限的名称
     * @param permission
     * @return
     */
    private String findPermisssionName(String permission){
        if(mPermissionModels != null){
            for(PermissionModel model : mPermissionModels){
                if(model != null && model.permission != null && model.permission.equals(permission)){
                    return model.name;
                }
            }
        }
        return null;
    }

    private static class PermissionModel{
        /**
         * 权限名称
         */
        public String name;
        /**
         * 请求的权限
         */
        public String permission;
        /**
         * 解析为什么请求这个权限
         */
        public String explain;
        /**
         * 请求代码
         */
        public int requestCode;

        public PermissionModel(String name, String permission, String explain, int requestCode){
            this.name = name;
            this.permission = permission;
            this.explain = explain;
            this.requestCode = requestCode;
        }
    }

    /**
     * 权限申请事件监听
     */
    public interface OnApplyPermissionListener{
        /**
         * 申请所有权限之后的逻辑
         */
        void onAfterApplyAllPermission();
    }
}
