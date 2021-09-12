package com.encore.ete.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "classpath:features",
        glue = {"com.encore.ete.steps"},
        plugin = { "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "summary", "rerun:target/rerun1.txt" }
        /* , tags = "@website" */
)
public class CucumberTestNGRunner extends AbstractTestNGCucumberTests {
}
