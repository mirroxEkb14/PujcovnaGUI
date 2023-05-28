package cz.upce.fei.boop.pujcovna.util;

/**
 * Třída obsahuje metody pro výpis systémových vlastností.
 */
public final class SystemInfo {

    public static String javaVersion() { return System.getProperty("java.version"); }

    public static String javaFxVersion() { return System.getProperty("javafx.version");}

    public static String osName() { return System.getProperty("os.name"); }

    public static String osArch() { return System.getProperty("os.arch"); }

    public static String osVersion() { return System.getProperty("os.version"); }

    public static String dejVlastnostiSystemu() {
        final String osName = SystemInfo.osName();
        final String osArch = SystemInfo.osArch();
        final String osVersion = SystemInfo.osVersion();
        final String javaVersion = SystemInfo.javaVersion();
        final String javaFxVersion = SystemInfo.javaFxVersion();

        return String.format("""
                os.name %s
                os.arch %s
                os.version %s
                java.version %s
                javafx.version %s
                """, osName, osArch, osVersion, javaVersion, javaFxVersion);
    }
}
