
package com.example.listeners;

import com.example.config.Config;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private final int max = Config.getInt("retry.count");

    @Override
    public boolean retry(ITestResult result) {
        if (count < max) {
            count++;
            return true;
        }
        return false;
    }
}
