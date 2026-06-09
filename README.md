# java18

Course-style Maven project demonstrating selected **JDK language and library features**. The repository name refers to the **Java 18** release line; the build targets **Java 21** so preview features from older releases are available as finalized APIs where applicable.

## What is included

| Area | Description |
|------|-------------|
| **File I/O and UTF-8** | Writes and reads text with Japanese characters; relies on UTF-8 source encoding and default charset behavior aligned with **JEP 400** (UTF-8 as default charset, JDK 18+). |
| **Pattern matching for `switch`** | `switch` on a `Notification` with typed `case` labels and records (**JEP 420**, second preview in JDK 18; finalized in a later release). |
| **JEP 405 — Record patterns** | `instanceof` and `switch` with record decomposition (including nested patterns) on the same `SMS` / `Whatsapp` types; see `RecordPatternDemo` (second preview in JDK 18; finalized in JDK 21). |
| **JEP 408 — Simple Web Server** | Programmatic static file server using `SimpleFileServer.createFileServer`, with static content under `www/`. |
| **JEP 418 — Internet-Address Resolution SPI** | Custom `InetAddressResolverProvider` registered under `META-INF/services/`; maps `jep418.course.local` to `127.0.0.77` and delegates other lookups to the built-in resolver. |
| **JEP 413 — Code snippets in JavaDoc** | `{@snippet}` inline and external-region examples on `SnippetDocDemo`; `maven-javadoc-plugin` passes `--snippet-path` to include `src/main/javadoc/snippets/`. |
| **`HashMap.newHashMap`** | Factory sizing the table for an expected number of mappings (**JDK 19+**, not JDK 18-specific). |

## Project layout

```
java18/
├── pom.xml
├── www/                              # Static files for JEP 408 demo (served from disk)
│   └── index.html
├── src/main/java/com/java18/
│   ├── app/Main.java                 # Default demo: file create/read
│   ├── service/FileHandler.java      # FileWriter / FileReader + try-with-resources
│   ├── switchcase/                   # Records + pattern matching switch
│   │   ├── Notification.java
│   │   ├── SMS.java
│   │   ├── Whatsapp.java
│   │   └── Service.java
│   ├── jep405/RecordPatternDemo.java # JEP 405 record patterns (instanceof + switch)
│   ├── jep408/SimpleWebServerDemo.java
│   ├── jep413/SnippetDocDemo.java   # JEP 413 {@snippet} demo (see Javadoc output)
│   ├── jep418/CourseInetAddressResolverProvider.java
│   ├── jep418/InetAddressResolutionDemo.java
│   └── demo/HashMapNewHashMapDemo.java
├── src/main/javadoc/snippets/        # External snippet sources for JEP 413
│   └── GreetingSnippet.java
├── src/main/resources/
│   └── META-INF/services/java.net.spi.InetAddressResolverProvider
└── filetest.txt                      # Created at project root when Main runs
```

## Prerequisites

- **JDK 21** or newer (matches `maven.compiler.source` / `target` in `pom.xml`)
- **Maven 3.6+**

## Build

From the project root:

```bash
mvn clean compile
```

Compiled classes are written to `target/classes/`.

API documentation (includes rendered `{@snippet}` blocks for JEP 413):

```bash
mvn javadoc:javadoc
```

Open `target/reports/apidocs/com/java18/jep413/SnippetDocDemo.html` in a browser after generation.

## Run the demos

Run each `main` from the **project root** so relative paths (`filetest.txt`, `www/`) resolve correctly.

After `mvn compile`, you can use the classpath form:

```bash
java -cp target/classes com.java18.app.Main
java -cp target/classes com.java18.switchcase.Service
java -cp target/classes com.java18.jep405.RecordPatternDemo
java -cp target/classes com.java18.jep408.SimpleWebServerDemo
java -cp target/classes com.java18.jep418.InetAddressResolutionDemo
java -cp target/classes com.java18.jep413.SnippetDocDemo
java -cp target/classes com.java18.demo.HashMapNewHashMapDemo
```

- **Main** — creates `filetest.txt` and prints its first line (Japanese greeting).
- **Service** — prints output for a typed `switch` on `SMS` vs other `Notification` types.
- **RecordPatternDemo** — JEP 405: record patterns in `instanceof`, `switch`, and a nested `Delivery(Notification, int)` example using `SMS` / `Whatsapp`.
- **SimpleWebServerDemo** — serves `www/` at `http://127.0.0.1:8080/` by default; optional args: `port` and absolute web root. Press **Enter** to stop the server.
- **InetAddressResolutionDemo** — resolves the demo hostname `jep418.course.local` via the JEP 418 SPI (optional arg: another hostname to resolve). Shows delegation by resolving `localhost` as well.
- **SnippetDocDemo** — runs sample output; the JEP 413 demonstration is the generated Javadoc (`mvn javadoc:javadoc`), which embeds inline and external snippets as copyable code blocks.
- **HashMapNewHashMapDemo** — prints a short explanation of `HashMap.newHashMap(int)` vs `new HashMap<>(int)`.

Optional port for the web server:

```bash
java -cp target/classes com.java18.jep408.SimpleWebServerDemo 9090
```

On **Windows PowerShell**, quote the classpath if you add spaces; the commands above work as-is when `target/classes` exists.

## Dependencies

No third-party libraries: only the **JDK** standard APIs (including `jdk.httpserver` types such as `com.sun.net.httpserver.SimpleFileServer` for JEP 408).

## Source encoding

`project.build.sourceEncoding` is **UTF-8** in `pom.xml`, matching the non-ASCII sample text in `FileHandler`.

## Null safety

This project is **educational demo code**, not a production library. There are no nullability annotations (`@NonNull` / `@Nullable`), no `Objects.requireNonNull` guards, and no documented null contracts on public methods. A full pass over all 13 Java sources shows **most methods are not null-safe** if callers pass unexpected `null` values.

### Summary

| Aspect | Status |
|--------|--------|
| **Overall** | **D+** for null safety — acceptable for controlled `main` demos; not safe as reusable API |
| **Null contracts** | Undocumented |
| **Validation at boundaries** | Partial — only `CourseInetAddressResolver` checks some SPI inputs |
| **Compile status** | `mvn compile` succeeds on JDK 21 |

### Known gaps (by severity)

**High**

| Location | Issue |
|----------|--------|
| `FileHandler.readFile()` | `BufferedReader.readLine()` can return `null` at EOF; method returns it unchanged |
| `RecordPatternDemo.describeNotification(Notification n)` | `switch (n)` throws `NullPointerException` when `n` is `null` |
| `RecordPatternDemo.describeDelivery(Delivery d)` | `switch (d)` throws `NullPointerException` when `d` is `null` |
| `CourseInetAddressResolver.lookupByName(...)` | On the demo hostname path, `lookupPolicy.characteristics()` is called without a null check |

**Medium**

| Location | Issue |
|----------|--------|
| Records `SMS`, `Whatsapp`, `Delivery` | `String` and `Notification` components accept `null`; no compact constructors |
| `CourseInetAddressResolverProvider.get(Configuration)` | No validation that `configuration` is non-null |
| `InetAddressResolutionDemo.main` | Optional CLI hostname (`args[0]`) is not validated before `InetAddress.getAllByName` |
| `SimpleWebServerDemo` (private helpers) | Null CLI arguments can cause NPE in `Integer.parseInt` or `Path.of` |

**Low / demo-only**

| Location | Issue |
|----------|--------|
| `Service.main`, `HashMapNewHashMapDemo.main` | Safe only because locals are constructed with literals |
| `RecordPatternDemo.describeWithInstanceof` | Handles `null` via the `else` branch (unlike `switch`) |

### What is already null-safe

- **`SnippetDocDemo`** — literals only; no nullable parameters or returns
- **`Notification`** — marker interface with no methods
- **`CourseInetAddressResolver.lookupByAddress`** — validates `addr` before reading demo IP bytes
- **`RecordPatternDemo.describeWithInstanceof`** — `instanceof` record patterns do not match `null`; falls through to `else`

### Recommended hardening (if this grows beyond demos)

1. **`FileHandler.readFile()`** — return `""`, throw `IOException`, or use `Objects.requireNonNullElse(line, "")` so the method never returns `null`.
2. **`RecordPatternDemo`** — guard `switch` selectors: `if (n == null) return "Unknown notification";` before pattern matching.
3. **Records** — add compact constructors with `Objects.requireNonNull` on `SMS`, `Whatsapp`, and `Delivery` components.
4. **SPI layer** — validate `configuration`, `lookupPolicy`, and `builtin` in `CourseInetAddressResolverProvider` before delegation.
5. **Contracts** — adopt [JSpecify](https://jspecify.dev/) or similar annotations and enforce with NullAway or the Checker Framework in CI.

### Pattern-matching note

In Java, **`instanceof` with record patterns** treats a `null` selector as non-matching (safe). **`switch` on a reference** does **not** — a `null` selector throws `NullPointerException` before any `case` runs. Demo code in `switchcase/Service` and `jep405/RecordPatternDemo` illustrates both behaviors.

## Design notes

- **Separation by JEP** — each feature lives in its own package (`jep405`, `jep408`, …) so demos can be run and taught independently.
- **Shared domain model** — `switchcase/` records (`SMS`, `Whatsapp`) are reused by JEP 420-style switch and JEP 405 record-pattern examples.
- **SPI registration** — JEP 418 relies on the standard `ServiceLoader` file at `src/main/resources/META-INF/services/java.net.spi.InetAddressResolverProvider`; the provider must be on the classpath when running `InetAddressResolutionDemo`.
- **Relative paths** — `FileHandler` writes `filetest.txt` in the working directory; `SimpleWebServerDemo` defaults to `./www`. Always run from the **project root** unless you pass an absolute web root.
- **Internal APIs** — JEP 408 uses `com.sun.net.httpserver` types shipped with the JDK; no extra Maven dependencies.

## Tests

There is no test suite wired in `pom.xml` yet. When tests exist:

```bash
mvn test
```

## Contributing

1. Fork the repository and create a branch for your change.
2. Keep demos focused and runnable from the project root unless documented otherwise.
3. Open a pull request with a short description of what you added or fixed.

## Contact

For questions or contributions, open an issue in the repository.
