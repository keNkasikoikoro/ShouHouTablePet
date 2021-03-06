package indi.koro.shouhou.starup.load;


import aurelienribon.tweenengine.equations.Linear;
import indi.korostudio.ksge.data.Data;
import indi.korostudio.ksge.panel.Scene;
import indi.korostudio.ksge.system.image.ImageBase;
import indi.korostudio.ksge.tool.Tool;
import indi.korostudio.ksge.tool.TweenTool;
import indi.korostudio.ksge.tween.TweenImplements;
import indi.korostudio.ksge.tween.TweenListener;
import indi.korostudio.ksge.tween.TweenSystem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class LoadScene extends Scene {

    protected TweenSystem lastout, inout;
    protected int nowImage = 1;
    protected Random r = new Random();
    protected Font font;
    protected BufferedImage strImage;
    protected Image image;
    protected LoadScene loadScene = this;

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (!aFlag) {
            if (inout.isRunning()) {
                inout.stop();
            }
        }
    }

    @Override
    public void in() {
        setAlpha(0f);
        /*
        in = TweenTool.SimpleTween(this, 2f, TweenImplements.ALPHA, 1f);
        out = TweenTool.SimpleTween(this, 2f, TweenImplements.ALPHA, 0f);
        inout = TweenTool.SimpleActuator(in, out);
        */
        lastout = TweenTool.SimpleTween(this, 2f, TweenImplements.ALPHA, 0f);
        inout = new TweenSystem();
        inout.addTimeLineDo(TweenTool.SimpleTimeLine(this, TweenImplements.ALPHA, 2f, 1, Linear.INOUT, 1f, 0f));
        inout.loop(true);
        reImage();
        Data.scenePanel.add(this);
        this.setVisible(true);
        lastout.addTweenListener(new TweenListener() {
            @Override
            public void start() {

            }

            @Override
            public void finish() {
                setVisible(false);
                setAlpha(1f);
                Data.scenePanel.remove(loadScene);
                loadScene.doNextScene();
            }

            @Override
            public void pause() {

            }

            @Override
            public void stop() {

            }
        });
        inout.start();
    }

    @Override
    public void out() {
        inout.stop();
        lastout.start();
    }

    @Override
    public String getSceneName() {
        return "load";
    }

    public void load() {
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setSize(Data.mainDimension);
        this.setAlpha(0f);
        font = new Font("Times New Roman", Font.BOLD, 40);
        strImage = Tool.stringImage(Color.GRAY, font, "loading......");
    }

    public void reImage() {
        image = ImageBase.get("load-0").getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, getWidth() - 350, getHeight() - 150, 150, 150, null);
        g2d.setFont(font);
        g2d.drawImage(strImage, getWidth() - 200, getHeight() - 50, null);
    }
}
