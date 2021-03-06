package net.bitpot.railways.models.routes;

import net.bitpot.railways.gui.RailwaysIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author toXXIc
 */
public class GetRequestType extends RequestType
{
    @Override
    public Icon getIcon()
    {
        return RailwaysIcons.HTTP_METHOD_GET;
    }

    @NotNull
    @Override
    public String getName()
    {
        return "GET";
    }
}
