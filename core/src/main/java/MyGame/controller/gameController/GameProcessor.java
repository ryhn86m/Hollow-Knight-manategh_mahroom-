package MyGame.controller.gameController;

import MyGame.Main;
import MyGame.controller.enemyController.ZoteController;
import MyGame.model.enums.MusicTracks;
import MyGame.model.world.GameModel;
import MyGame.model.entities.HowlingWraiths;
import MyGame.model.entities.Knight;
import MyGame.model.entities.VengefulSpirit;
import MyGame.model.entities.Zote;
import MyGame.model.enums.KnightState;
import MyGame.model.enums.Sfx;
import MyGame.view.menus.PauseMenu;
import MyGame.view.renderers.itemsRenderer.DialogueBox;
import MyGame.view.renderers.itemsRenderer.TiledMapHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameProcessor implements InputProcessor {
    private GameModel model;
    private PauseMenu pauseMenu;
    private Knight knight;
    private Zote zote;
    private ZoteController zoteController;
    private DialogueBox dialogueBox;
    private TiledMapHelper helper;
    private String[] mainDialogue;
    private String[] percepts;
    private boolean isCtrlPressed = false;
    private boolean isUpPressed = false;
    public GameProcessor(GameModel model, PauseMenu pauseMenu, Knight knight){
        this.model = model;
        this.pauseMenu = pauseMenu;
        this.knight=knight;
    }
    public GameProcessor(GameModel model, PauseMenu pauseMenu, Knight knight,Zote zote,ZoteController zoteController,DialogueBox dialogueBox,TiledMapHelper helper){
        this.model = model;
        this.pauseMenu = pauseMenu;
        this.knight=knight;
        this.zote = zote;
        this.zoteController = zoteController;
        this.dialogueBox = dialogueBox;
        this.helper = helper;
    }
    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox != null && dialogueBox.isActive() && keycode != Input.Keys.ENTER) {
            return true;
        }
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
            isCtrlPressed = true;
        }
        if (keycode == Input.Keys.UP) {
            isUpPressed = true;
        }
        if (isCtrlPressed) {
            switch (keycode) {
                case Input.Keys.B->{
                    model.setTeleportToBoss(true);
                    model.setInZone2(true);
                    if(Main.getMain().isMusicOn()) Main.getMain().changeMusic(MusicTracks.Forgotten);
                    return true;
                }
                case Input.Keys.S->{
                    model.setNoclipMode(!model.isNoclipMode());
                    knight.setGravityEnabled(!model.isNoclipMode());return true;}
                case Input.Keys.R->{ this.knight.setSoul(99);if(Main.getMain().isSfx()) Sfx.SoulGain.play();
                    return true;}
                case Input.Keys.H->{  int currentHp = knight.getHp();
                    if (currentHp <= 1) {
                        knight.setHp(knight.getHp()+1);
                        knight.setDead(false);
                        knight.setState(KnightState.IDLE);
                    }return true;}
                case Input.Keys.K->{if (model.getCurrentMapRenderer() != null) {
                    model.getCurrentMapRenderer().instaKillAll();
                }return true;}
                case Input.Keys.G->{model.setGodMode(!model.isGodMode());return true;}

            }
        }
        if (keycode == Input.Keys.ESCAPE) {
            model.setPaused(!model.isPaused());
            if(model.isPaused()) pauseMenu.show();
            else pauseMenu.hide();
            return true;
        }
        else if (keycode == KeyController.JUMP) {
            knight.jump();
            return true;
        }
        else if (keycode == KeyController.DASH) {
            if(Main.getMain().isSfx()) Sfx.Dash.play();
            knight.startDash();
            return true;
        }
        else if (keycode == KeyController.ATTACK) {
            if (!knight.isAttacking()) {
                if(Main.getMain().isSfx()) Sfx.NailSlash.play();
                if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    knight.attackDown();
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    knight.attackUp();
                }else {
                    knight.attack();
                }
            }
            return true;
        }
        else if (keycode == KeyController.FIREBALL) {
            if (knight.getSoul() >= 33 && knight.getState() != KnightState.FIREBALL && knight.getState() != KnightState.SCREAM && knight.getState() != KnightState.DASH) {
                knight.castFireball();
                model.getFireballs().add(new VengefulSpirit(knight.getX(), knight.getY(), knight.isFacingRight()));
            }
            return true;
        }
        else if (keycode == KeyController.INVENTORY) {
            model.setPaused(true);
            model.setInventoryOpen(!model.isInventoryOpen());
            return true;
        }
        else if (keycode == KeyController.SCREAM) {
            if (knight.getSoul() >= 33 && knight.getState() != KnightState.FIREBALL && knight.getState() != KnightState.SCREAM && knight.getState() != KnightState.DASH) {
                knight.castScream();
                model.getScreams().add(new HowlingWraiths(knight.getX(), knight.getY()));
            }
            return true;
        }
        else if (keycode == KeyController.INTERACT) {
            mainDialogue = new String[]{Main.getLanguage().dialogue1,
                Main.getLanguage().dialogue2,
                Main.getLanguage().dialogue3};

           percepts= new String[]{
               Main.getLanguage().precept1,
               Main.getLanguage().precept2,
               Main.getLanguage().precept3,
               Main.getLanguage().precept4,
               Main.getLanguage().precept5
           };
            zote.setMainDialogues(mainDialogue);
            zote.setPrecepts(percepts);
            if (zote != null && zoteController != null && dialogueBox != null) {
                if (Math.abs(knight.getX() - zote.getX()) < 150 && !dialogueBox.isActive()) {
                    dialogueBox.startDialogue(zoteController.interact());
                    zoteController.playRandomVoice();
                    knight.setVelocityX(0);
                }
            }
            return true;
        }
        else if (keycode == Input.Keys.ENTER) {
            if (dialogueBox != null && dialogueBox.isActive()) {
                if (!dialogueBox.isTypingFinished()) {
                    dialogueBox.skipTyping();
                }
                else {
                    if (!zote.isHasFinishedMainDialogue()) {
                        dialogueBox.startDialogue(zoteController.interact());
                        zoteController.playRandomVoice();
                    }
                    else {
                        dialogueBox.closeDialogue();
                        zoteController.endInteraction();
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
            isCtrlPressed = false;
        }
        if(keycode == KeyController.JUMP){
            knight.cutJump();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
