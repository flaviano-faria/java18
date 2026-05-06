package com.java18.jep413;

/**
 * Demonstrates <strong>JEP 413 — Code Snippets in Java API Documentation</strong> ({@code {@snippet}}).
 * <p>
 * Snippets are validated and copied into generated HTML by the {@code javadoc} tool. This class uses:
 * <ul>
 *   <li>an <em>inline</em> snippet in the {@link #inlineSnippetExample()} JavaDoc;</li>
 *   <li>an <em>external</em> snippet from {@code src/main/javadoc/snippets/GreetingSnippet.java}
 *       (included via {@code --snippet-path} passed by the Maven Javadoc plugin).</li>
 * </ul>
 * <p>
 * Generate browsable documentation from the project root:
 * <pre>
 *     mvn javadoc:javadoc
 * </pre>
 * Then open {@code target/reports/apidocs/com/java18/jep413/SnippetDocDemo.html} in a browser.
 */
public final class SnippetDocDemo {

    private SnippetDocDemo() {
    }

    /**
     * Inline snippet: the block below is real Java checked by Javadoc when you run {@code mvn javadoc:javadoc}.
     * {@snippet lang="java":
     * var message = "Hello from JEP 413";
     * System.out.println(message);
     * }
     */
    public static void inlineSnippetExample() {
        var message = "Hello from JEP 413";
        System.out.println(message);
    }

    /**
     * External snippet: content is pulled from {@code GreetingSnippet.java} using the named region.
     * {@snippet file="GreetingSnippet.java" region=show-greeting}
     */
    public static void externalSnippetExample() {
        var greeting = "こんにちは";
        System.out.println(greeting);
    }

    /**
     * Entry point: runs the same code as the snippets so you can execute the module without opening Javadoc.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println("=== Inline snippet output ===");
        inlineSnippetExample();
        System.out.println("=== External snippet output ===");
        externalSnippetExample();
        System.out.println();
        System.out.println("Open target/reports/apidocs/com/java18/jep413/SnippetDocDemo.html after: mvn javadoc:javadoc");
    }
}
