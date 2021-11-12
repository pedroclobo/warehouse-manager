package ggc.core;

import java.io.Serializable;

/**
 * This public class implements the default notification delivery method.
 * The default delivery method does nothing.
 */
public class DefaultNotificationDeliveryMethod implements NotificationDeliveryMethod, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/**
	 * Delivers the notification.
	 *
	 * @param notification the notification to deliver.
	 */
	public void deliverNotification(Notification notification) {}

}
