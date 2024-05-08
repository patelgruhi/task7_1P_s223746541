package sit707_week7;

public interface CloudService {

	/**
	 * Report temperature value to server.
	 * @param temperatureReading
	 */
	public void sendTemperatureToCloud(TemperatureReading temperatureReading);
	
	/**
	 * Inquire customer body status based on temperature.
	 * @param mockCustomer
	 * @return Status string - NORMAL, ABNORMAL
	 */
	public String queryCustomerBodyStatus(Customer mockCustomer);

	public Object queryCustomerBodyStatus(Object mockFamilyDoctor);
}
