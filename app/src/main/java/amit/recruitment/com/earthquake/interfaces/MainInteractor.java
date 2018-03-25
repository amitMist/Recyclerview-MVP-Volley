package amit.recruitment.com.earthquake.interfaces;

import android.content.Context;

/**
 * Created by amit on 3/23/18.
 */

public interface MainInteractor {

    void provideData(Context context, boolean isRestoring);

    void onDestroy();

}
