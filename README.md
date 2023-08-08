# crowdin-test
Crowdin test task

## Requirements:
- Java version: 1.8

## How to build runnable .jar:
### Via gradle command line:
- Open comand line at the project root
- Run `./gradlew build`
- Runnable `.jar` will be located at next path(relative to the project root): `build/libs/crowdin-test.jar`

### Via InteliJ IDEA:
- Navigated Gradle toolbar via: View -> Tool Windows -> Gradle
- In opened menu: `crowdin-test` -> Tasks -> build -> build
- Right click and select `Run...`
- Runnable `.jar` will be located at next path(relative to the project root): `build/libs/crowdin-test.jar`

## Tested on:
- Windows 10

## Example usage on Windows CMD:
- `java -jar crowdin-test.jar 12345 api_key \"*.json"`

P.S. `\` prevents globbing.

## Notes:
- File matching by regEx implemented via `PathMatcher` with regex and `SimpleFileVisitor`, but original though was to use `glob` syntax of `PathMatcher` and `SimpleFileVisitor`
- One of the requirement was to validate `Error in pattern`, but not sure what this mean, so it was ignored for now.
