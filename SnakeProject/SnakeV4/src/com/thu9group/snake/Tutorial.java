package com.thu9group.snake;



import com.thu9group.snake.R;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class Tutorial extends Activity  implements View.OnClickListener {

	private Button mainMenu;
	private int curPos;

	private final int NO_OF_IMAGES = 8;
	 private Integer[] mImageIds = {
	            R.drawable.snakeone,
	            R.drawable.snaketwo,
	            R.drawable.snakethree,
	            R.drawable.snakefour,
	            R.drawable.snakefive,
	            R.drawable.snakesix,
	            R.drawable.snakeseven,
	            R.drawable.snakeeight
	    };

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tutorial);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    mainMenu = (Button)findViewById(R.id.menuTut);
		mainMenu.setOnClickListener(this);
		

		final ImageView iv=(ImageView) findViewById(R.id.imageShower);
	    final Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    iv.setVisibility(View.VISIBLE);
        Drawable d=getResources().getDrawable(mImageIds[0]);
        iv.setImageDrawable(d); 
	    gallery.setAdapter(new ImageAdapter(this));

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	curPos = position;
	        	 iv.setVisibility(View.VISIBLE);
	             Drawable d=getResources().getDrawable(mImageIds[position]);
	             iv.setImageDrawable(d);  
	            Toast.makeText(Tutorial.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	        
	    });
	    
	   ImageView leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
       ImageView rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
       
       leftArrowImageView.setOnClickListener(new OnClickListener() {
    	   
           @Override
           public void onClick(View v) {
               if (curPos > 0) {
                   --curPos;

               }

               gallery.setSelection(curPos, false);
               iv.setVisibility(View.VISIBLE);
	            Drawable d=getResources().getDrawable(mImageIds[curPos]);
	            iv.setImageDrawable(d);
           }
       });

       rightArrowImageView.setOnClickListener(new OnClickListener() {

           @Override
           public void onClick(View v) {
        	 
               if (curPos < NO_OF_IMAGES - 1) {
                   ++curPos;

               }

               gallery.setSelection(curPos, false);
               iv.setVisibility(View.VISIBLE);
	            Drawable d=getResources().getDrawable(mImageIds[curPos]);
	            iv.setImageDrawable(d);

           }
       });
	    
	}
	
	 


	@Override
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.menuTut:
				Intent newIntent = new Intent(this, MainActivity.class);
				startActivity(newIntent);
		    	break;
		
		}
		
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	  

	    public ImageAdapter(Context c) {
	        mContext = c;
	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = attr.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        attr.recycle();
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);

	        imageView.setImageResource(mImageIds[position]);
	        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }

		
	}

}
