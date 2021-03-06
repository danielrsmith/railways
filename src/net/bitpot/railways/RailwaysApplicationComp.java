package net.bitpot.railways;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class RailwaysApplicationComp implements ApplicationComponent
{
    @SuppressWarnings("unused")
    private static Logger log = Logger.getInstance(RailwaysApplicationComp.class.getName());

    private static RailwaysApplicationComp instance = null;

    public RailwaysApplicationComp()
    {
    }

    public void initComponent()
    {
        //log.debug("Initializing Railways...");
        // Deny two instances of plugin. Otherwise we will get a mess-up in editor when using Code Injector.
        if (instance != null)
            throw new RuntimeException("Railways plugin already initialized.");

        // Save plugin object for accessing in static methods.
        instance = this;
    }

    public void disposeComponent()
    {
        // Do nothing now
    }

    @NotNull
    public String getComponentName()
    {
        return "Railways.ApplicationComponent";
    }
}
