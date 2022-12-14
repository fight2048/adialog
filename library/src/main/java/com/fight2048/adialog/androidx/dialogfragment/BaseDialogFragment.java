package com.fight2048.adialog.androidx.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.fight2048.adialog.R;
import com.fight2048.adialog.androidx.ADialogListener;
import com.fight2048.adialog.common.BaseViewHolder;
import com.fight2048.adialog.common.Utils;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public class BaseDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = BaseDialogFragment.class.getName();
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";
    private int margin;//左右边距
    private int width = -1;//宽度,-2代表包裹内容
    private int height = -2;//高度，-2代表包裹内容
    private float dimAmount = 0.5F;//灰度深浅
    private int gravity;//是否底部显示
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;
    private ADialogListener.OnDialogFragmentConvertListener mConvertListener;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_Dialog);
        //setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_NoTitleBar_Fullscreen);
        layoutId = getLayoutId();
        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (dialog == null) {
            return super.onCreateDialog(savedInstanceState);
        } else {
            return dialog;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            return inflater.inflate(layoutId, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        convertView(new BaseViewHolder(view), this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    private void initWindow() {
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置dialog进入、退出的动画
            window.setWindowAnimations(animStyle);
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5F
            lp.dimAmount = dimAmount;
            lp.gravity = gravity;
            //设置dialog宽度
            if (margin > 0) {
                lp.width = Utils.getScreenWidth(getContext()) - 2 * Utils.dp2px(getContext(), margin);
            } else if (width > 0) {
                lp.width = Utils.dp2px(getContext(), width);
            } else {
                lp.width = width;
            }
            //设置dialog高度
            if (height > 0) {
                lp.height = Utils.dp2px(getContext(), height);
            } else {
                lp.height = height;
            }
            window.setAttributes(lp);
        }
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void convertView(BaseViewHolder holder, BaseDialogFragment dialog) {
        if (mConvertListener != null) {
            mConvertListener.convert(holder, dialog);
        }
    }

    public BaseDialogFragment setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public BaseDialogFragment setDialog(Dialog dialog) {
        this.dialog = dialog;
        return this;
    }

    public BaseDialogFragment setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseDialogFragment show(FragmentManager manager) {
        super.show(manager, TAG);
        return this;
    }

    public BaseDialogFragment setConvertListener(ADialogListener.OnDialogFragmentConvertListener listener) {
        this.mConvertListener = listener;
        return this;
    }
}