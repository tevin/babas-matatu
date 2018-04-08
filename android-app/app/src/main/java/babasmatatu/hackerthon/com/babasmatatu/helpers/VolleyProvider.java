package babasmatatu.hackerthon.com.babasmatatu.helpers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

/**
 * Created by munene on 4/8/2018.
 */
public class VolleyProvider{
    private static VolleyProvider instance;
    private RequestQueue queue;
    private HurlStack hurlStack;
    String bksPassword = "alice123";
    private int MY_SOCKET_TIMEOUT_MS = 20000;

    private VolleyProvider(Context ctx){
        try {
            queue = Volley.newRequestQueue(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static VolleyProvider getInstance(Context ctx){
        if(instance == null){
            instance = new VolleyProvider(ctx);
        }
        return instance;
    }

    public RequestQueue getQueue(){
        return this.queue;
    }

    public <T> Request<T> addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return getQueue().add(req);
    }
}
