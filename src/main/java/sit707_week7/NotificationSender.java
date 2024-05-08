package sit707_week7;

public interface NotificationSender {

	/**
	 * Notify customer him/herself of a message.
	 * @param mockFamilyDoctor
	 * @param msg
	 */
	public void sendEmailNotification(Object mockFamilyDoctor, String msg);
	
	/**
	 * Notify family physician alert message.
	 * @param familyDoctor
	 * @param msg
	 */
	public void sendEmailNotification(FamilyDoctor familyDoctor, String msg);
}
