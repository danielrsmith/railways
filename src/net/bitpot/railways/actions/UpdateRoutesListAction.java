package net.bitpot.railways.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import net.bitpot.railways.api.Railways;
import net.bitpot.railways.gui.RailwaysIcons;
import org.jetbrains.annotations.NotNull;

/**
 * Updates the list of routes.
 */
public class UpdateRoutesListAction extends AnAction
{
    @SuppressWarnings("unused")
    private static Logger log = Logger.getInstance(UpdateRoutesListAction.class.getName());

    // Presentation is stored to be used when asynchronous action icon needs to be updated.
    private static Presentation updateBtnPresentation = null;

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        //log.debug("Updating presentation: " + e.getPresentation());

        Project project = e.getProject();
        if (project == null)
            return;

        // Get API
        Railways api = Railways.getAPI(project);

        if (api.isRoutesUpdating())
            api.cancelRoutesUpdate();
        else
        {
            // Save all documents to make sure that routes will be collected using actual files.
            FileDocumentManager.getInstance().saveAllDocuments();

            if (api.updateRouteList())
            {
                updateBtnPresentation = e.getPresentation();
                updatePresentation(project, e.getPresentation());
            }
        }
    }


    public static void updateIcon(@NotNull Project project)
    {
        if (updateBtnPresentation != null)
            updatePresentation(project, updateBtnPresentation);
    }


    private static void updatePresentation(@NotNull Project project, Presentation pres)
    {
        //log.debug("Updating presentation: " + pres);
        Railways api = Railways.getAPI(project);

        RailwaysActionsFields fields = api.getRailwaysActionsFields();
        boolean previousState = fields.previousRoutesUpdatingState;
        boolean newState = api.isRoutesUpdating();

        // Update only when state is changed.
        if (previousState == newState)
            return;

        if (newState)
        {
            pres.setIcon(RailwaysIcons.SUSPEND);
            pres.setText("Cancel routes list update");
            pres.setDescription("Stops updating the list of routes");
        }
        else
        {
            pres.setIcon(RailwaysIcons.UPDATE);
            pres.setText("Update routes list");
            pres.setDescription("Update the list of routes");
        }

        fields.previousRoutesUpdatingState = newState;
    }


    /**
     * Updates the state of the action. Default implementation does nothing.
     * Override this method to provide the ability to dynamically change action's
     * state and(or) presentation depending on the context (For example
     * when your action state depends on the selection you can check for
     * selection and change the state accordingly).
     * This method can be called frequently, for instance, if an action is added to a toolbar,
     * it will be updated twice a second. This means that this method is supposed to work really fast,
     * no real work should be done at this phase. For example, checking selection in a tree or a list,
     * is considered valid, but working with a file system is not. If you cannot understand the state of
     * the action fast you should do it in the {@link #actionPerformed(AnActionEvent)} method and notify
     * the user that action cannot be executed if it's the case.
     *
     * @param e Carries information on the invocation place and data available
     */
    @Override
    public void update(AnActionEvent e)
    {
        Project project = e.getProject();
        if (project == null)
            return;

        updatePresentation(project, e.getPresentation());
    }
}
