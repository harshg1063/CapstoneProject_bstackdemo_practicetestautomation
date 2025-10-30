package com.browserstack.tests;
 
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
 
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
 
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
 
public class SampleApp_NativeExample {
	AndroidDriver<MobileElement> driver;
	@BeforeTest
	public void setup() throws Exception {
		System.out.println("===========setup============");

		String appiumServer = "http://localhost:4723/wd/hub";
		DesiredCapabilities caps = new DesiredCapabilities();
		//DeviceDetails
		caps.setCapability("udid", "c60c1a73");    					//adb devices
		caps.setCapability("platformName", "android");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		caps.setCapability(MobileCapabilityType.NO_RESET, true);
		//AppDetails
		//APK info
		//adb shell dumpsys window | find "mCurrentFocus"
		//adb -s deviceID shell dumpsys window | find "mCurrentFocus"

		// mCurrentFocus=Window{fe3035c u0 io.appium.android.apis/io.appium.android.apis.ApiDemos}
		caps.setCapability("appPackage", "io.appium.android.apis"); 
		caps.setCapability("appActivity", "io.appium.android.apis.ApiDemos"); 
		//create a session
//		driver = new AppiumDriver<MobileElement>(new URL(appiumServer),caps);
		driver = new AndroidDriver<MobileElement>(new URL(appiumServer),caps);
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public void teardown() throws Exception {

		Thread.sleep(7000);
		driver.quit();
		System.out.println("===========teardown============");
	}

	@Test
	public void verifyDeviceLocation() {
		System.out.println("==============verifyDeviceLocation========================");
		System.out.println("Latitude: " + driver.location().getLatitude());
		System.out.println("Long: " + driver.location().getLongitude());
		System.out.println("Altitude: " + driver.location().getAltitude());
	}
 
	
	@Test
	public void verifyCurrentContext() {
		System.out.println("==============verifyCurrentContext========================");
		System.out.println("CurrentContext: " + driver.getContext());

		//get available context
		driver.getContextHandles();
		for (String contextname : driver.getContextHandles()) {
			System.out.println("Avaialble Context: " + contextname);
		}
		//Switch to WebView 
//		driver.context("WEBVIEW_stetho_com.google.android.apps.messaging");
//		driver.conte

	}

	//@Test
	public void verifyLock_unLockDevice() {
		System.out.println("==============verifyLock_unLockDevice========================");
		boolean isDeviceLockedbefore = driver.isDeviceLocked();
		System.out.println("isDeviceLocked before lock evenet: " + isDeviceLockedbefore);
//		driver.lockDevice();
		driver.lockDevice(Duration.ofSeconds(10));

		boolean isDeviceLocked = driver.isDeviceLocked();
		System.out.println("isDeviceLocked: " + isDeviceLocked);

		//unlock
//		driver.unlockDevice();

	}

	@Test
	public void VerifyOrientation() throws Exception {
		System.out.println("==============VerifyOrientation========================");
		ScreenOrientation Orient = driver.getOrientation();
		System.out.println("Cuurent Orentiation: " + Orient);
		//rotate to LANDSCAPE
		driver.rotate(Orient.LANDSCAPE);

		Thread.sleep(5000);
		//rotate to PORTRAIT
		driver.rotate(Orient.PORTRAIT);
	}

	@Test
	public void VerifyWifi() throws Exception {
		System.out.println("==============VerifyWifi========================");
		//turn on WIFI
		driver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().build());

		//turn on WIFI
		driver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().build());

		//get Current connection State
		ConnectionState connectionState = driver.getConnection();
		System.out.println("Current connectionState: " + connectionState);
	}

	@Test
	public void VerifyNotification() throws Exception {
		System.out.println("==============VerifyWifi========================");
		driver.openNotifications();
		System.out.println("===============Notification panel opened=============");
	}

	@Test
	public void VerifyBattery() throws Exception {
		System.out.println("==============VerifyBattery========================");
		driver.getBatteryInfo().getLevel();
		driver.getBatteryInfo().getState();
		System.out.println("BatteryInfo level: " +driver.getBatteryInfo().getLevel());
		System.out.println("BatteryInfo State: " +driver.getBatteryInfo().getState());
	}

}