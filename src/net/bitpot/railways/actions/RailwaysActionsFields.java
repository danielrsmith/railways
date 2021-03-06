package net.bitpot.railways.actions;

/**
 * This class is used to store additional project wide information that is used internally by plugin actions.
 * Actions are registered in ActionManager instance which is application-wide, so
 * each Action object is shared between all projects. That's why we cannot use internal fields in actions
 * if we need to keep separate values in a separate projects.
 */
public class RailwaysActionsFields
{
    // Field is used to store previous value of Railways.isRoutesUpdating()
    public boolean previousRoutesUpdatingState = false;
}
