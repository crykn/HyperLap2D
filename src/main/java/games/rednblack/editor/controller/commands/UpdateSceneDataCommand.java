package games.rednblack.editor.controller.commands;

import games.rednblack.editor.renderer.data.LightsPropertiesVO;
import games.rednblack.editor.renderer.data.PhysicsPropertiesVO;
import games.rednblack.editor.renderer.data.SceneVO;
import games.rednblack.editor.view.stage.Sandbox;
import games.rednblack.h2d.common.MsgAPI;

public class UpdateSceneDataCommand extends EntityModifyRevertibleCommand {

    private SceneVO sceneVO;
    private PhysicsPropertiesVO physicsBackup;
    private LightsPropertiesVO lightsBackup;

    @Override
    public void doAction() {
        Object[] payload = getNotification().getBody();
        sceneVO = (SceneVO) payload[0];
        PhysicsPropertiesVO physicsPropertiesVO = (PhysicsPropertiesVO) payload[1];
        LightsPropertiesVO lightsPropertiesVO = (LightsPropertiesVO) payload[2];

        physicsBackup = new PhysicsPropertiesVO(sceneVO.physicsPropertiesVO);
        lightsBackup = new LightsPropertiesVO(sceneVO.lightsPropertiesVO);

        PhysicsPropertiesVO physicsVO = sceneVO.physicsPropertiesVO;
        physicsVO.gravityX = physicsPropertiesVO.gravityX;
        physicsVO.gravityY = physicsPropertiesVO.gravityY;
        physicsVO.sleepVelocity = physicsPropertiesVO.sleepVelocity;
        physicsVO.enabled = physicsPropertiesVO.enabled;

        LightsPropertiesVO lightsVO = sceneVO.lightsPropertiesVO;
        lightsVO.ambientColor[0] = lightsPropertiesVO.ambientColor[0];
        lightsVO.ambientColor[1] = lightsPropertiesVO.ambientColor[1];
        lightsVO.ambientColor[2] = lightsPropertiesVO.ambientColor[2];
        lightsVO.ambientColor[3] = lightsPropertiesVO.ambientColor[3];
        lightsVO.blurNum = lightsPropertiesVO.blurNum;
        lightsVO.lightType = lightsPropertiesVO.lightType;
        lightsVO.directionalDegree = lightsPropertiesVO.directionalDegree;
        lightsVO.directionalRays = lightsPropertiesVO.directionalRays;
        lightsVO.directionalColor[0] = lightsPropertiesVO.directionalColor[0];
        lightsVO.directionalColor[1] = lightsPropertiesVO.directionalColor[1];
        lightsVO.directionalColor[2] = lightsPropertiesVO.directionalColor[2];
        lightsVO.directionalColor[3] = lightsPropertiesVO.directionalColor[3];

        lightsVO.enabled = lightsPropertiesVO.enabled;

        Sandbox.getInstance().sceneControl.updateAmbientLights();

        facade.sendNotification(MsgAPI.ITEM_DATA_UPDATED);
    }

    @Override
    public void undoAction() {
        PhysicsPropertiesVO physicsVO = sceneVO.physicsPropertiesVO;
        physicsVO.gravityX = physicsBackup.gravityX;
        physicsVO.gravityY = physicsBackup.gravityY;
        physicsVO.sleepVelocity = physicsBackup.sleepVelocity;
        physicsVO.enabled = physicsBackup.enabled;

        LightsPropertiesVO lightsVO = sceneVO.lightsPropertiesVO;
        lightsVO.ambientColor[0] = lightsBackup.ambientColor[0];
        lightsVO.ambientColor[1] = lightsBackup.ambientColor[1];
        lightsVO.ambientColor[2] = lightsBackup.ambientColor[2];
        lightsVO.ambientColor[3] = lightsBackup.ambientColor[3];
        lightsVO.blurNum = lightsBackup.blurNum;
        lightsVO.lightType = lightsBackup.lightType;
        lightsVO.directionalDegree = lightsBackup.directionalDegree;
        lightsVO.directionalRays = lightsBackup.directionalRays;
        lightsVO.directionalColor[0] = lightsBackup.directionalColor[0];
        lightsVO.directionalColor[1] = lightsBackup.directionalColor[1];
        lightsVO.directionalColor[2] = lightsBackup.directionalColor[2];
        lightsVO.directionalColor[3] = lightsBackup.directionalColor[3];

        lightsVO.enabled = lightsBackup.enabled;

        Sandbox.getInstance().sceneControl.updateAmbientLights();

        facade.sendNotification(MsgAPI.ITEM_DATA_UPDATED);
    }

    public static Object payload(SceneVO scene, PhysicsPropertiesVO physicsPropertiesVO, LightsPropertiesVO lightsPropertiesVO) {
        Object[] payload = new Object[3];
        payload[0] = scene;
        payload[1] = physicsPropertiesVO;
        payload[2] = lightsPropertiesVO;

        return payload;
    }
}
