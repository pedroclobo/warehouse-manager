package ggc.core;

/*
 * Defines an entity who's interested in receiving product notifications.
 */
public interface Notifiable {

	/**
	 * Updates the receiver's notifications with a new one.
	 *
	 * @param notification the new notification.
	 */
    public void updateNotifications(Notification notification);
}
