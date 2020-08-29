package games.rednblack.editor.renderer.factory.component;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.label.LabelComponent;
import games.rednblack.editor.renderer.components.label.TypingLabelComponent;
import games.rednblack.editor.renderer.data.*;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;

public class LabelComponentFactory extends ComponentFactory{
	
	private static int labelDefaultSize = 12;

	public LabelComponentFactory(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
		super(engine, rayHandler, world, rm);
	}

	@Override
	public void createComponents(Entity root, Entity entity, MainItemVO vo) {
		 createCommonComponents(entity, vo, EntityFactory.LABEL_TYPE);
		 createParentNodeComponent(root, entity);
		 createNodeComponent(root, entity);
		 createLabelComponent(entity, (LabelVO) vo);
	}

	@Override
	protected DimensionsComponent createDimensionsComponent(Entity entity, MainItemVO vo) {
        DimensionsComponent component = engine.createComponent(DimensionsComponent.class);
        component.height = ((LabelVO) vo).height;
        component.width = ((LabelVO) vo).width;

        entity.add(component);
        return component;
    }

    protected LabelComponent createLabelComponent(Entity entity, LabelVO vo) {
	    LabelComponent component = engine.createComponent(LabelComponent.class);
	    component.setText(vo.text);
	    component.setStyle(generateStyle(rm, vo.style, vo.size));
        component.fontName = vo.style;
        component.fontSize = vo.size;
        component.setAlignment(vo.align);
        component.setWrap(vo.wrap);

        ProjectInfoVO projectInfoVO = rm.getProjectVO();
        ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);

        component.setFontScale(multiplier/projectInfoVO.pixelToWorld);

        entity.add(component);

        if (vo.isTyping) {
            TypingLabelComponent typingLabelComponent = engine.createComponent(TypingLabelComponent.class);
            entity.add(typingLabelComponent);
        }
        return component;
    }
    
    
    public static LabelStyle generateStyle(IResourceRetriever rManager, String fontName, int size) {

        if (size == 0) {
            size = labelDefaultSize;
        }
        return new LabelStyle(rManager.getBitmapFont(fontName, size), null);
    }

}
