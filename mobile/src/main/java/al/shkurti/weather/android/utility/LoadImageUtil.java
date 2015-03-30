package al.shkurti.weather.android.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;

import al.shkurti.weather.android.R;

/**
 * Created by Armando Shkurti on 2015-03-30.
 *
 * Here we use Universal Image Loader Library to load images to ImageView
 */
public class LoadImageUtil {

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    DisplayImageOptions options;

    /**
     * Call this method to clear cache of loaded images
     * */
    public boolean memoryManage(int item) {
        switch (item) {
            case 0:
                if(imageLoader.isInited())
                    imageLoader.clearMemoryCache();
                return true;
            case 1:
                if(imageLoader.isInited())
                    imageLoader.clearDiskCache();
                return true;
            default:
                return false;
        }
    }

    /**
     * Call this constructor to initialize the library
     *
     * @param mContext Activity context (from this the library get application context)
     * */
    public LoadImageUtil(Context mContext) {
        initLibrarySettings(mContext);
    }

    public void initLibrarySettings(Context mContext) {

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
                .threadPriority(Thread.NORM_PRIORITY)
                .threadPoolSize(4)// should use values from 1-5
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                        //.diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCache(new WeakMemoryCache())
                        //.writeDebugLogs() // Remove for release app
                .build();

        // Initialize ImageLoader with configuration.
        imageLoader.init(config);

        if(options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.ic_launcher)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .considerExifParams(true)
                    .build();
        }
    }


    public void loadBitmapToImageView(ImageView myImageView, String ImagePath) {

        imageLoader.displayImage(ImagePath, myImageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }
        });
    }

    public void removeSingleItemFromCache(String imageUri) {
        try {
            File imageFile = imageLoader.getDiskCache().get(imageUri);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            MemoryCacheUtils.removeFromCache(imageUri, imageLoader.getMemoryCache());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
