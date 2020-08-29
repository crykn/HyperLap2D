package games.rednblack.editor.view.ui.properties.panels;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.observer.Notification;
import games.rednblack.editor.HyperLap2DFacade;
import games.rednblack.editor.controller.commands.RemoveComponentFromItemCommand;
import games.rednblack.editor.renderer.components.label.TypingLabelComponent;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.view.ui.properties.UIItemPropertiesMediator;
import games.rednblack.h2d.common.MsgAPI;
import org.apache.commons.lang3.ArrayUtils;

public class UITypingLabelPropertiesMediator extends UIItemPropertiesMediator<Entity, UITypingLabelProperties> {
    private static final String TAG = UITypingLabelPropertiesMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    public UITypingLabelPropertiesMediator() {
        super(NAME, new UITypingLabelProperties());
    }

    @Override
    public String[] listNotificationInterests() {
        String[] defaultNotifications = super.listNotificationInterests();
        String[] notificationInterests = new String[]{
                UITypingLabelProperties.CLOSE_CLICKED,
                UITypingLabelProperties.RESTART_BUTTON_CLICKED
        };

        return ArrayUtils.addAll(defaultNotifications, notificationInterests);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        switch (notification.getName()) {
            case UITypingLabelProperties.CLOSE_CLICKED:
                HyperLap2DFacade.getInstance().sendNotification(MsgAPI.ACTION_REMOVE_COMPONENT, RemoveComponentFromItemCommand.payload(observableReference, TypingLabelComponent.class));
                break;
            case UITypingLabelProperties.RESTART_BUTTON_CLICKED:
                restartTypingLabel();
                break;
        }
    }

    private void restartTypingLabel() {
        TypingLabelComponent typingLabelComponent = ComponentRetriever.get(observableReference, TypingLabelComponent.class);
        typingLabelComponent.typingLabel.restart();
    }

    @Override
    protected void translateObservableDataToView(Entity item) {

    }

    @Override
    protected void translateViewToItemData() {

    }
}
