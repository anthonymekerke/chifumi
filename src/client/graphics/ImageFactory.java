package client.graphics;


import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ImageFactory {

    public static enum SPRITES_ID {

        STARTER_ROCK        ("images/starter_rock.jpg"),
        STARTER_PAPER       ("images/starter_paper.jpg"),
        STARTER_SCISSORS    ("images/starter_scissors.jpg"),
        KANJI_ROCK        ("images/kanji_rock.png"),
        KANJI_PAPER       ("images/kanji_paper.png"),
        KANJI_SCISSORS    ("images/kanji_scissors.png"),
        LITERAL_ROCK        ("images/literal_rock.png"),
        LITERAL_PAPER       ("images/literal_paper.png"),
        LITERAL_SCISSORS    ("images/literal_scissors.png"),
        TOILET_PAPER        ("images/toilet_paper.png"),
        DEFAULT             ("images/default.jpg")
        ;

        private final String path ;
        SPRITES_ID(String path) { this.path = path ; }
    }

    private static HashMap<SPRITES_ID, Image[]> sprites = new HashMap<>() ;

    /**
     * Permet de précharger toutes les images du jeu en tâche de fond
     * @param finishedHandler : un handler déclenché lorsque le préchargement est terminé
     *                          (peut être null)
     */
    public static void preloadAll(Runnable finishedHandler){
        new Thread(()-> {
            try {
                for (SPRITES_ID id : SPRITES_ID.values()) {
                    load(id);
                }
                if(finishedHandler != null) finishedHandler.run();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Permet de récupérer les images
     * @param id : le code désignant une ou un ensemble d'images
     * @return : un tableau contenant la ou les images demandées
     */
    public static Image[] getSprites(SPRITES_ID id){
        Image[] images = sprites.get(id) ;
        if(images == null) {
            try {
                images = load(id) ;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images ;
    }

    private static Image[] load(SPRITES_ID id) throws URISyntaxException, IOException {
        String pathName = id.path ;
        Path path = Paths.get(ImageFactory.class.getResource(pathName).toURI()) ;
        Image[] images ;
        if(Files.isDirectory(path)){
            List<Path> paths = Files.list(path).filter(f -> !(Files.isDirectory(f))).sorted().collect(Collectors.toList()) ;
            images = new Image[paths.size()] ;
            int i=0 ;
            for (Path p: paths){
                String name = pathName + p.getFileName() ;
                images[i] = new Image(ImageFactory.class.getResource(name).toExternalForm());
                int finalI = i;
                i++ ;
            }
        }else{
            images = new Image[]{ new Image(ImageFactory.class.getResource(id.path).toExternalForm()) };
        }

        ImageFactory.sprites.put(id, images) ;
        return images ;
    }

}
