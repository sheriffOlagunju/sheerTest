package com.bjss.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import util.SeleniumBaseTest;

public class TestListener implements ITestListener{
	
	WebDriver _driver = null;

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println(">>>>>>>>>> This test "+ result.getName()+" has failed <<<<<<<<<<<<<<<<<");
		String gethMethod = result.getName().toString().trim();
		printStackTrace(result.getThrowable());
		
		try {
			takeScreenShot(gethMethod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void takeScreenShot(String gethMethod) throws IOException {
		String screenshotName = "snapshot_" + System.currentTimeMillis() + ".jpg";
		_driver = SeleniumBaseTest.getDriver();
		 File scrFile = ((TakesScreenshot)_driver).getScreenshotAs(OutputType.FILE);
		 Path dir = Paths.get(System.getProperty("user.dir") + "/src/screenshots/");
		  if(!Files.exists(dir)) {
	            dir = Files.createDirectory(dir);
	        }
		  String fullPath = dir.toString()+"/"+screenshotName;
		  final File destFile = new File(fullPath);
	        
		 try {
				FileUtils.copyFile(scrFile, destFile);
				System.out.println("Taking Screenshot SnapshotName -> " + screenshotName);
				System.out.println("Located --> " + destFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	
	private static void printStackTrace(Throwable t) {
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("|       Cause     |  " + t.getCause());
		System.out.println("|       Class     |  " + t.getCause()); t.getClass().getSimpleName();
		System.out.println("|       Message   |  " + WordUtils.wrap(t.getMessage(), 70));
		System.out.println("-----------------------------------------------------------------------");
		if (t instanceof SkipException) {
			throw new SkipException("Test Skipped.");
		}
		StackTraceElement[] elementList = t.getStackTrace();
		System.out.println("ATTENTION ! Below are the lines of code where the test fails");
		System.out.println("------------------------------------------------------------------------");
		for (int j = 0; j < elementList.length; j++) {
			if (elementList[j].getClassName().contains("com.mg")) {
				System.out.println(elementList[j]);
			}
		}
		System.out.println("------------------------------------------------------------------------");
		Assert.fail("Some Exception Occurred ..... Please check the error messages.");
	}


}
