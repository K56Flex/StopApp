package com.sscience.stopapp.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.science.baserecyclerviewadapter.base.BaseCommonAdapter;
import com.science.baserecyclerviewadapter.base.ViewHolder;
import com.sscience.stopapp.R;
import com.sscience.stopapp.bean.AppInfo;

import java.util.List;

import static com.sscience.stopapp.util.DiffCallBack.BUNDLE_PAYLOAD;


/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2017/1/15
 */

public class AppAdapter extends BaseCommonAdapter<List<AppInfo>> {

    private ColorMatrixColorFilter mColorFilterGrey, mColorFilterNormal; // 设置图片灰度
    private ColorMatrix mMatrix;
    private Resources mResources;

    public AppAdapter(Activity activity, RecyclerView recyclerView) {
        super(activity, recyclerView);
        mResources = activity.getResources();
        mMatrix = new ColorMatrix();
        mMatrix.setSaturation(0); // 参数大于1将增加饱和度，0～1之间会减少饱和度。0值将产生一幅灰度图像。
        mColorFilterGrey = new ColorMatrixColorFilter(mMatrix);
        mMatrix.setSaturation(1); // 参数大于1将增加饱和度，0～1之间会减少饱和度。0值将产生一幅灰度图像。
        mColorFilterNormal = new ColorMatrixColorFilter(mMatrix);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_app;
    }

    @Override
    public void convertCommon(final ViewHolder viewHolder, List<AppInfo> appInfo, final int position) {
        final AppInfo info = appInfo.get(position);
        viewHolder.setImageDrawable(R.id.iv_app_icon, info.getAppIcon());
        viewHolder.setText(R.id.tv_app_name, info.getAppName());
        viewHolder.setText(R.id.tv_app_package_name, info.getAppPackageName());

        ((TextView) viewHolder.getView(R.id.tv_app_name)).setTextColor(info.isEnable()
                ? mResources.getColor(R.color.textPrimary) : mResources.getColor(R.color.translucentBg));
        ((TextView) viewHolder.getView(R.id.tv_app_package_name)).setTextColor(info.isEnable()
                ? mResources.getColor(R.color.textSecondary) : mResources.getColor(R.color.translucentBg));
        ((ImageView) viewHolder.getView(R.id.iv_app_icon)).getDrawable().setColorFilter(info.isEnable()
                ? mColorFilterNormal : mColorFilterGrey);
    }

    @Override
    public void convertDiff(ViewHolder holder, int position, List payloads) {
        Bundle payload = (Bundle) payloads.get(0);
        boolean isEnable = payload.getBoolean(BUNDLE_PAYLOAD);
        ((AppCompatCheckBox) holder.getView(R.id.cb_select_apps)).setChecked(isEnable);
        ((TextView) holder.getView(R.id.tv_app_name)).setTextColor(isEnable
                ? mResources.getColor(R.color.textPrimary) : mResources.getColor(R.color.translucentBg));
        ((TextView) holder.getView(R.id.tv_app_package_name)).setTextColor(isEnable
                ? mResources.getColor(R.color.textSecondary) : mResources.getColor(R.color.translucentBg));
        if (isEnable) {
            mMatrix.setSaturation(1);
            mColorFilterNormal = new ColorMatrixColorFilter(mMatrix);
            ((ImageView) holder.getView(R.id.iv_app_icon)).setColorFilter(mColorFilterNormal);
        } else {
            mMatrix.setSaturation(0);
            mColorFilterGrey = new ColorMatrixColorFilter(mMatrix);
            ((ImageView) holder.getView(R.id.iv_app_icon)).setColorFilter(mColorFilterGrey);
        }
    }
}
