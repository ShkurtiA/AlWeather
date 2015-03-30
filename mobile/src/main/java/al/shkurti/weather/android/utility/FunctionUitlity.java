package al.shkurti.weather.android.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * Created by Armando Shkurti on 2015-03-28.
 */
public class FunctionUitlity {


    /**
     * Checks to see if the device is connected to a network (cell, wifi, etc).
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a network connection is available, otherwise false.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Cross-fades between showView and hideView
     * @param showView show view with animation
     * @param hideView hide view with animation
     */
    public static void showHideViews(final View showView ,final View hideView) {
        // Decide which view to hide and which to show.

        int mShortAnimationDuration = 300;
        if(showView != null) {
            // Set the "show" view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            showView.setAlpha(0f);
            showView.setVisibility(View.VISIBLE);

            // Animate the "show" view to 100% opacity, and clear any animation listener set on
            // the view. Remember that listeners are not limited to the specific animation
            // describes in the chained method calls. Listeners are set on the
            // ViewPropertyAnimator object for the view, which persists across several
            // animations.
            showView.animate()
                    .alpha(1f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(null);
        }

        if(hideView != null) {
            // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
            // to GONE as an optimization step (it won't participate in layout passes, etc.)
            hideView.animate()
                    .alpha(0f)
                    .setDuration(mShortAnimationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideView.setVisibility(View.GONE);
                            hideView.clearAnimation();// mundeson qe te hiqet instance per
                            //kete listener dhe mos te mbahet refernece per kete view
                        }
                    });
        }
    }


}
