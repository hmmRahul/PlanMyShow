package com.hmmRahulDev.planmyshow.utilities;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    //for custom DataBinding we use BindingAdapters so that when we
    //set any custom attribute automatically our custom code get executed
    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView,String URL)
    {
        try{
            imageView.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }catch (Exception ignored)
        {

        }
    }


}
