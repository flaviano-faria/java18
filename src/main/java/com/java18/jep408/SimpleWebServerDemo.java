package com.java18.jep408;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JEP 408 (JDK 18): Simple Web Server — programmatic file server using
 * {@link SimpleFileServer#createFileServer(InetSocketAddress, Path, SimpleFileServer.OutputLevel)}.
 * <p>
 * CLI equivalent: run {@code jwebserver} from the JDK {@code bin} directory (same underlying stack).
 */
public class SimpleWebServerDemo {

    public static void main(String[] args) throws IOException {
        int port = parsePort(args);
        Path root = resolveWebRoot(args);

        if (!Files.isDirectory(root)) {
            System.err.println("Web root must be an existing directory: " + root);
            System.err.println("Create project folder www/ or pass absolute path as second argument.");
            System.exit(1);
        }

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", port);
        HttpServer server = SimpleFileServer.createFileServer(
            address,
            root,
            SimpleFileServer.OutputLevel.INFO
        );

        System.out.println("JEP 408 - Simple Web Server (programmatic API)");
        System.out.println("Root (absolute): " + root);
        System.out.println("Listening: http://127.0.0.1:" + port + "/");
        System.out.println("Press Enter to stop.");
        server.start();

        try {
            System.in.read();
        } finally {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

    private static int parsePort(String[] args) {
        if (args.length < 1) {
            return 8080;
        }
        try {
            int p = Integer.parseInt(args[0]);
            if (p < 1 || p > 65535) {
                throw new IllegalArgumentException("port out of range");
            }
            return p;
        } catch (RuntimeException e) {
            System.err.println("Usage: SimpleWebServerDemo [port] [absoluteWebRoot]");
            System.err.println("Default port: 8080. Default root: <cwd>/www");
            throw e;
        }
    }

    /**
     * {@link SimpleFileServer} requires an absolute, readable directory path.
     */
    private static Path resolveWebRoot(String[] args) {
        if (args.length >= 2) {
            return Path.of(args[1]).toAbsolutePath().normalize();
        }
        return Path.of("www").toAbsolutePath().normalize();
    }
}
