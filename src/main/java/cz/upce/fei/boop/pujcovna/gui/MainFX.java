package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.gui.kontejnery.Okno;
import cz.upce.fei.boop.pujcovna.gui.vyjimky.ChybnaCestaException;
import cz.upce.fei.boop.pujcovna.util.SystemInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Tato třída zajistí vytvoření hlavní obrazovky, která se skládá ze dvou čístí:
 * <ol>
 * <li>SeznamPanel - {@code ListView} reprezentující seznam prvků.
 * <li>PrikazovyPanel - {@code VBox} s navigací.
 * </ol>
 *
 * @author zea1ot 4/20/2023
 */
public class MainFX extends Application {

    private Stage primaryStage;

    private final Image SCENE_ICON;
    private static final String JMENO_IMAGE_SOUBORU = "icon.png";
    private static final String SCENE_TITULEK = "Půjčovna Motorek";
    private static final boolean SCENE_RESIZABLE = false;
    private static final int SCENE_SIRKA = 800;
    private static final int SCENE_VYSKA = 600;

    private final HBox root;

    public MainFX() {
        root = new Okno(SCENE_SIRKA);

        Image sceneIcon = null;
        try {
            sceneIcon = dejTitulek();
        } catch (ChybnaCestaException ignored) {}
        SCENE_ICON = sceneIcon;
    }

    @Override
    public void start(Stage stage) {
        var systemInfo = SystemInfo.dejVlastnostiSystemu();
        vypisSystemInfo(systemInfo);

        primaryStage = stage;
        nastavStage();
    }

    private void nastavStage() {
        var scene = new Scene(root, SCENE_SIRKA, SCENE_VYSKA);
        primaryStage.setScene(scene);
        primaryStage.setTitle(SCENE_TITULEK);
        primaryStage.setResizable(SCENE_RESIZABLE);
        if (SCENE_ICON != null)
            primaryStage.getIcons().add(SCENE_ICON);
        primaryStage.show();
    }

    private Image dejTitulek() throws ChybnaCestaException {
        var cesta = MainFX.class.getClassLoader().getResourceAsStream(JMENO_IMAGE_SOUBORU);
        if (cesta == null)
            throw new ChybnaCestaException();
        return new Image(cesta);
    }

    private static void vypisSystemInfo(String properties) { System.out.println(properties); }

    public static void main(String[] args) { launch(args); }
}
