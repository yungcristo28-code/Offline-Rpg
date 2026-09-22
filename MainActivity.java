package com.example.igoroffline;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.*;
import android.graphics.drawable.*;
import android.content.Context;
import android.view.MotionEvent;
import java.util.*;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(new GameView(this));
    }

    static class GameView extends View {
        Paint p = new Paint(3);
        Random rng = new Random();
        float px=0, py=0, targetX=0, targetY=0;
        int hp=100, maxHp=100, diamonds=0, level=1, xp=0;
        long lastAttack=0, lastDrop=0;
        ArrayList<Enemy> enemies = new ArrayList<>();

        GameView(Context c) {
            super(c);
            setFocusable(true);
            for(int i=0;i<7;i++) enemies.add(new Enemy(140+i*110, 330+(i%2)*90));
        }

        protected void onDraw(Canvas c) {
            super.onDraw(c);
            int w=getWidth(), h=getHeight();
            if(px==0){ px=w/2f; py=h/2f; targetX=px; targetY=py; }

            c.drawColor(Color.rgb(12,14,24));
            p.setColor(Color.rgb(28,42,38)); c.drawRect(0,0,w,h,p);

            // arena
            p.setColor(Color.rgb(42,58,49));
            for(int x=0;x<w;x+=64) for(int y=80;y<h;y+=64) c.drawRect(x+1,y+1,x+62,y+62,p);

            // enemies
            for(Enemy e: enemies) {
                p.setColor(Color.rgb(170,55,65)); c.drawCircle(e.x,e.y,22,p);
                p.setColor(Color.WHITE); p.setTextSize(12); c.drawText(""+e.hp,e.x-8,e.y-28,p);
            }

            // Igor
            p.setColor(Color.rgb(225,225,240));
            c.drawCircle(px,py,27,p);
            p.setColor(Color.rgb(145,75,175));
            Path wing=new Path();
            wing.moveTo(px-15,py-5); wing.lineTo(px-62,py-35); wing.lineTo(px-45,py+10); wing.lineTo(px-18,py+18); wing.close(); c.drawPath(wing,p);
            wing=new Path();
            wing.moveTo(px+15,py-5); wing.lineTo(px+62,py-35); wing.lineTo(px+45,py+10); wing.lineTo(px+18,py+18); wing.close(); c.drawPath(wing,p);

            // HUD
            p.setColor(Color.BLACK); c.drawRect(0,0,w,72,p);
            p.setColor(Color.WHITE); p.setTextSize(22); c.drawText("IGOR  •  ANJO VAMPÍRICO",20,30,p);
            p.setTextSize(16); c.drawText("HP "+hp+"/"+maxHp+"   LV "+level+"   💎 "+diamonds+"   XP "+xp,20,56,p);

            p.setColor(Color.rgb(180,50,190)); c.drawCircle(w-90,h-80,55,p);
            p.setColor(Color.WHITE); p.setTextSize(15); c.drawText("ECLIPSE",w-124,h-76,p); c.drawText("Carmesim",w-130,h-56,p);

            p.setColor(Color.argb(70,240,80,180)); c.drawCircle(px,py,115,p);

            if(System.currentTimeMillis()-lastAttack>80) {
                updateGame();
                invalidate();
            }
        }

        void updateGame() {
            long now=System.currentTimeMillis();
            float dx=targetX-px, dy=targetY-py, d=(float)Math.sqrt(dx*dx+dy*dy);
            if(d>8){ px += dx/d*3.5f; py += dy/d*3.5f; }

            // Low-cooldown area vampirism.
            if(now-lastAttack>=3500) {
                lastAttack=now;
                int hits=0;
                for(Enemy e: enemies) {
                    float ex=e.x-px, ey=e.y-py;
                    if(ex*ex+ey*ey <= 115*115 && e.hp>0){ e.hp-=55; hits++; }
                }
                hp=Math.min(maxHp, hp + hits*8);
            }

            for(Enemy e: enemies) {
                if(e.hp<=0) {
                    if(now-lastDrop>350) {
                        lastDrop=now;
                        if(rng.nextFloat()<0.45f) diamonds++;
                        xp+=10;
                        if(xp>=100){xp-=100;level++;maxHp+=10;hp=maxHp;}
                    }
                    e.x=70+rng.nextInt(Math.max(80,getWidth()-140));
                    e.y=120+rng.nextInt(Math.max(80,getHeight()-220));
                    e.hp=100+level*8;
                }
            }
        }

        public boolean onTouchEvent(MotionEvent e) {
            if(e.getAction()==MotionEvent.ACTION_DOWN || e.getAction()==MotionEvent.ACTION_MOVE) {
                targetX=e.getX(); targetY=e.getY();
                return true;
            }
            return true;
        }

        static class Enemy {
            float x,y; int hp=100;
            Enemy(float a,float b){x=a;y=b;}
        }
    }
}
