package mfuentes.labartolaoficial.Service;

import android.app.Activity;
import android.os.AsyncTask;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;

public class MusicService extends AsyncTask {
    final private String DROPBOX_APP_KEY = "albv627f8kq7rt6";
    final private String DROPBOX_APP_SECRET = "eoea1ernktlthy7";
    final private Session.AccessType ACCESS_TYPE = Session.AccessType.DROPBOX;
    private Activity context;


    @Override
    protected Object doInBackground(Object[] objects) {
        context = (Activity) objects[0];
        AppKeyPair appKeys = new AppKeyPair(DROPBOX_APP_KEY, DROPBOX_APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys, ACCESS_TYPE);
        DropboxAPI<AndroidAuthSession> mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        mDBApi.getSession().startOAuth2Authentication(context);
        return null;
    }
}
