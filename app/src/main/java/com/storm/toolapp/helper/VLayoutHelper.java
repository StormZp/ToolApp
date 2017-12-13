package com.storm.toolapp.helper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;
import com.storm.toolapp.adapter.VLayoutAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class VLayoutHelper {

    private static VLayoutAdapter getAdapter(final Builder builder) {
        if (builder.params == null) {
            return new VLayoutAdapter(builder.context, builder.layoutHelper, builder.count) {
                @Override
                public void onBindViewHolder(@Nullable BannerViewHolder holder, int position) {
                    if (builder.onBindView != null)
                        builder.onBindView.bindView(holder, position);
                }

                @Override
                protected void onBindViewHolderWithOffset(BannerViewHolder holder, int position, int offsetTotal) {
                    if (builder.onBindViewOffset != null) {
                        builder.onBindViewOffset.bindView(holder, offsetTotal);
                    }
                }

                @Override
                public int getItemViewType(int position) {
                    return builder.viewType;
                }

                @NotNull
                @Override
                public BannerViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(builder.context).inflate(builder.res, parent, false);
                    if (getMLayoutParams() != null) {
                        view.getLayoutParams().width = super.getMLayoutParams().width;
                        view.getLayoutParams().height = super.getMLayoutParams().height;
                    }
                    return new BannerViewHolder(view);
                }
            };
        } else {
            return new VLayoutAdapter(builder.context, builder.layoutHelper, builder.count, builder.params) {
                @Override
                public void onBindViewHolder(@Nullable BannerViewHolder holder, int position) {
                    if (builder.onBindView != null)
                        builder.onBindView.bindView(holder, position);
                }

                @Override
                public int getItemViewType(int position) {
                    return builder.viewType;
                }

                @Override
                protected void onBindViewHolderWithOffset(BannerViewHolder holder, int position, int offsetTotal) {
                    if (builder.onBindViewOffset != null) {
                        builder.onBindViewOffset.bindView(holder, offsetTotal);
                    }
                }

                @NotNull
                @Override
                public BannerViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(builder.context).inflate(builder.res, parent, false);
                    ViewDataBinding dataBinding = null;
                    try {
                        dataBinding = DataBindingUtil.bind(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getMLayoutParams() != null) {
                        view.getLayoutParams().width = super.getMLayoutParams().width;
                        view.getLayoutParams().height = super.getMLayoutParams().height;
                    }
                    BannerViewHolder viewHolder = new BannerViewHolder(view);
                    if (dataBinding != null) {
                        viewHolder.setDataBinding(dataBinding);
                    }
                    return viewHolder;
                }
            };
        }
    }

    public static class Builder {
        public Context getContext() {
            return context;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public int getRes() {
            return res;
        }

        public Builder setRes(int res) {
            this.res = res;
            return this;
        }

        public OnBindView getOnBindView() {
            return onBindView;
        }

        public Builder setOnBindView(OnBindView onBindView) {
            this.onBindView = onBindView;
            return this;
        }

        public int getCount() {
            return count;
        }

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public int getViewType() {
            return viewType;
        }

        public Builder setViewType(int viewType) {
            this.viewType = viewType;
            return this;
        }

        public ViewGroup.LayoutParams getParams() {
            return params;
        }

        public Builder setParams(ViewGroup.LayoutParams params) {
            this.params = params;
            return this;
        }

        public VLayoutAdapter creatAdapter() {
            return VLayoutHelper.getAdapter(this);
        }

        public LayoutHelper getLayoutHelper() {
            return layoutHelper;
        }

        public Builder setLayoutHelper(LayoutHelper layoutHelper) {
            this.layoutHelper = layoutHelper;
            return this;
        }

        private LayoutHelper layoutHelper;
        private Context context;
        private int res;
        private OnBindView onBindView;
        private int count;
        private int viewType;
        private ViewGroup.LayoutParams params;

        public Builder setOnBindViewOffset(OnBindView onBindViewOffset) {
            this.onBindViewOffset = onBindViewOffset;
            return this;
        }

        private OnBindView onBindViewOffset;
    }

    public interface OnCreateView {
        void createView();
    }

    public interface OnBindView {
        void bindView(VLayoutAdapter.BannerViewHolder itemView, int position);
    }
}
