package com.bjss.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import util.SeleniumBaseTest;

public class TestListener implements ITestListener{
	
	WebDriver _driver = null;

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println(">>>>>>>>>> This test "+ result.getName()+" has failed <<<<<<<<<<<<<<<<<");
		String gethMethod = result.getName().toString().trim();
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

}
