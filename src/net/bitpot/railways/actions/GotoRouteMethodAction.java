package net.bitpot.railways.actions;

import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import net.bitpot.railways.api.Railways;
import net.bitpot.railways.gui.gotoPopup.ChooseByNameRouteFilter;
import net.bitpot.railways.gui.gotoPopup.GotoRouteConfiguration;
import net.bitpot.railways.gui.gotoPopup.GotoRouteMethodModel;
import net.bitpot.railways.models.routes.RequestType;
import net.bitpot.railways.models.Route;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Action opens popup with list of all routes.
 */
public class GotoRouteMethodAction extends GotoActionBase {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getInstance(GotoRouteMethodAction.class.getName());

    @Override
    protected void gotoActionPerformed(AnActionEvent e) {

        final Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null)
            return;

        final GotoRouteMethodModel model = new GotoRouteMethodModel(project);
        PsiDocumentManager.getInstance(project).commitAllDocuments();

        showNavigationPopup(e, model, new GotoActionCallback<RequestType>() {

            @Nullable
            @Override
            protected ChooseByNameFilter<RequestType> createFilter(@NotNull ChooseByNamePopup popup)
            {
                return new ChooseByNameRouteFilter(popup, model,
                        GotoRouteConfiguration.getInstance(project), project);
            }

            @Override
            public void elementChosen(ChooseByNamePopup popup, Object element)
            {
                if (element instanceof Route)
                    ((Route) element).navigate(true);
            }
        });
    }


    @Override
    public void update(AnActionEvent event)
    {
        Project project = event.getProject();
        if (project == null)
            return;

        Railways api = Railways.getAPI(project);
        event.getPresentation().setEnabled(api.isRailsApp());
    }
}
