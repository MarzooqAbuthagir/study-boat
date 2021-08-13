package com.we_connect_students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<silder> silders;
    private ImageLoader imageLoader;
//    private Integer [] images = {R.drawable.mslide01,R.drawable.mslide01,R.drawable.manbk};

    public ViewPagerAdapter(List<silder>silder,Context context) {
        this.silders = silder;
        this.context = context;
    }

    @Override
    public int getCount() {
        return silders.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);

        silder silder = silders.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);


        /*imageLoader = Customizevolleysilder.getInstance(context).getImageLoader();
        imageLoader.get(silder.getImage(),ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));*/
        System.out.println("image path "+ silder.getImage());
        Glide.with(context).load(silder.getImage()).into(imageView);

//        imageView.setImageResource(images[position]);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}