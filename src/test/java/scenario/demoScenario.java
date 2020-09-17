package scenario;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class demoScenario {

    private String finalString;

    @Before
    public void SetUp() {
        finalString = "";
    }

    @Given("empty word {string}")
    public void emptyString(String newString) {
        Assert.assertEquals("", newString);
    }

    @When("adding {string} and {string}")
    public void addString(String one, String two) {
        finalString = one + two;
        Assert.assertEquals(one+two, finalString );
    }

    @Then("the empty string becomes {string}")
    public void checkString(String checkString) {
        Assert.assertEquals(finalString, checkString);
    }
}
