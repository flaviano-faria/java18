package com.java18.jep418;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.spi.InetAddressResolver;
import java.net.spi.InetAddressResolver.LookupPolicy;
import java.net.spi.InetAddressResolverProvider;
import java.util.stream.Stream;

/**
 * JEP 418 (JDK 18): Internet-Address Resolution SPI — registered via
 * {@code META-INF/services/java.net.spi.InetAddressResolverProvider}.
 * <p>
 * Maps a fixed demo hostname to {@code 127.0.0.77} and delegates all other lookups to the
 * JDK {@linkplain InetAddressResolverProvider.Configuration#builtinResolver() built-in resolver}.
 */
public class CourseInetAddressResolverProvider extends InetAddressResolverProvider {

    /** Hostname resolved by this demo (not a real DNS name). */
    public static final String DEMO_HOSTNAME = "jep418.course.local";

    /** IPv4 address returned for {@link #DEMO_HOSTNAME}. */
    public static final byte[] DEMO_IPV4 = {127, 0, 0, 77};

    @Override
    public InetAddressResolver get(Configuration configuration) {
        return new CourseInetAddressResolver(configuration.builtinResolver());
    }

    @Override
    public String name() {
        return "java18-course InetAddressResolver (JEP 418 demo)";
    }

    private static final class CourseInetAddressResolver implements InetAddressResolver {

        private final InetAddressResolver builtin;

        CourseInetAddressResolver(InetAddressResolver builtin) {
            this.builtin = builtin;
        }

        @Override
        public Stream<InetAddress> lookupByName(String host, LookupPolicy lookupPolicy)
                throws UnknownHostException {
            if (host != null && DEMO_HOSTNAME.equalsIgnoreCase(host)) {
                int ch = lookupPolicy.characteristics();
                boolean wantV4 = (ch & LookupPolicy.IPV4) != 0;
                if (!wantV4) {
                    throw new UnknownHostException(host + " (demo mapping is IPv4-only)");
                }
                InetAddress mapped = InetAddress.getByAddress(DEMO_HOSTNAME, DEMO_IPV4);
                return Stream.of(mapped);
            }
            return builtin.lookupByName(host, lookupPolicy);
        }

        @Override
        public String lookupByAddress(byte[] addr) throws UnknownHostException {
            if (addr != null
                    && addr.length == 4
                    && addr[0] == DEMO_IPV4[0]
                    && addr[1] == DEMO_IPV4[1]
                    && addr[2] == DEMO_IPV4[2]
                    && addr[3] == DEMO_IPV4[3]) {
                return DEMO_HOSTNAME;
            }
            return builtin.lookupByAddress(addr);
        }
    }
}
