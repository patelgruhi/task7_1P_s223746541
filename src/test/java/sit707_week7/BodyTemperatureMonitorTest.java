package sit707_week7;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class BodyTemperatureMonitorTest {

    @Test
    public void testStudentIdentity() {
        String studentId = "s223746541";
        Assert.assertNotNull("Student ID is ", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "gruhi";
        Assert.assertNotNull("Student name is ", studentName);
    }

    @Test
    public void testReadTemperature() {
        testReadTemperatureValue(-1.0);
        testReadTemperatureValue(0.0);
        testReadTemperatureValue(37.0);
        testReadTemperatureValue(41.0);
    }

    private void testReadTemperatureValue(double temperatureValue) {
        TemperatureSensor t1 = Mockito.mock(TemperatureSensor.class);
        Mockito.when(t1.readTemperatureValue()).thenReturn(temperatureValue);

        CloudService c1 = Mockito.mock(CloudService.class);
        NotificationSender n1 = Mockito.mock(NotificationSender.class);

        BodyTemperatureMonitor b1 = new BodyTemperatureMonitor(t1, c1, n1);
        Assert.assertEquals(b1.readTemperature(), temperatureValue, 0.0);
    }

    @Test
    public void testReportTemperatureReadingToCloud() {
        TemperatureReading tr1 = new TemperatureReading();

        TemperatureSensor t1 = Mockito.mock(TemperatureSensor.class);
        CloudService c1 = Mockito.mock(CloudService.class);
        NotificationSender n1 = Mockito.mock(NotificationSender.class);

        BodyTemperatureMonitor b1 = new BodyTemperatureMonitor(t1, c1, n1);
        b1.reportTemperatureReadingToCloud(tr1);

        Mockito.verify(c1).sendTemperatureToCloud(tr1);
    }

    @Test
    public void testInquireBodyStatusNormalNotification() {
        testInquireBodyStatusNotification("NORMAL", "Thumbs Up!");
    }

    @Test
    public void testInquireBodyStatusAbnormalNotification() {
        testInquireBodyStatusNotification("ABNORMAL", "Emergency!");
    }

    private void testInquireBodyStatusNotification(String bodyStatus, String expectedNotification) {
        TemperatureSensor t1 = Mockito.mock(TemperatureSensor.class);
        CloudService c1 = Mockito.mock(CloudService.class);
        NotificationSender n1 = Mockito.mock(NotificationSender.class);

        BodyTemperatureMonitor b1 = new BodyTemperatureMonitor(t1, c1, n1);
        Customer cust1 = b1.getFixedCustomer();
        Mockito.when(c1.queryCustomerBodyStatus(cust1)).thenReturn(bodyStatus);
        b1.inquireBodyStatus();

        if (bodyStatus.equals("NORMAL")) {
            Mockito.verify(n1).sendEmailNotification(cust1, expectedNotification);
        } else {
            FamilyDoctor fd1 = b1.getFamilyDoctor();
            Mockito.verify(n1).sendEmailNotification(fd1, expectedNotification);
        }
    }
}