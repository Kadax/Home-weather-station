package ru.kadax.mytestapp_1;

import org.json.JSONObject;
import org.json.JSONArray;

/**
 * Created by Kadax on 31.10.2017.
 */

public interface DataCallback {
    void onError(String errorMessage);
    void onSuccess(JSONArray result);
}
