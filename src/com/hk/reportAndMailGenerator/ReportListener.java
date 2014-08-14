package com.hk.reportAndMailGenerator;

import com.hk.property.PropertyHelper;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.annotations.Listeners;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.List;

@Listeners({ReportListener.class})


public class ReportListener implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, final String outputDirectory) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            private File file = new File(PropertyHelper.readProperty("reportFolder") + "index.html");

            @Override
            public synchronized void start() {
                boolean flag = false;

                if (file.exists()) {
                    // Include your code here
                    SendMail.sendmail("Please find the attached report of test cases", PropertyHelper.readProperty("screenshotFolder"), PropertyHelper.readProperty("reportFolder") + "report.zip");
                    /*System.out.println("Found the file @ " + file.getAbsolutePath());*/
                    flag = true;

                }
                if (!flag) {
                    System.out.println("Didn't find the file yet");
                }

            }
        });
    }
}

