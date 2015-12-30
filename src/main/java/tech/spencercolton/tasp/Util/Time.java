package tech.spencercolton.tasp.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Spencer Colton
 */
public class Time {

    private static final int TICKS_IN_DAY = 24000;
    public static final int TICKS_IN_SECOND = 20;
    private static final long NOON = 6000L;
    private static final long NIGHT = 12000L;
    private static final long MIDNIGHT = 18000L;
    private static final float MINUTES_TO_TICKS_FACTOR = 50.0F / 3.0F;
    private static final int HOURS_TO_TICKS_FACTOR = 1000;
    private static final int DAY_RESET_NUMBER = 18000;
    private static final int HOURS_IN_DAY = 24;
    private static final int AM_PM_BREAK = 12;
    private static final float MINUTES_IN_HOUR = 60.0F;

    public static Long timeToBukkitTime(String s) {
        if (s.equals("noon"))
            return NOON;
        if (s.equals("night"))
            return NIGHT;
        if (s.equals("midnight"))
            return MIDNIGHT;
        if (s.equals("day"))
            return 0L;

        try {
            int i = Integer.parseInt(s);
            if (!(i < 0) && !(i >= TICKS_IN_DAY))
                return (long) i;
        } catch (NumberFormatException ignored) {
        }

        SimpleDateFormat f2 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        try {
            Date d = f2.parse(s);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int xz = c.get(Calendar.MINUTE);
            int yz = (int) (xz * MINUTES_TO_TICKS_FACTOR);
            int gz = c.get(Calendar.HOUR_OF_DAY);
            int hz = (gz >= 6) ? ((gz - 6) * HOURS_TO_TICKS_FACTOR) : (DAY_RESET_NUMBER + (1000 * gz));
            return (long) (yz + hz);
        } catch (ParseException e) {
            try {
                Date d = f.parse(s);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int xz = c.get(Calendar.MINUTE);
                int yz = (int) (xz * MINUTES_TO_TICKS_FACTOR);
                int gz = c.get(Calendar.HOUR_OF_DAY);
                int hz = (gz >= 6) ? ((gz - 6) * 1000) : (18000 + (1000 * gz));
                return (long) (yz + hz);
            } catch (ParseException e2) {
                return null;
            }
        }
    }

    private static Calendar getCalFromString(String s) {
        if (s.equals("noon")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.AM_PM, Calendar.PM);
            return c;
        }
        if (s.equals("midnight")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.AM_PM, Calendar.AM);
            return c;
        }
        if (s.equals("day")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 6);
            c.set(Calendar.AM_PM, Calendar.AM);
            return c;
        }
        if (s.equals("night")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.HOUR, 6);
            c.set(Calendar.AM_PM, Calendar.PM);
            return c;
        }
        SimpleDateFormat f2 = new SimpleDateFormat("hh:mma");
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(f2.parse(s));
            return c;
        } catch (ParseException e) {
            try {
                Calendar c = Calendar.getInstance();
                c.setTime(f.parse(s));
                return c;
            } catch (ParseException e2) {
                return null;
            }
        }
    }

    public static String niceFormatTime(long time) {
        long hours = time / 1000;
        hours += 6;
        if (hours >= HOURS_IN_DAY) {
            hours -= HOURS_IN_DAY;
        }

        long m2 = time % 1000;
        long min2 = (long) (((float) m2 / HOURS_TO_TICKS_FACTOR) * MINUTES_IN_HOUR);

        String ampm2 = "AM";
        if (hours >= AM_PM_BREAK) {
            if (hours > AM_PM_BREAK)
                hours %= AM_PM_BREAK;
            ampm2 = "PM";
        }

        if (hours == 0)
            hours = AM_PM_BREAK;
        String finH2 = Long.toString(hours);
        String finMin2 = min2 < 10 ? "0" + Long.toString(min2) : Long.toString(min2);
        return finH2 + ":" + finMin2 + " " + ampm2;
    }

    @SuppressWarnings({"MagicConstant"})
    public static String prettyPlayerDate(String s) {
        Calendar c = getCalFromString(s);
        if (c == null)
            return null;
        String ampm = c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        int min = c.get(Calendar.MINUTE);
        String minFin = min < 10 ? "0" + Integer.toString(min) : Integer.toString(min);
        int hour = c.get(Calendar.HOUR);
        String hourFin = hour == 0 ? "12" : Integer.toString(hour);
        return hourFin + ":" + minFin + " " + ampm;
    }
}
