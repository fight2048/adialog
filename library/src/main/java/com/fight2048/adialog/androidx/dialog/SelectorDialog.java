package com.fight2048.adialog.androidx.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fight2048.adialog.R;
import com.fight2048.adialog.androidx.ADialogListener;
import com.fight2048.adialog.common.BaseViewHolder;

import java.util.List;

/**
 * @author: fight2048
 * @e-mail: fight2048@outlook.com
 * @blog: https://github.com/fight2048
 * @time: 2020-03-07 0007 下午 10:46
 * @version: v0.0.0
 * @description: 选择器
 */
public class SelectorDialog extends BaseDialog {
    private String title;
    private List mData;
    private RecyclerView.Adapter adapter;
    private int itemLayoutId;
    private ADialogListener.OnItemConvertListener itemConvertListener;
    private ADialogListener.OnItemClickListener itemClickListener;

    public SelectorDialog(@NonNull Context context) {
        super(context);
    }

    public SelectorDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SelectorDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adialog_selector;
    }

    @Override
    public void convertView(BaseViewHolder holder, BaseDialog dialog) {
        super.convertView(holder, dialog);
        holder.setText(R.id.tv_title_selector, title)
                .setOnClickListener(R.id.iv_cancel_selector, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                })
                .setOnClickListener(R.id.tv_cancel_selector, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });

        RecyclerView recyclerView = holder.getView(R.id.recyclerView_selector);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setAdapter(adapter = new RecyclerView.Adapter<BaseViewHolder>() {
                @Override
                public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new BaseViewHolder(getLayoutInflater().inflate(itemLayoutId, parent, false));
                }

                @Override
                public void onBindViewHolder(final BaseViewHolder holder, final int position) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (itemClickListener != null) {
                                itemClickListener.onItemClick(v, holder, position, SelectorDialog.this);
                            }
                        }
                    });

                    if (itemConvertListener != null) {
                        itemConvertListener.onItemConvert(holder, position, SelectorDialog.this);
                    }
                }

                @Override
                public int getItemCount() {
                    return mData == null ? 0 : mData.size();
                }
            });
        }
    }

    public String getTitle() {
        return title;
    }

    public SelectorDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public List getData() {
        return mData;
    }

    public SelectorDialog setData(List mData) {
        this.mData = mData;
        return this;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public SelectorDialog setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public int getItemLayoutId() {
        return itemLayoutId;
    }

    public SelectorDialog setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
        return this;
    }

    public ADialogListener.OnItemConvertListener getOnItemConvertListener() {
        return itemConvertListener;
    }

    public SelectorDialog setOnItemConvertListener(ADialogListener.OnItemConvertListener itemConvertListener) {
        this.itemConvertListener = itemConvertListener;
        return this;
    }

    public ADialogListener.OnItemClickListener getOnItemClickListener() {
        return itemClickListener;
    }

    public SelectorDialog setOnItemClickListener(ADialogListener.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }
}
