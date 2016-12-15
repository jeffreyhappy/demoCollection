/**
 * Cobub Razor
 *
 * An open source analytics android sdk for mobile applications
 *
 * @package     Cobub Razor
 * @author      WBTECH Dev Team
 * @copyright   Copyright (c) 2011 - 2015, NanJing Western Bridge Co.,Ltd.
 * @license     http://www.cobub.com/products/cobub-razor/license
 * @link        http://www.cobub.com/products/cobub-razor/
 * @since       Version 0.1
 * @filesource 
 */

package com.wswy.thridstatics.statics;

import android.util.Log;


/**
 * @author Cobub Logger class is responsible for Log records.
 */
class CobubLog {

    public static void v(String tag,Class<?> classobj, String msg) {

        if (!UmsConstants.DebugEnabled)
            return;

        if (UmsConstants.DebugLevel == UmsAgent.LogLevel.Debug
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Info
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Warn
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Error)
            return;

        Log.v(tag, classobj.getCanonicalName()+": "+ msg);
    }

    public static void d(String tag,Class<?> classobj, String msg) {

        if (!UmsConstants.DebugEnabled)
            return;

        if (UmsConstants.DebugLevel == UmsAgent.LogLevel.Info
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Warn
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Error)
            return;

        Log.d(tag, classobj.getCanonicalName()+": "+ msg);
    }

    public static void i(String tag,Class<?> classobj, String msg) {

        if (!UmsConstants.DebugEnabled)
            return;

        if (UmsConstants.DebugLevel == UmsAgent.LogLevel.Warn
                || UmsConstants.DebugLevel == UmsAgent.LogLevel.Error)
            return;

        Log.i(tag, classobj.getCanonicalName()+": "+ msg);
    }

    public static void w(String tag,Class<?> classobj, String msg) {

        if (!UmsConstants.DebugEnabled)
            return;

        if (UmsConstants.DebugLevel == UmsAgent.LogLevel.Error)
            return;

        Log.w(tag, classobj.getCanonicalName()+": "+ msg);
    }

    public static void e(String tag,Class<?> classobj, String msg) {
        if (!UmsConstants.DebugEnabled)
            return;
        Log.e(tag, classobj.getCanonicalName()+": "+ msg);
    }

    public static void e(String tag, Exception e) {
        if (!UmsConstants.DebugEnabled)
            return;
        Log.e(tag, e.toString());
        e.printStackTrace();
    }
}
