package games.rednblack.editor.plugin.tiled.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.io.ByteStreams;
import games.rednblack.editor.plugin.tiled.TiledPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mariam on 4/21/16.
 */
public class ResourcesManager {

    private final String RESOURCES_FILE_NAME = "tiled";

    private TiledPlugin tiledPlugin;
    private TextureAtlas textureAtlas;

    public ResourcesManager(TiledPlugin tiledPlugin) {
        this.tiledPlugin = tiledPlugin;

        init();
    }

    private void init() {
        FileHandle atlasTempFile = getResourceFileFromJar(".atlas");
        FileHandle pngTempFile = getResourceFileFromJar(".png");
        textureAtlas = new TextureAtlas(atlasTempFile);
        atlasTempFile.file().deleteOnExit();
        pngTempFile.file().deleteOnExit();
    }

    private FileHandle getResourceFileFromJar(String extension) {
        String fileName = RESOURCES_FILE_NAME+extension;
        //File tempFile = new File (System.getProperty("user.dir")+fileName);
        FileHandle tempFile = Gdx.files.internal(fileName);
        try {
            InputStream in = getClass().getResourceAsStream("/"+fileName);
            FileOutputStream out = new FileOutputStream(tempFile.file());
            ByteStreams.copy(in, out);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public TextureRegion getTextureRegion(String name) {
        TextureRegion region = textureAtlas.findRegion(name); // try to get region from plugin assets
        if (region != null) {
//            System.out.println("region: "+name+",  "+region.getRegionWidth()+" "+region.getRegionHeight());
        }
        if (region == null) { // take the region from hyperlap assets
            region = tiledPlugin.getAPI().getSceneLoader().getRm().getTextureRegion(name);
        }
        return region;
    }

    public NinePatch getPluginNinePatch(String name) {
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(name);
        if(region == null) return null;
        return new NinePatch(region, region.splits[0], region.splits[1], region.splits[2], region.splits[3]);
    }
}
