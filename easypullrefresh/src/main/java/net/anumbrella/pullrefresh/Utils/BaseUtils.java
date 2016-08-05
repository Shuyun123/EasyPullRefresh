package net.anumbrella.pullrefresh.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author：anumbrella
 * Date:16/7/30 下午1:59
 */
public class BaseUtils {

    public static long lastTime = 0;

    public static String friendlyTime(long time) {
        int ct = 0;
        if (lastTime == 0) {
            ct = (int) ((System.currentTimeMillis() - time) / 1000);
        } else {
            ct = (int) ((time - lastTime) / 1000);
        }

        lastTime = time;
        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

    public static String formatDateTime(long time) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        if (0 == time) {
            return "";
        }
        return mDateFormat.format(new Date(time));
    }

    /**
     * md5加密
     *
     * @param content
     * @return
     */
    public static String Md5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 验证URL是否正确
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        Pattern p = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        return matcher.find();
    }


    public static int convertStyle(String value) {
        String[] style = new String[]{
                "BallPulse",
                "BallGridPulse",
                "BallClipRotate",
                "BallClipRotatePulse",
                "SquareSpin",
                "BallClipRotateMultiple",
                "BallPulseRise",
                "BallRotate",
                "CubeTransition",
                "BallZigZag",
                "BallZigZagDeflect",
                "BallTrianglePath",
                "BallScale",
                "LineScale",
                "LineScaleParty",
                "BallScaleMultiple",
                "BallPulseSync",
                "BallBeat",
                "LineScalePulseOut",
                "LineScalePulseOutRapid",
                "BallScaleRipple",
                "BallScaleRippleMultiple",
                "BallSpinFadeLoader",
                "LineSpinFadeLoader",
                "TriangleSkewSpin",
                "Pacman",
                "BallGridBeat",
                "SemiCircleSpin"

        };

        for (int i = 0; i < style.length; i++) {
            if (style[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }


}
