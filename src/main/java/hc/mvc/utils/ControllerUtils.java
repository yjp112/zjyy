package hc.mvc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jodd.util.Base64;
import jodd.util.StringUtil;

public abstract class ControllerUtils
{
  public static String redirect(boolean success, String title, String message, String next, String nextUrl, boolean closeWindow, String callback, boolean historyGo, Integer stopwatch)
  {
    StringBuilder builder = new StringBuilder("redirect:/platform/public/redirect?success=");
    builder.append(String.valueOf(success));
    builder.append("&close=").append(String.valueOf(closeWindow));
    builder.append("&his=").append(String.valueOf(historyGo));
    if (StringUtil.isNotBlank(title)) {
      builder.append("&title=");
      try {
        builder.append(URLEncoder.encode(Base64.encodeToString(title), "UTF-8"));
      } catch (UnsupportedEncodingException e) {
      }
    }
    if (StringUtil.isNotBlank(message)) {
      builder.append("&message=");
      try
      {
        builder.append(URLEncoder.encode(Base64.encodeToString(message), "UTF-8"));
      } catch (UnsupportedEncodingException e) {
      }
    }
    if (StringUtil.isNotBlank(next)) {
      builder.append("&next=");
      try {
        builder.append(URLEncoder.encode(Base64.encodeToString(next), "UTF-8"));
      } catch (UnsupportedEncodingException e) {
      }
    }
    if (StringUtil.isNotBlank(nextUrl)) {
      builder.append("&nextUrl=").append(nextUrl);
    }
    if (StringUtil.isNotBlank(callback)) {
      builder.append("&callback=").append(callback);
    }
    if ((null != stopwatch) && (stopwatch.intValue() > 0)) {
      builder.append("&stopwatch=").append(stopwatch);
    }
    return builder.toString();
  }

  public static String errorRedirect(String title, String message) {
    return redirect(false, title, message, null, null, true, null, true, null);
  }

  public static String errorRedirect(String title, String message, boolean goHistory) {
    return redirect(false, title, message, null, null, true, null, goHistory, null);
  }

  public static String successRedirect(String title) {
    return redirect(true, title, null, null, null, true, "top._main_frame.edited_callback", false, null);
  }

  public static String successRedirect(String title, String next, String nextUrl) {
    return redirect(true, title, null, next, nextUrl, true, "top._main_frame.edited_callback", false, null);
  }

  public static String successRedirect(String title, String message, String next, String nextUrl) {
    return redirect(true, title, message, next, nextUrl, true, "top._main_frame.edited_callback", false, null);
  }

  public static String successRedirect(String title, String message, String next, String nextUrl, String callback) {
    return redirect(true, title, message, next, nextUrl, true, callback, false, null);
  }
}