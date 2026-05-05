package com.java18.jep418;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Demonstrates JEP 418 by resolving {@link CourseInetAddressResolverProvider#DEMO_HOSTNAME}
 * through the installed {@link java.net.spi.InetAddressResolverProvider}.
 * <p>
 * Requires {@code META-INF/services/java.net.spi.InetAddressResolverProvider} on the classpath
 * (included when built with Maven).
 */
public final class InetAddressResolutionDemo {

    public static void main(String[] args) throws UnknownHostException {
        String host =
                args.length > 0 ? args[0] : CourseInetAddressResolverProvider.DEMO_HOSTNAME;

        System.out.println("JEP 418 - Internet-Address Resolution SPI");
        System.out.println(
                "Provider jar/resource must register "
                        + CourseInetAddressResolverProvider.class.getPackageName()
                        + ".CourseInetAddressResolverProvider");
        System.out.println();

        InetAddress[] all = InetAddress.getAllByName(host);
        System.out.println("InetAddress.getAllByName(\"" + host + "\"):");
        System.out.println("  " + Arrays.toString(all));

        if (CourseInetAddressResolverProvider.DEMO_HOSTNAME.equalsIgnoreCase(host)) {
            InetAddress raw = InetAddress.getByAddress(CourseInetAddressResolverProvider.DEMO_IPV4);
            System.out.println();
            System.out.println("Reverse path for 127.0.0.77 (InetAddress without hostname):");
            System.out.println("  getHostName: " + raw.getHostName());
        }

        InetAddress localhost = InetAddress.getByName("localhost");
        System.out.println();
        System.out.println("Delegation check (built-in resolver):");
        System.out.println("  InetAddress.getByName(\"localhost\") -> " + localhost);
    }
}
