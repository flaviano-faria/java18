# Java 18 Project

A simple Java application that demonstrates file handling capabilities with UTF-8 encoding support.

## Project Overview

This project showcases basic file operations in Java, specifically creating and writing text files with international character support. The application writes a Japanese greeting message to demonstrate UTF-8 encoding capabilities.

## Features

- **File Creation**: Creates text files with proper UTF-8 encoding
- **International Character Support**: Handles non-ASCII characters (Japanese text)
- **Resource Management**: Uses try-with-resources for automatic resource cleanup
- **Error Handling**: Implements proper exception handling with runtime exceptions

## Project Structure

```
java18/
├── pom.xml                          # Maven configuration file
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── java18/
│   │   │           ├── app/
│   │   │           │   └── Main.java          # Application entry point
│   │   │           └── service/
│   │   │               └── FileHandler.java   # File handling service
│   │   └── resources/
│   └── test/
│       └── java/
├── target/                          # Compiled output directory
└── filetest.txt                     # Generated output file
```

## Technical Specifications

### Java Version
- **Java Version**: 21 (as specified in pom.xml)
- **Project Name**: java18
- **Group ID**: com.ead
- **Artifact ID**: java18
- **Version**: 1.0-SNAPSHOT

### Dependencies
- No external dependencies required
- Uses only Java standard library components

### Key Components

#### Main.java
- Application entry point
- Instantiates FileHandler service
- Executes file creation operation

#### FileHandler.java
- Service class for file operations
- `createFile()` method creates and writes to a text file
- Uses `FileWriter` and `BufferedWriter` for efficient file writing
- Implements try-with-resources for automatic resource management
- Writes Japanese text: "こんにちは世界" (Hello World)

## Prerequisites

- Java 21 or higher
- Maven 3.6+ (for building the project)

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd java18
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.java18.app.Main"
   ```

## Usage

After running the application, a file named `filetest.txt` will be created in the project root directory containing the Japanese greeting message.

### Expected Output


## Development

### Building the Project
```bash
mvn clean compile
```

### Running Tests
```bash
mvn test
```

### Package the Application
```bash
mvn package
```

## File Encoding

The project is configured to use UTF-8 encoding for source files, ensuring proper handling of international characters:

```xml
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

## Error Handling

The application includes proper exception handling:
- Catches `IOException` during file operations
- Wraps checked exceptions in `RuntimeException` for simplified error handling
- Uses try-with-resources to ensure proper resource cleanup

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source and available under the [MIT License](LICENSE).

## Contact

For questions or contributions, please open an issue in the repository. 
