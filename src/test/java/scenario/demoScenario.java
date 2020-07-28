package scenario;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class demoScenario {

    private String finalString;

    @Before
    public void SetUp() {
        finalString = "";
    }

    @Given("^empty word \"([^\"]*)\"$")
    public void emptyString(String newString) {
        Assert.assertEquals("", newString);
    }

    @When("^adding \"([^\"]*)\" and \"([^\"]*)\"$")
    public void addString(String one, String two) {
        finalString = one + two;
        Assert.assertEquals(one+two, finalString );
    }

    @Then("^the empty string becomes \"([^\"]*)\"$")
    public void checkString(String checkString) {
        Assert.assertEquals(finalString, checkString);
    }
}
