# java18

Course-style Maven project demonstrating selected **JDK language and library features**. The repository name refers to the **Java 18** release line; the build targets **Java 21** so preview features from older releases are available as finalized APIs where applicable.

## What is included

| Area | Description |
|------|-------------|
| **File I/O and UTF-8** | Writes and reads text with Japanese characters; relies on UTF-8 source encoding and default charset behavior aligned with **JEP 400** (UTF-8 as default charset, JDK 18+). |
| **Pattern matching for `switch`** | `switch` on a `Notification` with typed `case` labels and records (**JEP 420**, second preview in JDK 18; finalized in a later release). |
| **JEP 408 — Simple Web Server** | Programmatic static file server using `SimpleFileServer.createFileServer`, with static content under `www/`. |
| **JEP 418 — Internet-Address Resolution SPI** | Custom `InetAddressResolverProvider` registered under `META-INF/services/`; maps `jep418.course.local` to `127.0.0.77` and delegates other lookups to the built-in resolver. |
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
│   ├── jep408/SimpleWebServerDemo.java
│   ├── jep418/CourseInetAddressResolverProvider.java
│   ├── jep418/InetAddressResolutionDemo.java
│   └── demo/HashMapNewHashMapDemo.java
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

## Run the demos

Run each `main` from the **project root** so relative paths (`filetest.txt`, `www/`) resolve correctly.

After `mvn compile`, you can use the classpath form:

```bash
java -cp target/classes com.java18.app.Main
java -cp target/classes com.java18.switchcase.Service
java -cp target/classes com.java18.jep408.SimpleWebServerDemo
java -cp target/classes com.java18.jep418.InetAddressResolutionDemo
java -cp target/classes com.java18.demo.HashMapNewHashMapDemo
```

- **Main** — creates `filetest.txt` and prints its first line (Japanese greeting).
- **Service** — prints output for a typed `switch` on `SMS` vs other `Notification` types.
- **SimpleWebServerDemo** — serves `www/` at `http://127.0.0.1:8080/` by default; optional args: `port` and absolute web root. Press **Enter** to stop the server.
- **InetAddressResolutionDemo** — resolves the demo hostname `jep418.course.local` via the JEP 418 SPI (optional arg: another hostname to resolve). Shows delegation by resolving `localhost` as well.
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
