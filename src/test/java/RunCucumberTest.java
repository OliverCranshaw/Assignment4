import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources"},
        glue = "scenario",
        plugin = {"pretty", "html:target/cucumber.html"},
        dryRun = false,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        publish = true)

public class RunCucumberTest {

}
