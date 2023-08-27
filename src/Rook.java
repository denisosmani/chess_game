import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Rook extends Piece {
    Rook(int x, int y,String color,Initialize initialize){
        super(x,y,color,initialize);
        Image rookImage;
        ImageView rookImageView;

        if(color.equals("black")) {

            rookImage = new Image("file:chessPieces/black_rook.png");
        }else{
            rookImage = new Image("file:chessPieces/white_rook.png");
        }

        rookImageView = new ImageView(rookImage);
        rookImageView.setFitWidth(60);
        rookImageView.setFitHeight(60);
        this.setTranslateX(x*60);
        this.setTranslateY(y*60);

        getChildren().add(rookImageView);

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
        //top
        for(int i=this.getY()-1; i>=0; i--){
            Initialize.squaresMatrix[this.getX()][i].setAttacked(true);
            if(Initialize.squaresMatrix[this.getX()][i].getOccupied()){
                break;
            }
        }

        //down
        for(int i=this.getY()+1; i<8; i++){
            Initialize.squaresMatrix[this.getX()][i].setAttacked(true);
            if(Initialize.squaresMatrix[this.getX()][i].getOccupied()){
                break;
            }
        }

        //right
        for(int i=this.getX()+1; i<8; i++){
            Initialize.squaresMatrix[i][this.getY()].setAttacked(true);
            if(Initialize.squaresMatrix[i][this.getY()].getOccupied()){
                break;
            }
        }

        //left
        for(int i=this.getX()-1; i>=0; i--){
            Initialize.squaresMatrix[i][this.getY()].setAttacked(true);
            if(Initialize.squaresMatrix[i][this.getY()].getOccupied()){
                break;
            }
        }


    }
}
