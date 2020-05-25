package com.panda.mvp.design.pattern.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.panda.mvp.design.pattern.lib.R;

/**
 * @author Pearce
 * @date 08/26/2019
 * @description
 */
public class TitleView extends FrameLayout {

    private boolean mShowLeftIcon;
    private Drawable mLeftIconRes;
    private boolean mShowRightIcon;
    private Drawable mRightIconRes;
    private int mTitleTextColor;

    private Context mContext;
    private LayoutInflater mFactory;

    private TextView mTitle;
    private ImageView mLeftIcon;
    private ImageView mRightIcon;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.styleable_panda_mvp_design_pattern_lib_title_view);
        mShowLeftIcon = typedArray.getBoolean(R.styleable.styleable_panda_mvp_design_pattern_lib_title_view_attr_show_left_icon, false);
        mLeftIconRes = typedArray.getDrawable(R.styleable.styleable_panda_mvp_design_pattern_lib_title_view_attr_view_left_icon);
        mShowRightIcon = typedArray.getBoolean(R.styleable.styleable_panda_mvp_design_pattern_lib_title_view_attr_show_right_icon, false);
        mRightIconRes = typedArray.getDrawable(R.styleable.styleable_panda_mvp_design_pattern_lib_title_view_attr_right_icon);
        mTitleTextColor = typedArray.getColor(R.styleable.styleable_panda_mvp_design_pattern_lib_title_view_attr_title_text_color, 0);
        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mFactory = LayoutInflater.from(context);
        View root = mFactory.inflate(R.layout.layout_panda_mvp_design_pattern_lib_title_view, this);
        mTitle = root.findViewById(R.id.id_layout_panda_mvp_design_pattern_lib_title_view_title);
        mLeftIcon = root.findViewById(R.id.id_layout_panda_mvp_design_pattern_lib_title_view_left);
        mRightIcon = root.findViewById(R.id.id_layout_panda_mvp_design_pattern_lib_title_view_right);

        if (mShowLeftIcon) {
            mLeftIcon.setVisibility(VISIBLE);
            mLeftIcon.setImageDrawable(mLeftIconRes);
        } else {
            mLeftIcon.setVisibility(INVISIBLE);
        }

        if (mShowRightIcon) {
            mRightIcon.setVisibility(VISIBLE);
            mRightIcon.setImageDrawable(mRightIconRes);
        } else {
            mRightIcon.setVisibility(INVISIBLE);
        }

        if (mTitleTextColor != 0) {
            mTitle.setTextColor(mTitleTextColor);
        }
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setLeftIconClickListener(OnClickListener listener) {
        mLeftIcon.setOnClickListener(listener);
    }

    public void setRightIconClickListener(OnClickListener listener) {
        mRightIcon.setOnClickListener(listener);
    }

    public void setTitleTextSize(float textSize) {
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void setTitleTextColor(int colorId) {
        mTitle.setTextColor(mContext.getResources().getColor(colorId));
    }

    public void setTitleGravity(int gravity) {
        mTitle.setGravity(gravity);
    }
}