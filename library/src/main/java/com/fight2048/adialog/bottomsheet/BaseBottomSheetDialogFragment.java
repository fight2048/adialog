package com.fight2048.adialog.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.fight2048.adialog.R;
import com.fight2048.adialog.androidx.ADialogListener;
import com.fight2048.adialog.common.BaseViewHolder;
import com.fight2048.adialog.common.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description:
 */
public class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = BaseBottomSheetDialogFragment.class.getName();
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";
    private static final String PEEKHEIGHT = "peekHeight";
    protected int margin;//左右边距
    protected int width = -1;//宽度,-2代表包裹内容
    protected int height = -2;//高度，-2代表包裹内容
    protected float dimAmount = 0.5F;//灰度深浅
    protected int gravity;//是否底部显示
    @StyleRes
    protected int animStyle;
    @LayoutRes
    protected int layoutId;
    protected ADialogListener.OnDialogFragmentConvertListener mConvertListener;
    protected Dialog dialog;
    protected Integer peekHeight;//之所以使用Integer而不用int，就是希望判空来判断是否曾经设置过
    protected BottomSheetBehavior<FrameLayout> behavior;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_Dialog);
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
            peekHeight = savedInstanceState.getInt(PEEKHEIGHT);
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
        if (peekHeight != null && behavior != null) {
            behavior.setPeekHeight(peekHeight);
        }
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
        if (peekHeight != null) {
            outState.putInt(PEEKHEIGHT, peekHeight);
        }
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
            View decorView = window.getDecorView();
            behavior = BottomSheetBehavior.from(decorView.findViewById(com.google.android.material.R.id.design_bottom_sheet));
        }
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void convertView(BaseViewHolder holder, BaseBottomSheetDialogFragment dialog) {
        if (mConvertListener != null) {
            mConvertListener.convert(holder, dialog);
        }
    }

    public BaseBottomSheetDialogFragment setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public BaseBottomSheetDialogFragment setDialog(Dialog dialog) {
        this.dialog = dialog;
        return this;
    }

    public BaseBottomSheetDialogFragment setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseBottomSheetDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseBottomSheetDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseBottomSheetDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseBottomSheetDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseBottomSheetDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    public BaseBottomSheetDialogFragment show(FragmentManager manager) {
        super.show(manager, TAG);
        return this;
    }

    public BaseBottomSheetDialogFragment setConvertListener(ADialogListener.OnDialogFragmentConvertListener listener) {
        this.mConvertListener = listener;
        return this;
    }

    public BaseBottomSheetDialogFragment setPeekHeight(int peekHeight) {
        this.peekHeight = peekHeight;
        return this;
    }

    public void updatePeekHeight(int peekHeight) {
        if (behavior != null) {
            behavior.setPeekHeight(peekHeight);
        }
    }
}