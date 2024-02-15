package com.spierings.protestupdates.Animations;

import android.content.Context;
import android.view.View;

public interface AnimationsInterface {

    void fadeInView (Context context, View view);
    void fadeOutView(Context context, View view, boolean fadeOutTransition, View fadeInView);

}
