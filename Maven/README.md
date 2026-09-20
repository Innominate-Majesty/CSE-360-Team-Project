# Run Foundations F26 with Maven

This folder lets VS Code build and run the JavaFX application stored in
`../TP1/Foundations-F26/src`. It includes the Maven Wrapper, so Maven
does not need to be installed separately.

## Requirements

- JDK 25
- Internet access on the first run so the wrapper and project dependencies can
  be downloaded
- VS Code's **Extension Pack for Java** (recommended for IDE support)

## Run

Open the repository in VS Code, open a terminal, and run:

### macOS or Linux

```sh
cd Maven
./mvnw javafx:run
```

### Windows

```bat
cd Maven
mvnw.cmd javafx:run
```

The first run downloads Maven, JavaFX, and H2 automatically. Later runs reuse
the downloaded copies.

## Compile without launching

```sh
cd Maven
./mvnw clean compile
```

On Windows, replace `./mvnw` with `mvnw.cmd`.
