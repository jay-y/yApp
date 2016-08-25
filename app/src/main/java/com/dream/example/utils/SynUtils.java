package com.dream.example.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import org.yapp.utils.Toast;

import java.util.regex.Pattern;

/**
 * Description: 综合工具类. <br>
 * Date: 2016/3/18 11:48 <br>
 * Author: ysj
 */
public class SynUtils {

    /**
     * @param context
     * @param text
     * @param success
     */
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("y_copy", text);
        ClipboardManager manager =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.showMessageForButtomShort(success);
    }

    /**
     * is url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }
}
