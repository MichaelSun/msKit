/**
 * Copyright (C)  2007 - 2010 kaixin001
 */

package com.plugin.logger;

import android.text.TextUtils;
import android.util.Log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * dump the log to sdcard file, should replace Config.LOG by this step by step
 */
public final class Logger {

    private static String TAG = "Logger";

    public static final class Option {

        public String logTag;

        public String logDirFullPath;

        public String logFileName;
    }

    private static String LOG_DIR = null;
    private static String LOG_CURR_FILENAME = "debug_log.txt";

    private static final long MAX_LOGFILE_SIZE = 2 * 1024 * 1024;

    public static final boolean DEBUG = true;
    public static final boolean LOGVV = true && DEBUG;
    public static final boolean LOGV = true && DEBUG;
    public static final boolean LOGD = true && DEBUG;
    public static final boolean LOGW = true && DEBUG;
    public static final boolean LOGE = true && DEBUG;
    public static final boolean LOGR = true && DEBUG;

    /**
     * flag to control if dump the log to console
     */
    private static final boolean DUMP_LOG_TO_CONSOLE = true;

    private static final String VERBOSE_VERBOSE_TAG = "VV";
    private static final String VERBOSE_TAG = "VERBOSE";
    private static final String DEBUG_TAG = "DEBUG";
    private static final String WARNING_TAG = "WARNIG";
    private static final String ERORR_TAG = "ERROR";
    private static final String RELEASE_TAG = "RELEASE";

    private static Logger instance = null;

    private BufferedWriter mOutWriter = null;
    private long mCurrFileSize = 0;

    private Option mOption;

    private static final String DATE_FORMAT = "MM-dd HH:mm:ss:SSS";
    private SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);
    private Calendar mCalendar = Calendar.getInstance();

    public synchronized static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public synchronized void init(Option option) {
        if (Logger.getInstance().mOption != null) {
            throw new RuntimeException("Logger只能初始化一次");
        }

        if (TextUtils.isEmpty(option.logDirFullPath)) {
            throw new RuntimeException("Option的logDirFullPath不能为空");
        }

        File file = new File(option.logDirFullPath);
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("创建Logger文件失败，不能记录log");
        }

        mOption = option;
        if (!TextUtils.isEmpty(mOption.logTag)) {
            TAG = mOption.logTag;
        }
        LOG_DIR = mOption.logDirFullPath;
        if (!TextUtils.isEmpty(mOption.logFileName)) {
            LOG_CURR_FILENAME = mOption.logFileName;
        }
    }

    private Logger() {
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void vv(String tag, String msg) {
        vv(tag, msg, null);
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void vv(String tag, String msg, Throwable tr) {
        if (!LOGVV) {
            return;
        }

        getInstance().writeLog(VERBOSE_VERBOSE_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.d(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    /**
     * Send a verbose log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (!LOGV) {
            return;
        }

        getInstance().writeLog(VERBOSE_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.d(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    /**
     * Send a debug log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    /**
     * Send a debug log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (!LOGD) {
            return;
        }

        getInstance().writeLog(DEBUG_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.d(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    /**
     * Send a warning log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    /**
     * Send a warning log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (!LOGW) {
            return;
        }

        getInstance().writeLog(WARNING_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.d(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    /**
     * Send a error log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    /**
     * Send a error log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (!LOGE) {
            return;
        }

        getInstance().writeLog(ERORR_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.e(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    /**
     * Send a release log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void r(String tag, String msg) {
        r(tag, msg, null);
    }

    /**
     * Send a release log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void r(String tag, String msg, Throwable tr) {
        if (!LOGR) {
            return;
        }

        getInstance().writeLog(RELEASE_TAG, tag, msg, tr);
        if (DUMP_LOG_TO_CONSOLE) {
            Log.e(TAG, "[[" + tag + "]]" + msg, tr);
        }
    }

    public void close() {
        if (null != mOutWriter) {
            try {
                mOutWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mOutWriter = null;
    }

    private synchronized void writeLog(String level, String tag, String msg,
                                       Throwable tr) {
        if (null == mOutWriter) {
            boolean success = openFile();
            if (!success) {
                return;
            }
        }

        StringBuilder log = composeLog(level, tag, msg, tr);
        mCurrFileSize += log.length();

        try {
            mOutWriter.write(log.toString());
            mOutWriter.flush();
            mOutWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        if (mCurrFileSize > MAX_LOGFILE_SIZE) {
            // TODO change log file.
        }
    }

    private boolean openFile() {
        if (LOG_DIR == null) {
            throw new IllegalArgumentException("LOG_DIR can't be empty!");
        }

        File f = new File(LOG_DIR);

        boolean logDirExists = false;
        if (!f.exists()) {
            logDirExists = f.mkdirs();
        } else {
            logDirExists = true;
        }
        if (!logDirExists) {
            return false;
        }

        f = new File(LOG_DIR, LOG_CURR_FILENAME);
        mOutWriter = null;
        try {
            mOutWriter = new BufferedWriter(new OutputStreamWriter(
                                                                      new FileOutputStream(f, true)));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private StringBuilder composeLog(String level, String tag, String msg,
                                     Throwable tr) {
        StringBuilder log = new StringBuilder(512);

        log.append(composeTime());
        log.append(" ");
        log.append(level + "/" + tag);
        log.append("\t");
        if (!TextUtils.isEmpty(msg)) {
            log.append(msg);
            if (null != tr) {
                log.append("\n\t");
            }
        }
        log.append(Log.getStackTraceString(tr));

        return log;
    }

    private String composeTime() {
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        return mDateFormat.format(mCalendar.getTime());
    }
}
