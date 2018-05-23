package com.example.flaviuoprea.shopapp.data.model;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.flaviuoprea.shopapp.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by flaviuoprea on 1/4/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {

    private ShoppingCart mShoppingCart;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Product> mProductsList = Collections.emptyList();
    private OnItemClickListener listener;
    private boolean mIsCheckoutActivity;

    public ProductAdapter(Context context, boolean checkoutActivity) {
        mContext = context;
        mIsCheckoutActivity = checkoutActivity;
        mInflater = LayoutInflater.from(context);
        mShoppingCart = ShoppingCart.newInstance();
    }

    public void setItems(List<Product> productList) {
        mProductsList = productList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(mInflater.inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Product product = mProductsList.get(position);

        holder.mNameTextView.setText(product.getName());
        holder.mPriceTextView.setText("Pret: " + String.valueOf(product.getPret()));
        Glide.with(holder.itemView.getContext())
                .load("https://cntronic.com/data/product/lg/" + product.getImagine())
                .into(holder.mImageView);

        holder.mAddButton.setOnClickListener(v -> {
                    mShoppingCart.addProduct(product);
                    listener.onItemClickListener(mShoppingCart);
                }
        );
        holder.mRemoveButton.setOnClickListener(v -> {
                    mShoppingCart.removeProduct(product);
                    listener.onItemClickListener(mShoppingCart);
                }
        );

    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(ShoppingCart shoppingCart);
    }


    public class Holder extends RecyclerView.ViewHolder {

        private AppCompatImageView mImageView;
        private AppCompatTextView mPriceTextView;
        private AppCompatTextView mNameTextView;
        private AppCompatButton mAddButton;
        private AppCompatButton mRemoveButton;

        public Holder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mPriceTextView = itemView.findViewById(R.id.text_view_price);
            mNameTextView = itemView.findViewById(R.id.text_view_name);
            mAddButton = itemView.findViewById(R.id.button_add_to_cart);
            mRemoveButton = itemView.findViewById(R.id.button_remove_from_cart);
            if (mIsCheckoutActivity) {
                mAddButton.setVisibility(View.GONE);
                mRemoveButton.setVisibility(View.GONE);
            }
        }
    }
}
