package com.example.myapplication.SuperClasses;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.myapplication.BackGround.BGBlock;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.R;
import com.example.myapplication.HitBoxes.BoundingBox;
import com.example.myapplication.HitBoxes.BoundingCircle;

public class Projectile extends Drawable {
    //Todo meg kéne ezt csinálni
    protected final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 vTexCoord;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fTexCoord = vTexCoord;" +
                    "}";
    protected final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform sampler2D uTexture;" +
                    "varying vec2 fTexCoord;" +
                    "void main() {" +
                    "  gl_FragColor = texture2D(uTexture, fTexCoord);" +
                    "}";
    protected float projectileSpeed;
    private final float dx;
    private final float dy;

    private final float[] foo = new float[16];
    protected Character owner ;
    //todo valami nem stimmel mert nem fügetlen a mozgás a valami mástól és nem áll most össze a fejembe ezt fel kéne rajzolni ugy egyszerübb lenne
    public Projectile(float angle,Character character) {
        System.arraycopy(character.getOwnPositionM().clone(),0,ownPositionM,0,ownPositionM.length);
        this.owner = character;
        this.dx = (float) Math.cos(angle);
        this.dy = (float) Math.sin(angle);
        init();
        //Matrix.rotateM(rotationM,0,0,0,0,-90f);
    }
    public Projectile(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
        init();
        //Matrix.rotateM(rotationM,0,0,0,0,-90f);
    }
    private void init(){
        setVertexShader(vertexShaderCode);
        setFragmentShader(fragmentShaderCode);
        setProg();
        setVertexBuffer();
        setDrawListBuffer();
        setTexCoordBuffer();
        setSpriteSheets(R.drawable.place_holder,64,64);
    }


    public void move (float constantMoveNumber){
        Matrix.translateM(ownPositionM, 0, dx*projectileSpeed*constantMoveNumber, dy * projectileSpeed*constantMoveNumber, 0);
    }
    public void draw(float[] mvp){
        move();
        GLES20.glUseProgram(Prog);
        setvPMatrixHandle(mvp);
        setTextCord();
        setPositionHandle();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,spriteSheets.NextFrame());
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        setoffHandels();
    }
    private void move(){
        Matrix.translateM(ownPositionM,0,dx * 0.004f,dy * -0.004f,0);
        if (hit()) Game.getInstance().removeProjectile(this);
    }
    public boolean hit (){
        int i = 0;
        BGBlock[] valami =  Game.getInstance().getHitField().toArray(new BGBlock[0]);
        for (BGBlock bgBlock : valami) {
           ++i;
           if(new BoundingBox(bgBlock).intersects(new BoundingCircle(this))){
               return true;
           }
        }
        return false;
    }

    public Character getOwner() {
        return owner;
    }
}

