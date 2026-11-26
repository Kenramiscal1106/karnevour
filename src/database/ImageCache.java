package database;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class ImageCache {

    private final static ImageCache instance = new ImageCache();

    private static final File PICTURE_CACHE_FILE = new File("src/database/data/picturecache");

    void cacheImage(String name, String url) {
        if(!PICTURE_CACHE_FILE.exists())
            PICTURE_CACHE_FILE.mkdirs();

        String fileName = name.replace(" ", "-");
        Path pictureFile = PICTURE_CACHE_FILE.toPath().resolve(fileName+".jpg");

        if(new File(pictureFile.toUri()).exists())
            return;

        try(InputStream stream = URI.create(url).toURL().openStream()){
            Files.copy(stream, pictureFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearImageCache(){
        Path cacheFolder = PICTURE_CACHE_FILE.toPath();
        if(!Files.exists(cacheFolder))
            return;

        try(Stream<Path> walker = Files.walk(cacheFolder)){
            walker.filter(path -> !path.equals(cacheFolder)).sorted(Comparator.reverseOrder()).forEach(file -> {
                try{
                    Files.deleteIfExists(file);
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            Files.deleteIfExists(cacheFolder);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public String getCachedImage(String name){
        Path resolved = PICTURE_CACHE_FILE.toPath().resolve(name.replace(" ","-")+".jpg");
        return resolved.toUri().toString();
    }
    public static ImageCache getInstance(){return instance;}
}
