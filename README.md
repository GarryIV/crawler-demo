# crawler-demo

To build the demo:
*  navigate to the `crawler-demo` directory
*  execute `gradlew build`

To run the demo via gradle:
* execute `gradlew execute`
* or execute `gradlew execute -Dexec.args="--pathToDirectory=<path> --outputFile=<path>"`

To run the demo directly from jar:
* execute `java -jar ./build/libs/crawler-demo.jar`
* or execute `java -jar ./build/libs/crawler-demo.jar --pathToDirectory=<path> --outputFile=<path>`

Default configuration is in `config/application.yml`, to override it use `--arg=value` syntax as it shown above.
By default, keywords are resolved via MockKeywordService,
use `--spring.profiles.active=simple-keywords` to enable more realistic implementation from SimpleKeywordService.
