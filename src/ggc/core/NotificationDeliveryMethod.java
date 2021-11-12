package ggc.core;

/**
 * This interface allows the app to have various notification delivery methods.
 * Example: SMS, Email, ...
 */
public interface NotificationDeliveryMethod {

	/**
	 * Delivers the notification.
	 *
	 * @param notification the notification to deliver.
	 */
	public void deliverNotification(Notification notification);
}
