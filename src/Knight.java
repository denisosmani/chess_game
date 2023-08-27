import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Knight extends Piece {
    Knight(int x, int y,String color,Initialize initialize){
        super(x,y,color,initialize);
        Image knightImage;
        ImageView knightImageView;

        if(color.equals("black")) {
            knightImage = new Image("file:chessPieces/black_knight.png");
        }else{
            knightImage = new Image("file:chessPieces/white_knight.png");
        }
        knightImageView = new ImageView(knightImage);
        knightImageView.setFitWidth(60);
        knightImageView.setFitHeight(60);
        this.setTranslateX(x*60);
        this.setTranslateY(y*60);

        getChildren().add(knightImageView);

    }
    @Override
    protected boolean  Move(int x2, int y2){
        clearAttacks();
        this.Attack();
        if(Initialize.squaresMatrix[x2][y2].getAttacked()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void Attack() {
        if(this.getX()-1>=0 && this.getY()-2>=0){
            Initialize.squaresMatrix[this.getX()-1][this.getY()-2].setAttacked(true);
        }

        if(this.getX()+1<8 && this.getY()-2>=0){
            Initialize.squaresMatrix[this.getX()+1][this.getY()-2].setAttacked(true);
        }

        if(this.getX()-1 >= 0 && this.getY()+2 <8){
            Initialize.squaresMatrix[this.getX()-1][this.getY()+2].setAttacked(true);
        }

        if(this.getX()+1 < 8 && this.getY() + 2 <8){
            Initialize.squaresMatrix[this.getX()+1][this.getY()+2].setAttacked(true);
        }

        if(this.getX()-2>=0 && this.getY()-1>=0){
            Initialize.squaresMatrix[this.getX()-2][this.getY()-1].setAttacked(true);
        }

        if(this.getX()-2>=0 && this.getY()+1<8){
            Initialize.squaresMatrix[this.getX()-2][this.getY()+1].setAttacked(true);
        }

        if(this.getX()+2<8 && this.getY()-1 >=0){
            Initialize.squaresMatrix[this.getX()+2][this.getY()-1].setAttacked(true);
        }

        if(this.getX()+2 < 8 && this.getY() + 1 <8){
            Initialize.squaresMatrix[this.getX()+2][this.getY()+1].setAttacked(true);
        }
    }

}
